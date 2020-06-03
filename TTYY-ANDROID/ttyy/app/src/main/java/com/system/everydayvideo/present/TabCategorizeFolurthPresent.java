package com.system.everydayvideo.present;

import android.view.View;

import com.google.gson.Gson;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.App;
import com.system.everydayvideo.Bean.BaseResponse;
import com.system.everydayvideo.Bean.ConversionCodeBean;
import com.system.everydayvideo.Bean.LoginBean;
import com.system.everydayvideo.Bean.UserOfficialBean;
import com.system.everydayvideo.Bean.UserShareBean;
import com.system.everydayvideo.Bean.UstomerService;
import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.net.Api;
import com.system.everydayvideo.ui.fragment.TabCategorizeFourthFragment;
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

public class TabCategorizeFolurthPresent extends BasePresent<TabCategorizeFourthFragment> {

/*    public void loadHomeUserState() {
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
                        getV().setHomeUserState(gankResults);
                    }
                });
    }*/

    public void loadConversionCode(String code) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        objectMap.put("code",code);
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getConversionCode(requestBody)
                .compose(XApi.<ConversionCodeBean>getApiTransformer())
                .compose(XApi.<ConversionCodeBean>getScheduler())
                .compose(getV().<ConversionCodeBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<ConversionCodeBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(ConversionCodeBean gankResults) {
                        getV().setConversionCode(gankResults);
                    }
                });
    }


    public void loadUserShare() {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getUserShare(requestBody)
                .compose(XApi.<UserShareBean>getApiTransformer())
                .compose(XApi.<UserShareBean>getScheduler())
                .compose(getV().<UserShareBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserShareBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(UserShareBean gankResults) {
                        getV().setUserShare(gankResults);
                    }
                });
    }

    public void loadLogOutCode() {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getLogOutCode(requestBody)
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
                        getV().setLogOutCode(gankResults);
                    }
                });
    }


    public void loadUstomerService() {

        Api.getGankService().getUstomerServiceView()
                .compose(XApi.<UstomerService>getApiTransformer())
                .compose(XApi.<UstomerService>getScheduler())
                .compose(getV().<UstomerService>bindToLifecycle())
                .subscribe(new ApiSubscriber<UstomerService>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(UstomerService gankResults) {
                        getV().setUstomerService(gankResults);
                    }
                });
    }

    public void loadUserOfficial() {
        Api.getGankService().getUserOfficial()
                .compose(XApi.<UserOfficialBean>getApiTransformer())
                .compose(XApi.<UserOfficialBean>getScheduler())
                .compose(getV().<UserOfficialBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserOfficialBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        showError(error);
                    }

                    @Override
                    public void onNext(UserOfficialBean gankResults) {
                        getV().setUserOfficial(gankResults);
                    }
                });
    }


    public void showError(NetError error) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.AuthError:
                    getV().login = null;
                    getV().iv_user_name.setText("立即登录");
                    App.getInstance().logout();
                    getV().tv_userlogout.setVisibility(View.GONE);
                    getV().tv_user_official.setVisibility(View.GONE);
                    getV().v_zw1.setVisibility(View.GONE);
                    getV().tv_user_ustomer_service.setVisibility(View.GONE);
                    getV().v_zw2.setVisibility(View.GONE);
                    getV().tv_invite_code.setVisibility(View.VISIBLE);
                    getV().v_zw3.setVisibility(View.VISIBLE);
                    getV().tv_user_uszw1.setVisibility(View.INVISIBLE);
                    getV().v_zw4.setVisibility(View.INVISIBLE);
                    getV().tv_user_uszw2.setVisibility(View.INVISIBLE);
                    break;
                case NetError.OtherError:
                    ToastUtil.showToast(error.getMessage());
                    break;
                case NetError.NoConnectError:
                    ToastUtil.showToast("网络异常");
                    break;
                case NetError.BusinessError:
                    ToastUtil.showToast(error.getMessage());
                    break;
            }
        }
    }

    public void getProfile() {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getProfile(requestBody)
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
                        getV().setHomeUserState(gankResults);

                    }
                });
    }

  /*  public void loadFindInviteCode() {
        Api.getGankService().getFindeInviteCode()
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
                        getV().setFindInviteCode(gankResults);
                    }
                });
    }

    public void loadUserInviteCode() {
        Api.getGankService().getFindeInviteCode()
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
                        getV().setFindInviteCode(gankResults);
                    }
                });
    }*/

    public void loadUserInviteCodeAdd(String inviteCode) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("timeMillis",System.currentTimeMillis()+"");
        objectMap.put("inviteCode",inviteCode);
        String gson = new Gson().toJson(objectMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), AKOP.Encrypt(AKey.getString(),gson,AKey.getString1(),AKey.getString2()));
        Api.getGankService().getUserInviteCodeAdd(requestBody)
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
                        getV().setFindInviteCode(gankResults);
                    }
                });
    }
}
