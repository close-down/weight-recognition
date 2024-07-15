package com.ytt.service;

import com.ytt.common.enums.ExceptionEnum;
import com.ytt.common.exception.RecognitionException;
import com.ytt.common.rest.RecognitionResult;
import com.ytt.dto.Goods;
import com.ytt.dto.Layer;
import com.ytt.dto.RecognitionItem;
import com.ytt.dto.Stock;

import java.util.*;

public class VendingMachineRecognizer {

    private static final int SENSOR_TOLERANCE = 10;

    public RecognitionResult recognize(List<Layer> initialWeights, List<Layer> finalWeights,
                                       List<Goods> goodsList, List<Stock> stockList) {
        
        Map<Integer, Integer> initialMap = new HashMap<>();
        Map<Integer, Integer> finalMap = new HashMap<>();

        for (Layer layer : initialWeights) {
            initialMap.put(layer.getIndex(), layer.getWeight());
        }
        for (Layer layer : finalWeights) {
            finalMap.put(layer.getIndex(), layer.getWeight());
        }

        List<RecognitionItem> items = new ArrayList<>();
        List<RecognitionException> exceptions = new ArrayList<>();
        boolean successful = true;

        for (int i = 1; i <= 10; i++) {
            int startWeight = initialMap.getOrDefault(i, -1);
            int endWeight = finalMap.getOrDefault(i, -1);

            if (startWeight < 0 || endWeight < 0 || startWeight > 32767 || endWeight > 32767) {
                exceptions.add(new RecognitionException(i, ExceptionEnum.SENSOR_EXCEPTION, startWeight, endWeight));
                successful = false;
                continue;
            }

            int weightChange = startWeight - endWeight;

            if (weightChange < 0) {
                exceptions.add(new RecognitionException(i, ExceptionEnum.FOREIGN_OBJECT_EXCEPTION, startWeight, endWeight));
                successful = false;
                continue;
            }

            List<Stock> stocksInLayer = getStocksForLayer(stockList, i);
            List<Goods> possibleGoods = getPossibleGoods(goodsList, stocksInLayer);

            Map<String, Integer> recognizedItems = recognizeItems(weightChange, possibleGoods);

            if (recognizedItems == null) {
                exceptions.add(new RecognitionException(i, ExceptionEnum.UNRECOGNIZABLE_COMBINATION_EXCEPTION, startWeight, endWeight));
                successful = false;
            } else {
                for (Map.Entry<String, Integer> entry : recognizedItems.entrySet()) {
                    items.add(new RecognitionItem(entry.getKey(), entry.getValue()));
                }
            }
        }

        return new RecognitionResult(successful, items, exceptions);
    }

    private List<Stock> getStocksForLayer(List<Stock> stockList, int layer) {
        List<Stock> result = new ArrayList<>();
        for (Stock stock : stockList) {
            if (stock.getLayer() == layer) {
                result.add(stock);
            }
        }
        return result;
    }

    private List<Goods> getPossibleGoods(List<Goods> goodsList, List<Stock> stocksInLayer) {
        List<Goods> result = new ArrayList<>();
        Set<String> goodsIds = new HashSet<>();
        for (Stock stock : stocksInLayer) {
            goodsIds.add(stock.getGoodsId());
        }
        for (Goods goods : goodsList) {
            if (goodsIds.contains(goods.getId())) {
                result.add(goods);
            }
        }
        return result;
    }

    private Map<String, Integer> recognizeItems(int weightChange, List<Goods> possibleGoods) {
        Map<String, Integer> recognizedItems = new HashMap<>();
        if (recognizeHelper(weightChange, possibleGoods, 0, recognizedItems)) {
            return recognizedItems;
        }
        return null;
    }

    private boolean recognizeHelper(int remainingWeight, List<Goods> possibleGoods, int index, Map<String, Integer> currentItems) {
        if (remainingWeight >= -SENSOR_TOLERANCE && remainingWeight <= SENSOR_TOLERANCE) {
            return true;
        }
        if (index >= possibleGoods.size() || remainingWeight < 0) {
            return false;
        }

        Goods currentGoods = possibleGoods.get(index);
        int maxCount = remainingWeight / currentGoods.getWeight();

        for (int count = 0; count <= maxCount; count++) {
            currentItems.put(currentGoods.getId(), count);
            if (recognizeHelper(remainingWeight - count * currentGoods.getWeight(), possibleGoods, index + 1, currentItems)) {
                return true;
            }
            currentItems.remove(currentGoods.getId());
        }

        return false;
    }
}
