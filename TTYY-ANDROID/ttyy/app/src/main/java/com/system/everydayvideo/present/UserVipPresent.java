package com.system.everydayvideo.present;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.UserRecharge;
import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.user.UserVipActivity;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserVipPresent extends BasePresent<UserVipActivity> {
    final static String TAG = "LoginPresent";

    public void loadUserVip(final int page, final int pageSize,int versionCode) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        objectMap.put("apptype","android");
        objectMap.put("appversion",versionCode+"");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getUserVip(requestBody)
                .compose(XApi.<UserVipBean>getApiTransformer())
                .compose(XApi.<UserVipBean>getScheduler())
                .compose(getV().<UserVipBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserVipBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(UserVipBean gankResults) {
                        getV().setUserVip(gankResults, page);
                    }
                });
    }

    public void loadUserRecharge(int productId,int versionCode) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("productId", productId + "");
        objectMap.put("type", "alipay");
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        objectMap.put("apptype","android");
        objectMap.put("appversion",versionCode+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(), gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getUserRecharge(requestBody)
                .compose(XApi.<UserRecharge>getApiTransformer())
                .compose(XApi.<UserRecharge>getScheduler())
                .compose(getV().<UserRecharge>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserRecharge>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                    @Override
                    public void onNext(UserRecharge gankResults) {
                        getV().setUserRecharge(gankResults);
                    }
                });
    }

}
