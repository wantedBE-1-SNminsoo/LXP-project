package com.ogirafferes.lxp.sales.application.pg;

public enum PayCode {
    SUCCESS("Success"),
    FAIL("fail");

    private String code;

    private PayCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
