package com.system.everydayvideo.present;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.BaseResponse;
import com.system.everydayvideo.Bean.MenuListBean;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.FlickerScreenActivity;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class FlickerScreenPresent extends BasePresent<FlickerScreenActivity> {
    final static String TAG="FlickerScreenPresent";
    public void loadData() {
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), account);
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getMenuList(requestBody)
                .compose(XApi.<MenuListBean>getApiTransformer())
                .compose(XApi.<MenuListBean>getScheduler())
                .compose(getV().<MenuListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<MenuListBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(MenuListBean gankResults) {
                        getV().setMenuList(gankResults);
                    }
                });
    }



    public void showError(NetError error) {
        super.showError(error);
        getV().finish();
    }
}
