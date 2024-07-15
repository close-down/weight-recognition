package com.ytt.dto;

import lombok.Data;

@Data
public class Layer {
    /**
     * 编号，从1开始
     */
    private int index;
    /**
     * 重量传感器数值，单位g
     */
    private int weight;

    public Layer(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }
}
