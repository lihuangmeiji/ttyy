package com.system.everydayvideo.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.system.everydayvideo.AKey;
import com.system.everydayvideo.App;
import com.system.everydayvideo.Bean.LoginBean;
import com.system.everydayvideo.R;
import com.system.everydayvideo.present.LoginPresent;
import com.system.everydayvideo.util.ConstUtils;
import com.system.everydayvideo.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

public class LoginActivity extends XActivity<LoginPresent> implements TextWatcher {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_phone)
    ClearEditText et_phone;
    @BindView(R.id.et_pwd)
    ClearEditText et_pwd;
    @BindView(R.id.tv_login)
    TextView tv_login;

    int logintype=0;

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbarTitle.setText("登陆");
        et_phone.addTextChangedListener(this);
        et_pwd.addTextChangedListener(this);
        logintype=getIntent().getIntExtra("logintype",0);
        tv_login.setEnabled(false);
        tv_login.setSelected(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent();
    }

    public static void launch() {
        if(App.getMainActivity() !=null) {
            Router.newIntent(App.getMainActivity())
                    .to(LoginActivity.class)
                    .launch();
        }
    }

//    public static void launch(Activity activity) {
//        Router.newIntent(activity)
//                .to(LoginActivity.class)
//                .launch();
//    }

    @OnClick({
            R.id.tv_register,
            R.id.tv_forgot_password,
            R.id.tv_login
    })
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                RegisterActivity.launch(context);
                break;
            case R.id.tv_forgot_password:
                ForgotPasswordActivity.launch(context);
                break;
            case R.id.tv_login:
                getP().loadLogin(et_phone.getText().toString(),et_pwd.getText().toString());
                break;
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(et_phone.getText().toString().trim())
                && !TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
            tv_login.setEnabled(true);
            tv_login.setSelected(true);
        } else {
            tv_login.setEnabled(false);
            tv_login.setSelected(false);
        }
    }

    public  void setLogin(LoginBean gankResults){
        LoginBean.Login login = JSON.parseObject(AKOP.Decrypt(AKey.getString(),gankResults.getData(),AKey.getString1(),AKey.getString2()), LoginBean.Login.class);
        App.getInstance().saveCurrentPersion(login);
        if(logintype==1){
            Intent intent = new Intent();
            setResult(1, intent);
        }
        Intent intents = new Intent("updateuser");
        sendBroadcast(intents);
        finish();
    }
}
