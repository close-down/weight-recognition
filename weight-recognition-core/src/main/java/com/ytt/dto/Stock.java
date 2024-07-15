package com.ytt.dto;

import lombok.Data;

@Data
public class Stock {
    /**
     * 库存对应的商品
     */
    private String goodsId;
    /**
     * 库存对应的层架
     */
    private int layer;
    /**
     * 库存数量
     */
    private int num;

    public Stock(String goodsId, int layer, int num) {
        this.goodsId = goodsId;
        this.layer = layer;
        this.num = num;
    }
}
