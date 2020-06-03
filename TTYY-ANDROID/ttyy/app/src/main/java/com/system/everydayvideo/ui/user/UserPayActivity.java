package com.system.everydayvideo.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.system.everydayvideo.R;
import com.system.everydayvideo.ui.account.RegisterActivity;
import com.system.everydayvideo.util.ToastUtil;
import com.system.everydayvideo.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

public class UserPayActivity extends XActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.web_userpay)
    WebView web_userpay;
    Handler handler = new Handler();
    String paycontent;
    @Override
    public void initData(Bundle savedInstanceState) {
        toolbarTitle.setText("用户支付");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        paycontent=getIntent().getStringExtra("paycontent");
        WebSettings ws = web_userpay.getSettings();
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setBuiltInZoomControls(false);//  隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//  排版适应屏幕
        ws.setUseWideViewPort(false);//  可任意比例缩放
        ws.setLoadWithOverviewMode(true);//  setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);//  保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setLoadsImagesAutomatically(true) ;//支持自动加载图片
        ws.setGeolocationEnabled(true);//  启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");//  设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        //web_userpay.setWebViewClient(new xWebViewClientent());
         //web_userpay.loadDataWithBaseURL("",paycontent, "text/html; charset=UTF-8", null,null);
        web_userpay.setWebChromeClient(new WebChromeClient());
        web_userpay.loadUrl(paycontent);
    }


    public static void launch(Activity activity,String paycontent) {
        Router.newIntent(activity)
                .to(UserPayActivity.class)
                .putString("paycontent",paycontent)
                .launch();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_pay;
    }

    @Override
    public Object newP() {
        return null;
    }

    public class xWebViewClientent extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            if (url == null)
                return false;
            try {
                if (url.startsWith("upwrp://")
                    //其他自定义的scheme
                        ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else if (url.contains("callback.action?transNumber")) {
                    finish();
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return false;
            }

            //处理http和https开头的url
            web_userpay.loadUrl(url);
            return true;
        }
    }

    @OnClick({
            R.id.toolbar
    })
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;

        }
    }
}
