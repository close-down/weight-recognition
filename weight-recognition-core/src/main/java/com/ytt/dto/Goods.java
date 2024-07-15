package com.ytt.dto;

import lombok.Data;

@Data
public class Goods {
    /**
     * 唯一标识
     */
    private String id;

    /**
     * 商品重量,单位g
     */
    private int weight;

    /**
     * 包装容差，以百分比表示
     */
    private double packageTolerance;

    public Goods(String id, int weight, double packageTolerance) {
        this.id = id;
        this.weight = weight;
        this.packageTolerance = packageTolerance;
    }

    public Goods(String id, int weight) {
        this.id = id;
        this.weight = weight;
    }
}
