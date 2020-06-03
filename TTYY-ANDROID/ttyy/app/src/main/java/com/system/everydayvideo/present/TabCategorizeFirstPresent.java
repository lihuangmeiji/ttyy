package com.system.everydayvideo.present;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.HomeBannerBean;
import com.system.everydayvideo.Bean.HomeVideo;
import com.system.everydayvideo.Bean.HomeVideoListBean;
import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.fragment.TabCategorizeFirstFragment;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TabCategorizeFirstPresent extends BasePresent<TabCategorizeFirstFragment> {
    final static String TAG = "FlickerScreenPresent";

    public void loadHomeBannersData() {
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), account);
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getHomeBanners(requestBody)
                .compose(XApi.<HomeBannerBean>getApiTransformer())
                .compose(XApi.<HomeBannerBean>getScheduler())
                .compose(getV().<HomeBannerBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<HomeBannerBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(HomeBannerBean gankResults) {
                        getV().setHomeBanners(gankResults);
                    }
                });
    }

    public void loadHomeVideoData(final int page, final int pageSize) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("pageNum", page + "");
        objectMap.put("pageSize", pageSize + "");
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getHomeVideoList(requestBody)
                .compose(XApi.<HomeVideoListBean>getApiTransformer())
                .compose(XApi.<HomeVideoListBean>getScheduler())
                .compose(getV().<HomeVideoListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<HomeVideoListBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(HomeVideoListBean gankResults) {
                        getV().setHomeVideoList(gankResults, page);
                    }
                });
    }

    public void loadHomeUserState(final HomeVideo model) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getHomeUserState(requestBody)
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
                        getV().setHomeUserState(gankResults,model);
                    }
                });
    }


}
