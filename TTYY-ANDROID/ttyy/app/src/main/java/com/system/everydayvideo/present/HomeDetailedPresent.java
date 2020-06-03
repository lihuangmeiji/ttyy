package com.system.everydayvideo.present;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.VideoFilterBean;
import com.system.everydayvideo.Bean.VideoPlayBean;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.HomeDetailedActivity;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeDetailedPresent extends BasePresent<HomeDetailedActivity> {
   static String TAG = "HomeDetailedActivity";

    public void getVideoFilter(int videoPlatId) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("videoPlatId", String.valueOf(videoPlatId));
        objectMap.put("timeMillis",String.valueOf(System.currentTimeMillis()));
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getVideoFilter(requestBody)
                .compose(XApi.<VideoFilterBean>getApiTransformer())
                .compose(XApi.<VideoFilterBean>getScheduler())
                .compose(getV().<VideoFilterBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<VideoFilterBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(VideoFilterBean gankResults) {
                        VideoFilterBean.VideoFilter videoFilter = JSON.parseObject(AKOP.Decrypt(AKey.getString(), gankResults.getData(),AKey.getString1(),AKey.getString2()), VideoFilterBean.VideoFilter.class);
                        getV().setVideoFilter(videoFilter);
                    }
                });
    }



    public void getRealVideoPalyUrl(int channelId, int videoPlatId, String videoDetailUrl, String videoName) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("channelId", String.valueOf(channelId));
        objectMap.put("videoPlatId", String.valueOf(videoPlatId));
        objectMap.put("videoDetailUrl", videoDetailUrl);
        objectMap.put("videoName", videoName);
        objectMap.put("timeMillis",String.valueOf(System.currentTimeMillis()));
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getVideoVipPlayUrl(requestBody)
                .compose(XApi.<VideoPlayBean>getApiTransformer())
                .compose(XApi.<VideoPlayBean>getScheduler())
                .compose(getV().<VideoPlayBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<VideoPlayBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(VideoPlayBean gankResults) {
                        VideoPlayBean.VideoPlay videoPlay = JSON.parseObject(AKOP.Decrypt(AKey.getString(), gankResults.getData(),AKey.getString1(),AKey.getString2()), VideoPlayBean.VideoPlay.class);
                        //getV().setVideoPlay(videoPlay);
                        getV().OpenPlayActivity(videoPlay);
                    }
                });
    }
}
