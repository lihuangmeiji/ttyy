package cn.droidlover.xdroidmvp.net;

/**
 * Created by wanglei on 2016/12/26.
 */

public interface IResponse {
    boolean isNull();       //空数据

    boolean isAuthError();  //验证错误

    boolean isBizError();   //业务错误

    String getMsg();   //后台返回的信息

    String getCode();   //后台返回的code信息

}
