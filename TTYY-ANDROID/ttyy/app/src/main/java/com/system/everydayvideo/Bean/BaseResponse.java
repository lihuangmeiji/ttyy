package com.system.everydayvideo.Bean;

import cn.droidlover.xdroidmvp.net.IResponse;

/**
 * Created by wanglei on 2016/12/11.
 */

public class BaseResponse implements IResponse {

    private String msg;

    private String code;

    protected boolean error;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return error;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return code;
    }



}
