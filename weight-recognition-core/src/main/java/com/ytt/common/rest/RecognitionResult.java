package com.ytt.common.rest;

import com.ytt.common.exception.RecognitionException;
import com.ytt.dto.RecognitionItem;
import lombok.Data;

import java.util.List;

/**
 * 统一返回体积
 */
@Data
public class RecognitionResult {
    private boolean successful;
    private List<RecognitionItem> items;
    private List<RecognitionException> exceptions;

    public RecognitionResult(boolean successful, List<RecognitionItem> items, List<RecognitionException> exceptions) {
        this.successful = successful;
        this.items = items;
        this.exceptions = exceptions;
    }

    public RecognitionResult() {
    }
}
