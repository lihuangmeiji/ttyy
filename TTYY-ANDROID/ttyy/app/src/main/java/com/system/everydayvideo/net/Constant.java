package com.system.everydayvideo.net;

public interface Constant {
    String COOKIE = "cookie";
    /**
     * 登录
     */
    String  SIGNIN = "";

    /**
     * 静态登录
     */
    String LOGIN_ON_PWD = "";

    /**
     * 获取菜单
     */
    String MENU_LIST = "/menu/findList";

    /**
     * 获取BANNERS
     */
    String HOME_BANNERS = "/home/banners";

    /**
     * 获取首页列表
     */
    String HOME_VIDEO_LIST = "/home/videoPlats";

    /**
     * 获取用户状态
     */
    String HOME_USER_STATE = "/user/findIsVIP";

    /**
     * 登录
     */
    String USER_LOGIN= "/user/login";

    /**
     * 注册
     */
    String USER_REGISTER= "/user/register";

    /**
     * 短信
     */
    String USER_SMS_SEND= "/sms/send";

    /**
     * 修改密码
     */
    String USER_FORGOT_PASSWORD= "/user/resetPassword";

    /**
     * 注册
     */
    String USER_PROFILE= "/user/profile/get";

    /**
     * VIP列表
     */
    String USER_VIP= "/product/findVipList";

    /**
     * 充值
     */
    String USER_RECHARGE= "/pay/pay";

    /**
     * xiazai
     */
    String FINDVERSION="/version/findVersionByType";

    /**
     * BANNERS
     */
    String TABTWO_BANNERS="";
    /**
     * 列表
     */
    String TABTWO_LIST= "";

    /**
     * BANNERS
     */
    String TABTHREE_BANNERS= "";
    /**
     * 列表
     */
    String TABTHREE_LIST= "";

    /**
     * 兑换码
     */
    String CONVERSIONCODE= "/redeemcode/use";

    /**
     * 退出登录
     */
    String LOGINOUTCODE= "/user/logout";

    /**
     * 退出登录
     */
    String USERSHARE= "/user/share";

    /**
     * 退出登录 UstomerService
     */
    String USTOMERSERVICE= "/kefu/getKefu";

    /**
     * 群组
     */
    String USEROFFICIAL= "/officialGroup";

    /**
     * 是否填写过邀请码
     */
    String FINDEINVITECODE= "/user/findIsInviteCode";

    /**
     * 邀请码
     */
    String USERINVITECODE= "/user/inviteCode";

    /**
     * 邀请码添加
     */
    String USERINVITECODEADD= "/user/setInviteCode";
}
