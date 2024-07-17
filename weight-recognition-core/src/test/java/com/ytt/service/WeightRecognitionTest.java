package com.ytt.service;

import com.ytt.common.rest.RecognitionResult;
import com.ytt.dto.Goods;
import com.ytt.dto.Layer;
import com.ytt.dto.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class WeightRecognitionTest {

    @Test
    public void testRecognize() {
        List<Layer> initialWeights = Arrays.asList(
                new Layer(1, 1000),
                new Layer(2, 2000)
        );

        List<Layer> finalWeights = Arrays.asList(
                new Layer(1, 900),
                new Layer(2, 1800)
        );

        List<Goods> goodsList = Arrays.asList(
                new Goods("000001", 100),
                new Goods("000002", 200)
        );


        List<Stock> stockList = Arrays.asList(
                new Stock("000001", 1, 10),
                new Stock("000002", 2, 10)
        );


        VendingMachineRecognizer recognizer = new VendingMachineRecognizer();
        RecognitionResult result = recognizer.recognize(initialWeights, finalWeights, goodsList, stockList);

        assertTrue(result.isSuccessful());
        assertEquals(2, result.getItems().size());
        assertEquals("000001", result.getItems().get(0).getGoodsId());
        assertEquals(1, result.getItems().get(0).getNum());
        assertEquals("000002", result.getItems().get(1).getGoodsId());
        assertEquals(1, result.getItems().get(1).getNum());
    }

}
