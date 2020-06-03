package com.system.everydayvideo.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.system.everydayvideo.Bean.VideoFilterBean;
import com.system.everydayvideo.Bean.VideoPlayBean;
import com.system.everydayvideo.BuildConfig;
import com.system.everydayvideo.R;
import com.system.everydayvideo.adapter.LineSelectAdapter;
import com.system.everydayvideo.present.HomeDetailedPresent;
import com.system.everydayvideo.widget.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;


public class HomeDetailedActivity extends XActivity<HomeDetailedPresent>  {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    private int videoPlatId = 0;

    private String videoTitle = "";

    private String url = null;

    private int isPlayPage = 0;

    private int playing = 0;

    String videoDetailUrl = null;

    private VideoFilterBean.VideoFilter videoFilter = null;

    private VideoPlayBean.VideoPlay videoPlay = null;

    private void LOG(String tag, String msg){
        if(BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }


    public void setVideoFilter(VideoFilterBean.VideoFilter videoFilter) {
        this.videoFilter = videoFilter;
    }

    public void setVideoPlay(VideoPlayBean.VideoPlay videoPlay) {
        this.videoPlay = videoPlay;
    }

    public void stopPlaying() {
        if(isPlayPage == 0) {
            playing = 0;
        }
    }

    public void startPlaying() {
        if(isPlayPage == 0) {
            playing = 1;
        }
    }

    public boolean isPlaying(){
        return playing == 1;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        videoPlatId = getIntent().getIntExtra("videoPlatId", 0);
        url = getIntent().getStringExtra("weburl");
        videoDetailUrl= getIntent().getStringExtra("videoDetailUrl");
        videoTitle= getIntent().getStringExtra("videoTitle");
        isPlayPage = getIntent().getIntExtra("isPlayPage", 0);
        if(isPlayPage==1){
            toolbarTitle.setText("播放中");
            toolbar.setNavigationIcon(R.mipmap.ic_return);
            toolbar.setVisibility(View.VISIBLE);
            iv_right.setVisibility(View.VISIBLE);
        }else{
            toolbar.setVisibility(View.GONE);
            iv_right.setVisibility(View.GONE);
        }
        if(videoPlatId > 0 && isPlayPage == 0) {
            getP().getVideoFilter(videoPlatId);
        }
        initWebView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_detailed;
    }

    @Override
    public HomeDetailedPresent newP() {
        return  new HomeDetailedPresent();
    }


    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {

            /*** 视频播放相关的方法 **/

            @Override
            public View getVideoLoadingProgressView() {
                if(isPlayPage == 1) {
                    FrameLayout frameLayout = new FrameLayout(context);
                    frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                    return frameLayout;
                }else{
                    return super.getVideoLoadingProgressView();
                }
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if(isPlayPage == 1) {
                    showCustomView(view, callback);
                }else{
                    super.onShowCustomView(view, callback);
                }
            }

            @Override
            public void onHideCustomView() {
                if(isPlayPage == 1) {
                    hideCustomView();
                }else{
                    super.onHideCustomView();
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                result.confirm();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //LOG("PlayPage","onProgressChanged,url=" + url);
                super.onProgressChanged(view, newProgress);

            }

            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(view !=null && isPlayPage == 0) {
                    LOG("onReceivedTitle","onReceivedTitle,url=" + view.getUrl());
                    videoTitle = title;
                    preOpenPlayActivity(view.getUrl());
                }
            }
        });

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setUseWideViewPort(true); // 关键点
        webView.getSettings().setAllowFileAccess(true); // 允许访问文件
//        @TargetApi(Build.VERSION_CODES.O)
//        webView.getSettings().setSafeBrowsingEnabled(true);
        //webView.getSettings().setSupportZoom(true); // 支持缩放
        webView.getSettings().setLoadWithOverviewMode(true);

        if(isPlayPage == 1) {
            webView.getSettings().setUserAgentString("ANDROID");
        }else{
            webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 6.0.1; Redmi Note 3 Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36");
        }
        webView.setWebViewClient(new xWebViewClientent());

