package com.system.everydayvideo.Bean;

/**
 * Created by miya95 on 2017/1/16.
 */
public class BaseResponse_old<T> {
    private String code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return "0".equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
