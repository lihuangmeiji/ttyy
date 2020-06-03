package com.system.everydayvideo.present;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.App;
import com.system.everydayvideo.Bean.LoginBean;
import com.system.everydayvideo.Bean.VersionBean;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.MainActivity;
import com.system.everydayvideo.ui.account.LoginActivity;
import com.system.everydayvideo.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class MainPresent extends BasePresent<MainActivity> {
    final static String TAG = "MainPresent";

    public void loadVersionData() {
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), account);
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis", System.currentTimeMillis() + "");
        objectMap.put("osType", "android");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),  gson);
        Api.getGankService().getVersionList(requestBody)
                .compose(XApi.<VersionBean>getApiTransformer())
                .compose(XApi.<VersionBean>getScheduler())
                .compose(getV().<VersionBean>bindToLifecycle())

                .subscribe(new ApiSubscriber<VersionBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(VersionBean gankResults) {
                        getV().showVersion(gankResults);
                    }
                });
    }



//    public void showError(NetError error) {
//        super.showError(error);
//        getV().finish();
//    }


}