        if(url !=null) {
            webView.loadUrl(url);
        }

    }

    private boolean matchUrl(String url, String pattern){
        boolean match = Pattern.matches(pattern, url);
        return match;
    }

    private void preOpenPlayActivity(String videoPalyUrl){
        if(isPlayPage == 0 && videoFilter!=null && !TextUtils.isEmpty(videoFilter.getVideoDetailPattern())) {
            boolean isVideoPage = matchUrl(videoPalyUrl, videoFilter.getVideoDetailPattern());
            if (isVideoPage) {
                videoDetailUrl = videoPalyUrl.replace("http://m.iqiyi.com", "https://www.iqiyi.com");
                synchronized (this) {
                    if (!isPlaying()) {
                        startPlaying();
                        getP().getRealVideoPalyUrl(0, videoPlatId, videoDetailUrl, videoTitle);
                    }
                }
            }
        }
    }

    public void OpenPlayActivity(VideoPlayBean.VideoPlay videoPlay){
        HomeDetailedActivity.launch(context, videoPlatId, videoPlay.getVideoPlayUrl(),videoDetailUrl, videoTitle, 1);
        //切换线路
        if(isPlayPage==1){
            finish();
        }
    }


    private  String injectIsParams(String url) {
        LOG("WEB","injectIsParams,url="+url);

        if (!TextUtils.isEmpty(url)) {
            LOG("WEB","url="+url);

            preOpenPlayActivity(url);
        }

        return url;
    }


    public class xWebViewClientent extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LOG("WEB","shouldOverrideUrlLoading,url="+url);
            return false;
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, final WebResourceRequest request) {
            LOG("WEB","shouldInterceptRequest,url="+request.getUrl());

            if(request!=null && request.getUrl() !=null && request.getUrl().toString().endsWith("loading_wap.gif")){
                WebResourceResponse response = null;
                try {
                    response = new WebResourceResponse("image/gif","UTF-8",getAssets().open("loading.gif"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            }

            if (request != null && request.getUrl() != null) {
                String scheme = request.getUrl().getScheme().trim();
                if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
                    return super.shouldInterceptRequest(view, new WebResourceRequest() {
                        @Override
                        public Uri getUrl() {
                            String url = injectIsParams(request.getUrl().toString());
                            return Uri.parse(url);
                        }

                        @SuppressLint("NewApi")
                        @Override
                        public boolean isForMainFrame() {
                            return request.isForMainFrame();
                        }

                        @SuppressLint("NewApi")
                        @Override
                        public boolean hasGesture() {
                            return request.hasGesture();
                        }

                        @SuppressLint("NewApi")
                        @Override
                        public String getMethod() {
                            return request.getMethod();
                        }

                        @SuppressLint("NewApi")
                        @Override
                        public Map<String, String> getRequestHeaders() {
                            return request.getRequestHeaders();
                        }

                        @SuppressLint("NewApi")
                        @Override
                        public boolean isRedirect(){
                            return request.isRedirect();
                        }
                    });
                }
            }
            return super.shouldInterceptRequest(view, request);
        }


        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if(url!=null && url.endsWith("loading_wap.gif")){
                WebResourceResponse response = null;
                try {
                    response = new WebResourceResponse("image/gif","UTF-8",getAssets().open("loading.gif"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            }

            if (!TextUtils.isEmpty(url) && Uri.parse(url).getScheme() != null) {
                String scheme = Uri.parse(url).getScheme().trim();
                if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
                    String urlAddress = injectIsParams(url);
                    return super.shouldInterceptRequest(view, urlAddress);
                }
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.cancel();
        }
    }

    public static void launch(Activity activity,  int videoPlatId, String url) {
        launch(activity, videoPlatId, url, null, null, 0);
    }

    public static void launch(Activity activity, int videoPlatId, String url, String videoDetailUrl, String videoTitle, int isPlayPage) {
        Router.newIntent(activity)
                .to(HomeDetailedActivity.class)
                .putInt("videoPlatId", videoPlatId)
                .putString("weburl", url)
                .putString("videoDetailUrl", videoDetailUrl)
                .putString("videoTitle", videoTitle)
                .putInt("isPlayPage", isPlayPage)
                .launch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopPlaying();
        try {
            if (webView != null) {
                webView.onResume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (webView != null) {
                webView.onPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({
            R.id.toolbar,
            R.id.iv_right
    })
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;

            case R.id.iv_right:
                new LineSelectView(context,iv_right);
                break;
        }
    }


    public class LineSelectView extends PopupWindow {

        public LineSelectView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.line_select_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            XRecyclerView rv_screen = (XRecyclerView) view.findViewById(R.id.rv_lin_selet);
            TextView tv_qx=(TextView)view.findViewById(R.id.tv_qx);
            LineSelectAdapter  lineSelectAdapter=new LineSelectAdapter(context);

            List<String> stringList=new ArrayList<>();
            for (int i = 1; i < 6; i++) {
                stringList.add("线路"+i);
            }
            lineSelectAdapter.addData(stringList);
            rv_screen.setAdapter(lineSelectAdapter);
            RelativeLayout rel_orderload = (RelativeLayout) view.findViewById(R.id.rel_lin_selet_orderload);
            rv_screen.setLayoutManager(new LinearLayoutManager(mContext));
            rv_screen.addItemDecoration(new GridDividerItemDecoration(2,context.getResources().getColor(R.color.textProfileGray)));
            lineSelectAdapter.setRecItemClick(new RecyclerItemCallback<String, LineSelectAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, String model, int tag, LineSelectAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    getP().getRealVideoPalyUrl(position, videoPlatId, videoDetailUrl, videoTitle);
                    dismiss();
                }
            });
            tv_qx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }




    /**
     * 视频播放全屏
     **/
    private View customView = null;

    private FullscreenHolder fullscreenContainer = null;

    private WebChromeClient.CustomViewCallback customViewCallback = null;

    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        setStatusBarVisibility(false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        webView.setVisibility(View.INVISIBLE);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(context);
        fullscreenContainer.addView(view, WindowManager.LayoutParams.MATCH_PARENT);
        decor.addView(fullscreenContainer, WindowManager.LayoutParams.MATCH_PARENT);
        customView = view;
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
        if (customView != null) {
            hideCustomView();
        }  else {
            super.onBackPressed();
        }
    }

}

