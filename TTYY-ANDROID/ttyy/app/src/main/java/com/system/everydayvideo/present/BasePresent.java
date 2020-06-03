package com.system.everydayvideo.present;

import com.system.everydayvideo.ui.account.LoginActivity;
import com.system.everydayvideo.util.ToastUtil;

import cn.droidlover.xdroidmvp.mvp.IView;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.NetError;

public abstract class BasePresent<V extends IView> extends XPresent<V> {

    public void showError(NetError error) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.AuthError:
                    ToastUtil.showToast(error.getMessage());
                    LoginActivity.launch();
                    break;
                case NetError.BusinessError:
                    ToastUtil.showToast(error.getMessage());
                    break;
                case NetError.OtherError:
                    ToastUtil.showToast("系统异常");
                    break;
                case NetError.NoConnectError:
                    ToastUtil.showToast("网络异常");
                    break;
            }
        }
    }
}
