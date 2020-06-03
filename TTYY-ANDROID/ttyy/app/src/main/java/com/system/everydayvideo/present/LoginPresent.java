package com.system.everydayvideo.present;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.LoginBean;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.account.LoginActivity;
import com.system.everydayvideo.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginPresent extends BasePresent<LoginActivity> {
    final static String TAG = "LoginPresent";

    public void loadLogin(String phone,String pwd) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("mobile", phone);
        objectMap.put("password", MD5Util.md5(pwd));
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getLogin(requestBody)
                .compose(XApi.<LoginBean>getApiTransformer())
                .compose(XApi.<LoginBean>getScheduler())
                .compose(getV().<LoginBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(LoginBean gankResults) {
                          getV().setLogin(gankResults);
                    }
                });
    }

}
