package com.system.everydayvideo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.system.everydayvideo.Bean.LoginBean;
import com.system.everydayvideo.net.Constant;
import com.system.everydayvideo.util.CommonUtil;
import com.system.everydayvideo.util.ConstUtils;
import com.system.everydayvideo.util.DataHelper;
import com.system.everydayvideo.util.DataKeeper;
import com.system.everydayvideo.util.DataManager;
import com.system.everydayvideo.util.SpUtil;
import com.system.everydayvideo.util.StringUtil;
import com.system.everydayvideo.util.SystemUtil;
import com.system.everydayvideo.util.ToastUtil;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatService;
import com.xsj.crasheye.Crasheye;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by wanglei on 2016/12/31.
 */
public class App extends Application {


    private static final String TAG = "App";
    private static App context;

    private static Activity mainActivity;

    private int myScreenWidth;
    private int myScreenHeight;

    private String latitude;

    private String longitude;

    private String city;

    public static Activity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(Activity mainActivity) {
        App.mainActivity = mainActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        try {
            // 第三个参数必须为：com.tencent.stat.common.StatConstants.VERSION
            StatService.startStatService(this, "A83B13TEERTA",
                    com.tencent.stat.common.StatConstants.VERSION);
            Log.d("MTA","MTA初始化成功");
        } catch (MtaSDkException e) {
            // MTA初始化失败
            Log.d("MTA","MTA初始化失败"+e);
        }
        //初始化ToastUtil
        ToastUtil.register(this);
        //初始化SpUtil
        SpUtil.init(this);
        //初始化DataKeeper
        DataKeeper.init(this);
        initScreenWidth();
        WebView mWebView = new WebView(context);
        mWebView.getSettings().getUserAgentString();//返回的string对象分为两部分，第一部分显示的是ua信息，第二部分显示的webkit版本信息。

        XApi.registerProvider(this,AKey.getString3(),new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                Interceptor[] interceptor = new Interceptor[2];
                Interceptor interceptor1 = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        //这里获取请求返回的cookie
                        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                            final StringBuffer cookieBuffer = new StringBuffer();
                            for (int i = 0; i < originalResponse.headers("Set-Cookie").size(); i++) {
                                cookieBuffer.append(originalResponse.headers("Set-Cookie").get(i)).append(";");
                            }
                            //保存cookie数据
                            SpUtil.setParam(Constant.COOKIE, cookieBuffer.toString());
                        }
                        return originalResponse;
                    }
                };
                Interceptor interceptor2 = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        String cookie = SpUtil.getParam(Constant.COOKIE, "").toString();
                        latitude= DataHelper.getStringSF(context,"lat");
                        longitude=DataHelper.getStringSF(context,"lng");
                        city=DataHelper.getStringSF(context,"locality");
                        builder.addHeader("apptype","android");
                        builder.addHeader("appversion", ConstUtils.getVersioncode(context)+"");
                        builder.addHeader("timestamp", System.currentTimeMillis()+"");
                        String maptype="GCJ-02";
                        if(latitude==null&&longitude==null){
                            maptype=null;
                        }
                        if(city!=null){
                            city=java.net.URLEncoder.encode(city, "utf-8");
                        }
                        String ua= SystemUtil.getSystemVersion()+"|okhttp 3.3.6|android,"+SystemUtil.getSystemVersion()+"|"+SystemUtil.getDeviceBrand()+","+SystemUtil.getSystemModel()+"|"+myScreenWidth+","+myScreenHeight+"|"+longitude+","+latitude+","+maptype+"|"+city;
                        builder.addHeader("user-agent", ua);
                        HttpUrl  httpUrl=chain.request().url();
                        if(httpUrl.encodedPath().contains("/version/findVersionByType")){
                            builder.addHeader("uri-path", "/version/findVersionByType");
                        }
                        if (!TextUtils.isEmpty(cookie)) {
                            builder.addHeader("Cookie", cookie);
                        }
                        return chain.proceed(builder.build());
                    }
                };
                interceptor[0] = interceptor1;
                interceptor[1] = interceptor2;
                return interceptor;
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
               /* PersistentCookieStore persistentCookieStore=new PersistentCookieStore(context);
                CookieJarImpl cookieJar=new CookieJarImpl(persistentCookieStore);*/
         /*       ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));*/
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return new RequestHandler() {
                    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
                        if (CommonUtil.hasWifiProxy(context)) {
                            return null;
                        }
                        return request;
                    }

                    public Response onAfterRequest(Response response, Interceptor.Chain chain) {
                        return response;
                    }
                };
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }


    public static App getInstance() {
        return context;
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public long getCurrentPersionId() {
        currentPersion = getCurrentPersion();
        Log.d(TAG, "getCurrentPersionId  currentPersionId = " + (currentPersion == null ? "null" : currentPersion.getId()));
        return currentPersion == null ? 0 : currentPersion.getId();
    }

    /**
     * 获取当前用户phone
     *
     * @return
     */
    public String getCurrentPersionPhone() {
        currentPersion = getCurrentPersion();
        return currentPersion == null ? null : currentPersion.getMobile();
    }


    private static LoginBean.Login currentPersion = null;

    public LoginBean.Login getCurrentPersion() {
        if (currentPersion == null) {
            currentPersion = DataManager.getInstance().getCurrentPersion();
        }
        return currentPersion;
    }

    public void saveCurrentPersion(LoginBean.Login Persion) {
        if (Persion == null) {
            Log.e(TAG, "saveCurrentPersion  currentPersion == null >> return;");
            return;
        }
        if (Persion.getId() <= 0 && StringUtil.isNotEmpty(Persion.getNickname(), true) == false) {
            Log.e(TAG, "saveCurrentPersion  Persion.getId() <= 0" +
                    " && StringUtil.isNotEmpty(Persion.getName(), true) == false >> return;");
            return;
        }

        currentPersion = Persion;
        DataManager.getInstance().saveCurrentPersion(currentPersion);
    }

    public void logout() {
        currentPersion = null;
        DataManager.getInstance().saveCurrentPersion(currentPersion);
    }

    /**
     * 判断是否为当前用户
     *
     * @param PersionId
     * @return
     */
    public boolean isCurrentPersion(long PersionId) {
        return DataManager.getInstance().isCurrentPersion(PersionId);
    }

    public boolean isLoggedIn() {
        return getCurrentPersionId() > 0;
    }



    /**
     * 获取屏幕的参数，宽度和高度
     */
    private void initScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)
                this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        myScreenHeight = metrics.heightPixels;
        myScreenWidth = metrics.widthPixels;
    }

    public int getMyScreenWidth() {
        return myScreenWidth;
    }

    public void setMyScreenWidth(int myScreenWidth) {
        this.myScreenWidth = myScreenWidth;
    }

    public int getMyScreenHeight() {
        return myScreenHeight;
    }

    public void setMyScreenHeight(int myScreenHeight) {
        this.myScreenHeight = myScreenHeight;
    }

}
