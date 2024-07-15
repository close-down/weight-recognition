package com.ytt.common.exception;


import com.ytt.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * 识别异常类
 */
@Data
public class RecognitionException {
    private int layer;
    private ExceptionEnum exception;
    private int beginWeight;
    private int endWeight;

    public RecognitionException(int layer, ExceptionEnum exception, int beginWeight, int endWeight) {
        this.layer = layer;
        this.exception = exception;
        this.beginWeight = beginWeight;
        this.endWeight = endWeight;
    }
}
