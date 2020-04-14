package com.xckj.ea.common;
import lombok.Getter;

@Getter
public enum TaskStatus {
    UNCHECK("1"), //未确认
    CHECKED("2"),//已确认
    SINGED("3"),//已签到
    DONE("4");//已完成
    private final String value;
    TaskStatus(String i) {        this.value=i;
    }
}
