package org.mediscribe.dto;

import java.io.Serializable;

public class ApiResponse<T> extends ApiResponseMeta  implements Serializable {
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T data) {
        super(code,message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
