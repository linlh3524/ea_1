package com.xckj.ea.common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("success"),
    ERROR("error"),
    NOLOGIN("nologin"),
    NOALLOW("noallow");


    private final String value;
    ResponseCode(String i) {
        this.value=i;
    }

}
