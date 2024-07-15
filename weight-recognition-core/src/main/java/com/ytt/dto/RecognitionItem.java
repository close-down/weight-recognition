package com.ytt.dto;

import lombok.Data;

@Data
public class RecognitionItem {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 数量
     */
    private int num;

    public RecognitionItem(String goodsId, int num) {
        this.goodsId = goodsId;
        this.num = num;
    }

    public RecognitionItem() {
    }
}
