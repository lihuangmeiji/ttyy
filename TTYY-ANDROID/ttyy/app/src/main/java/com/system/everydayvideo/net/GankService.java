package com.system.everydayvideo.net;


import com.system.everydayvideo.Bean.BaseResponse;
import com.system.everydayvideo.Bean.ConversionCodeBean;
import com.system.everydayvideo.Bean.HomeBannerBean;
import com.system.everydayvideo.Bean.HomeVideoListBean;
import com.system.everydayvideo.Bean.LoginBean;
import com.system.everydayvideo.Bean.MenuListBean;
import com.system.everydayvideo.Bean.UserOfficialBean;
import com.system.everydayvideo.Bean.UserRecharge;
import com.system.everydayvideo.Bean.UserShareBean;
import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.Bean.UstomerService;
import com.system.everydayvideo.Bean.VersionBean;
import com.system.everydayvideo.Bean.VideoFilterBean;
import com.system.everydayvideo.Bean.VideoPlayBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {


    //主页菜单
    @POST(Constant.MENU_LIST)
    Flowable<MenuListBean> getMenuList(@Body RequestBody request);

    //Banners
    @POST(Constant.HOME_BANNERS)
    Flowable<HomeBannerBean> getHomeBanners(@Body RequestBody request);

    //首页列表
    @POST(Constant.HOME_VIDEO_LIST)
    Flowable<HomeVideoListBean> getHomeVideoList(@Body RequestBody request);

    //查询用户状态
    @POST(Constant.HOME_USER_STATE)
    Flowable<UserVipBean> getHomeUserState(@Body RequestBody request);

    //用户登录
    @POST(Constant.USER_LOGIN)
    Flowable<LoginBean> getLogin(@Body RequestBody request);

    //用户注册
    @POST(Constant.USER_REGISTER)
    Flowable<BaseResponse> getRegister(@Body RequestBody request);

    //发送短信
    @POST(Constant.USER_SMS_SEND)
    Flowable<BaseResponse> getForgotPasswordSms(@Body RequestBody request);

    //修改密码
    @POST(Constant.USER_FORGOT_PASSWORD)
    Flowable<BaseResponse> getForgotPassword(@Body RequestBody request);

    //获取用户信息
    @POST(Constant.USER_PROFILE)
    Flowable<LoginBean> getProfile(@Body RequestBody request);

    //Vip列表
    @POST(Constant.USER_VIP)
    Flowable<UserVipBean> getUserVip(@Body RequestBody request);

    //充值
    @POST(Constant.USER_RECHARGE)
    Flowable<UserRecharge> getUserRecharge(@Body RequestBody request);


    @POST("/videoFilter/findFilterUrlByPlatId")
    Flowable<VideoFilterBean> getVideoFilter(@Body RequestBody request);

    @POST("/video/vip/url/get")
    Flowable<VideoPlayBean> getVideoVipPlayUrl(@Body RequestBody request);

    //Banners
    @POST()
    Flowable<HomeBannerBean> getTabTwoBanners(@Body RequestBody request);

    //首页列表
    @POST()
    Flowable<HomeVideoListBean> getTabTwoVideoList(@Body RequestBody request);

    //首页列表
    @POST(Constant.FINDVERSION)
    Flowable<VersionBean> getVersionList(@Body RequestBody request);


    //兑换码
    @POST(Constant.CONVERSIONCODE)
    Flowable<ConversionCodeBean> getConversionCode(@Body RequestBody request);

    //退出登录
    @POST(Constant.LOGINOUTCODE)
    Flowable<BaseResponse> getLogOutCode(@Body RequestBody request);


    @POST(Constant.USERSHARE)
    Flowable<UserShareBean> getUserShare(@Body RequestBody request);


    @POST(Constant.USTOMERSERVICE)
    Flowable<UstomerService> getUstomerServiceView();

    @POST(Constant.USEROFFICIAL)
    Flowable<UserOfficialBean> getUserOfficial();

    @POST(Constant.FINDEINVITECODE)
    Flowable<BaseResponse> getFindeInviteCode();

    @POST(Constant.USERINVITECODE)
    Flowable<BaseResponse> getUserInviteCode();

    @POST(Constant.USERINVITECODEADD)
    Flowable<BaseResponse> getUserInviteCodeAdd(@Body RequestBody request);
}
