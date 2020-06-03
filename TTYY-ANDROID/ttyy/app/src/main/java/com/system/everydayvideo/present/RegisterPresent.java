package com.system.everydayvideo.present;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.BaseResponse;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.account.RegisterActivity;
import com.system.everydayvideo.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterPresent extends BasePresent<RegisterActivity> {
    final static String TAG = "RegisterPresent";

    public void loadRegister(String phone, String pwd,String code) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("mobile", phone + "");
        objectMap.put("password", MD5Util.md5(pwd));
        objectMap.put("code", code);
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getRegister(requestBody)
                .compose(XApi.<BaseResponse>getApiTransformer())
                .compose(XApi.<BaseResponse>getScheduler())
                .compose(getV().<BaseResponse>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResponse>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(BaseResponse gankResults) {
                        getV().setRegister(gankResults);
                    }
                });
    }

    public void getForgotPasswordSms(String phone) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("mobile", phone + "");
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getForgotPasswordSms(requestBody)
                .compose(XApi.<BaseResponse>getApiTransformer())
                .compose(XApi.<BaseResponse>getScheduler())
                .compose(getV().<BaseResponse>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResponse>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(BaseResponse gankResults) {
                        getV().setForgotPasswordSms(gankResults);
                    }
                });
    }
}
