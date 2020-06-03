package com.system.everydayvideo.util;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspcadmin on 2018/9/30.
 */

public class HttpModel {

    public static final String secret = "b0b12569e3c4219c79717e2f2b09b7b5";

    public static final String appKey = "dcdzsoft_2018";

    public static final String signMethod = "md5";

    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("appKey", appKey);
        map.put("format", "JSON");
        map.put("signMethod", signMethod);
    }

    private static Map<String, String> methodMap = new HashMap<>();

    static {
        //methodMap.put("/jf/wx/test/recv", "coordinates");
    }


    public static void loadTabTwoBannersData(Map<String,Object> params,String method, HttpUtil.ResponeCallBack callBack){
        doPost(params,method,callBack);
    }
    public static void doPost(final Map<String, Object> params, final String method, final HttpUtil.ResponeCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                callBack.respone(HttpUtil.post(method, params));
            }
        }).start();
    }
}
