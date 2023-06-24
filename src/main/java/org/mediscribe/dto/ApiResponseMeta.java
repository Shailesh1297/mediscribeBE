package org.mediscribe.dto;

import java.io.Serializable;

public class ApiResponseMeta implements Serializable {
    protected int code;
    protected String message;

    public ApiResponseMeta() {
    }

    public ApiResponseMeta(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
