package com.ytt.common.enums;

/**
 * 异常枚举
 */
public enum ExceptionEnum {
    SENSOR_EXCEPTION(0, "传感器异常"),
    FOREIGN_OBJECT_EXCEPTION(1, "外来物体异常"),
    UNRECOGNIZABLE_COMBINATION_EXCEPTION(2, "不可识别异常")
    ;

    private final int code;
    private final String desc;

    ExceptionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
