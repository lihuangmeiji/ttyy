package com.system.everydayvideo.net;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {

    public static  String API_BASE_URL = EnvConstant.API_DEVENV_URL;

    static {
        API_BASE_URL =  EnvConstant.isDevEnv ? EnvConstant.API_DEVENV_URL : EnvConstant.API_RELEASE_URL;
    }

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankService.class);
                }
            }
        }
        return gankService;
    }
}
