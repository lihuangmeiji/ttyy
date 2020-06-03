package com.system.everydayvideo.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.system.everydayvideo.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog extends AlertDialog {
    private static LoadingDialog loadingDialog;
    private AVLoadingIndicatorView avi;

    public static LoadingDialog getInstance(Context context) {
        loadingDialog = new LoadingDialog(context, R.style.MyDialogStyle); //设置AlertDialog背景透明
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context,themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_loading);
        avi = (AVLoadingIndicatorView)this.findViewById(R.id.avi);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
