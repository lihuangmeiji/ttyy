package com.system.everydayvideo.ui.account;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.system.everydayvideo.Bean.BaseResponse;
import com.system.everydayvideo.R;
import com.system.everydayvideo.present.ForgotPasswordPresent;
import com.system.everydayvideo.util.ConstUtils;
import com.system.everydayvideo.util.ToastUtil;
import com.system.everydayvideo.widget.ClearEditText;
import com.system.everydayvideo.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;

public class ForgotPasswordActivity extends XActivity<ForgotPasswordPresent> implements TextWatcher {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_phone)
    ClearEditText et_phone;
    @BindView(R.id.et_code)
    ClearEditText et_code;
    @BindView(R.id.et_pwd)
    ClearEditText et_pwd;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;

    private int countDownTime = 60;
    private static final int COUNT_DOWN = 1;
    private boolean isSendSuccess=false;
    LoadingDialog loadingDialog;
    @Override
    public void initData(Bundle savedInstanceState) {
        toolbarTitle.setText("找回密码");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        et_phone.addTextChangedListener(this);
        et_pwd.addTextChangedListener(this);
        et_code.addTextChangedListener(this);
        loadingDialog=LoadingDialog.getInstance(this);
        tv_forgot_password.setEnabled(false);
        tv_forgot_password.setSelected(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public ForgotPasswordPresent newP() {
        return new ForgotPasswordPresent();
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(ForgotPasswordActivity.class)
                .launch();
    }

    @OnClick({
            R.id.toolbar,
            R.id.tv_get_code,
            R.id.tv_forgot_password
    })
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_get_code:
                tv_get_code.setEnabled(false);
                getP().getForgotPasswordSms(et_phone.getText().toString().trim());
                break;
            case R.id.tv_forgot_password:
                getP().getForgotPassword(et_phone.getText().toString().trim(),et_code.getText().toString().trim(),et_pwd.getText().toString().trim());
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNT_DOWN:
                    handleCountMessage(countDownTime, COUNT_DOWN, tv_get_code);
                    break;
            }
        }
    };

    public void setForgotPasswordSms(BaseResponse gankResults) {
        loadingDialog.dismiss();
        ToastUtil.showToast("发送成功");
        isSendSuccess = true;
        countDownTime = 60;//下次点击时倒计时重新置为原倒计时数
        setCountDown(tv_get_code, COUNT_DOWN);
    }

    public void setForgotPassword(BaseResponse gankResults) {
        ToastUtil.showToast(gankResults.getMsg());
        finish();
    }

    /**
     * 设置倒计时
     */
    private void setCountDown(TextView tv, int code) {
        tv.setEnabled(false);
        if (code == COUNT_DOWN) {
            tv.setText(countDownTime + "s");
        }
        mHandler.sendEmptyMessageDelayed(code, 1000);
    }


    /**
     * 处理倒计时的msg
     *
     * @param time    对应哪个倒计时
     * @param msgCode 消息的code码
     * @param tv      哪个textview对应变化
     */
    private void handleCountMessage(int time, int msgCode, TextView tv) {
        if (msgCode == COUNT_DOWN) {
            countDownTime--;
        }
        time--;
        if (time > 0) {
            setCountDown(tv, msgCode);
        } else if (time == 0) {
            //防止在验证码倒计时结束后，按钮不可点击的问题
            if (!TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                tv_get_code.setEnabled(true);
            } else {
                tv_get_code.setEnabled(false);
            }
            isSendSuccess = false;
            tv.setText("重新发送验证码");
        } else {
            mHandler.removeMessages(msgCode);
        }
    }

    public void showError(NetError error) {
        loadingDialog.dismiss();
        if (error != null) {
            switch (error.getType()) {
                case NetError.AuthError:
                    ToastUtil.showToast(error.getMessage());
                    LoginActivity.launch();
                    break;
                case NetError.OtherError:
                    ToastUtil.showToast(error.getMessage());
                    break;
                case NetError.NoConnectError:
                    ToastUtil.showToast("网络异常");
                    break;
            }
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
        if (!TextUtils.isEmpty(et_phone.getText().toString().trim())&&!isSendSuccess) {
            tv_get_code.setEnabled(true);
        } else {
            tv_get_code.setEnabled(false);
        }
        if (!TextUtils.isEmpty(et_phone.getText().toString().trim())
                && !TextUtils.isEmpty(et_code.getText().toString().trim()) && !TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
            tv_forgot_password.setEnabled(true);
            tv_forgot_password.setSelected(true);
        } else {
            tv_forgot_password.setEnabled(false);
            tv_forgot_password.setSelected(false);
        }
    }
}
