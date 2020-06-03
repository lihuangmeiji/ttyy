package com.system.everydayvideo.ui.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.system.everydayvideo.AKey;
import com.system.everydayvideo.App;
import com.system.everydayvideo.Bean.BaseResponse;
import com.system.everydayvideo.Bean.ConversionCodeBean;
import com.system.everydayvideo.Bean.HomeVideo;
import com.system.everydayvideo.Bean.LoginBean;
import com.system.everydayvideo.Bean.UserFeaturesModel;
import com.system.everydayvideo.Bean.UserOfficialBean;
import com.system.everydayvideo.Bean.UserShareBean;
import com.system.everydayvideo.Bean.UstomerService;
import com.system.everydayvideo.BuildConfig;
import com.system.everydayvideo.R;
import com.system.everydayvideo.adapter.HomeVideoAdapter;
import com.system.everydayvideo.adapter.UserFeaturesAdapter;
import com.system.everydayvideo.present.TabCategorizeFolurthPresent;
import com.system.everydayvideo.ui.MainActivity;
import com.system.everydayvideo.ui.account.LoginActivity;
import com.system.everydayvideo.ui.user.UserVipActivity;
import com.system.everydayvideo.util.ImgUtils;
import com.system.everydayvideo.util.InstallApk;
import com.system.everydayvideo.util.SoftUpdate;
import com.system.everydayvideo.util.ToastUtil;
import com.system.everydayvideo.widget.DataCleanManager;
import com.system.everydayvideo.widget.GridDividerItemDecoration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class TabCategorizeFourthFragment extends XLazyFragment<TabCategorizeFolurthPresent> {
    @BindView(R.id.rel_user_login)
    LinearLayout rel_user_login;
    @BindView(R.id.iv_user_img)
    CircleImageView iv_user_img;
    @BindView(R.id.tv_user_vip)
    TextView tv_user_vip;
    @BindView(R.id.rel_uservip)
    RelativeLayout rel_uservip;
    @BindView(R.id.tv_userlogout)
    public TextView tv_userlogout;
    @BindView(R.id.iv_user_name)
    public TextView iv_user_name;
    @BindView(R.id.tv_user_ustomer_service)
    public TextView tv_user_ustomer_service;
    @BindView(R.id.tv_user_official)
    public TextView tv_user_official;
    @BindView(R.id.lin_thirdtab)
    public  LinearLayout lin_thirdtab;
    @BindView(R.id.v_zw1)
    public  View v_zw1;
    @BindView(R.id.v_zw2)
    public  View v_zw2;
    @BindView(R.id.v_zw3)
    public  View v_zw3;
    @BindView(R.id.v_zw4)
    public  View v_zw4;
    @BindView(R.id.tv_user_uszw1)
    public  TextView tv_user_uszw1;
    @BindView(R.id.tv_user_uszw2)
    public  TextView tv_user_uszw2;
    @BindView(R.id.tv_invite_code)
    public TextView tv_invite_code;
    public LoginBean.Login login;
    @BindView(R.id.tv_versionname)
    TextView tv_versionname;
    @BindView(R.id.user_features_layout)
    XRecyclerView  user_features_layout;
    UserFeaturesAdapter userFeaturesAdapter;

    private final int CLEAN_SUC = 1001;
    private final int CLEAN_FAIL = 1002;
    UserOfficialBean.UserOfficialModel userOfficialModel;
    UstomerService.UstomerServiceModel ustomerServiceModel;
    List<UserFeaturesModel> userFeaturesModelList;

    private String[] mTitles = {"兑换码", "观看记录","分享有奖","购买会员","常见问题","清除缓存","邀请码","我的邀请码","官方群组","专属客服"};


    private int[] mUnSelectIcons = {
            R.mipmap.usertypeimg1, R.mipmap.usertypeimg2,R.mipmap.usertypeimg3,R.mipmap.usertypeimg6,R.mipmap.usertypeimg4,R.mipmap.usertypeimg5,R.mipmap.usertypeimg9,R.mipmap.usertypeimg10,R.mipmap.usertypeimg7,R.mipmap.usertypeimg8};
    @Override
    public void initData(Bundle savedInstanceState) {
        getP().getProfile();
        getP().loadUserOfficial();
        getP().loadUstomerService();
        userrefresh();
        userFeaturesModelList=new ArrayList<>();
        for (int i = 0; i <mTitles.length ; i++) {
            UserFeaturesModel userFeaturesModel=new UserFeaturesModel();
            userFeaturesModel.setIco(mUnSelectIcons[i]);
            userFeaturesModel.setName(mTitles[i]);
            userFeaturesModelList.add(userFeaturesModel);
        }
        userFeaturesAdapter = new UserFeaturesAdapter(context);
        user_features_layout.gridLayoutManager(context, 3);
        user_features_layout.setAdapter(userFeaturesAdapter);
        user_features_layout.addItemDecoration(new GridDividerItemDecoration(1,context.getResources().getColor(R.color.textProfileGray)));
        userFeaturesAdapter.setRecItemClick(new RecyclerItemCallback<UserFeaturesModel, UserFeaturesAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, UserFeaturesModel model, int tag, UserFeaturesAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
            }
        });
        userFeaturesAdapter.addData(userFeaturesModelList);
        tv_versionname.setText("V"+ BuildConfig.VERSION_NAME);
        //getP().loadUserShare();
    }

    public static TabCategorizeFourthFragment newInstance() {
        TabCategorizeFourthFragment fragment = new TabCategorizeFourthFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_categorize_fourth;
    }

    @Override
    public TabCategorizeFolurthPresent newP() {
        return new TabCategorizeFolurthPresent();
    }

    @OnClick({
            R.id.tv_uservip,
            R.id.tv_userproblem,
            R.id.rel_user_login,
            R.id.tv_userAllCache,
            R.id.tv_userconversioncode,
            R.id.tv_userlogout,
            R.id.tv_usershare,
            R.id.rel_uservip,
            R.id.tv_useraboutus,
            R.id.tv_user_official,
            R.id.tv_user_ustomer_service,
            R.id.tv_invite_code
    })
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_uservip:
                login = App.getInstance().getCurrentPersion();
                if (App.getInstance().isLoggedIn()) {
                    UserVipActivity.launch(context);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("logintype", 1);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.tv_userproblem:
                break;
            case R.id.rel_user_login:
                if (App.getInstance().isLoggedIn() == false) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("logintype", 1);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.tv_userAllCache:
                clearAppCache();
                break;

            case R.id.tv_userconversioncode:
                if (App.getInstance().isLoggedIn()) {
                    new ConversionCodeView(context, iv_user_name);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("logintype", 1);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.tv_userlogout:
                if (App.getInstance().isLoggedIn()) {
                    getP().loadLogOutCode();
                } else {
                    ToastUtil.showToast("用户未登录");
                }
                break;
            case R.id.tv_usershare:
                if (App.getInstance().isLoggedIn()) {
                    getP().loadUserShare();
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("logintype", 1);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.rel_uservip:
                UserVipActivity.launch(context);
                break;
            case R.id.tv_useraboutus:
                new AboutUsView(context, iv_user_name);
                break;
            case R.id.tv_user_official:
                if (App.getInstance().isLoggedIn()) {
                    new UserOfficialView(context, iv_user_name);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("logintype", 1);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.tv_user_ustomer_service:
                if (App.getInstance().isLoggedIn()) {
                    new UstomerServiceView(context, iv_user_name);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("logintype", 1);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.tv_invite_code:
                if (App.getInstance().isLoggedIn()) {
                    new InviteCodeView(context, iv_user_name,login.getFillInviteCode());
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("logintype", 1);
                    startActivityForResult(intent, 1);
                }
                break;
        }
    }

    public void setHomeUserState(LoginBean gankResults) {
        rel_uservip.setVisibility(View.INVISIBLE);
        LoginBean.Login loginhq = JSON.parseObject(AKOP.Decrypt(AKey.getString(),gankResults.getData(),AKey.getString1(),AKey.getString2()), LoginBean.Login.class);
        if (loginhq==null) {
            iv_user_name.setText("立即登录");
            login = null;
            tv_userlogout.setVisibility(View.GONE);
            iv_user_img.setImageResource(R.mipmap.ic_launcher);
            App.getInstance().logout();
        } else {
            App.getInstance().saveCurrentPersion(loginhq);
            login = App.getInstance().getCurrentPersion();
            if (App.getInstance().isLoggedIn()) {
                iv_user_name.setText(login.getNickname());
                if(login.getAvatar()==null){
                    iv_user_img.setImageResource(R.mipmap.ic_launcher);
                }else{
                    ILFactory.getLoader().loadNet(iv_user_img, login.getAvatar(), null);
                }
                if(login.getExpireAt() != null && login.getIsGiveVip() == 0 ){
                    rel_uservip.setVisibility(View.VISIBLE);
                    tv_user_vip.setText("VIP有效期："+stampToDate(login.getExpireAt()));
                }else if(login.getExpireAt() != null && login.getIsGiveVip() == 1 && Long.parseLong(login.getExpireAt()) - System.currentTimeMillis() <= 48 * 60 *60 * 1000 ) {
                    rel_uservip.setVisibility(View.VISIBLE);
                    tv_user_vip.setText("VIP有效期："+stampToDate(login.getExpireAt())+", 请购买VIP");
                }else{
                    rel_uservip.setVisibility(View.INVISIBLE);
                }
            }
            tv_userlogout.setVisibility(View.VISIBLE);
        }
    }

    public static void main(String[] args) {
        System.out.println(stampToDate("1512543427000"));
    }


    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public void setConversionCode(ConversionCodeBean gankResults) {
        Log.i("setUserShare", AKOP.Decrypt(AKey.getString(), gankResults.getData(), AKey.getString1(), AKey.getString2()));
        ConversionCodeBean.ConversionCodeModel conversionCodeModel = JSON.parseObject(AKOP.Decrypt(AKey.getString(), gankResults.getData(), AKey.getString1(), AKey.getString2()), ConversionCodeBean.ConversionCodeModel.class);
        if (login != null) {
            login.setExpireAt(conversionCodeModel.getExpireAt());
            App.getInstance().saveCurrentPersion(login);
            tv_user_vip.setText("VIP有效期：" + stampToDate(login.getExpireAt()));
        }
        ToastUtil.showToast(gankResults.getMsg());
    }

    public void setLogOutCode(BaseResponse gankResults) {
        iv_user_name.setText("立即登录");
        iv_user_img.setImageResource(R.mipmap.ic_launcher);
        login = null;
        rel_uservip.setVisibility(View.GONE);
        tv_userlogout.setVisibility(View.GONE);
       tv_userlogout.setVisibility(View.GONE);
        tv_user_official.setVisibility(View.GONE);
       v_zw1.setVisibility(View.GONE);
        tv_user_ustomer_service.setVisibility(View.GONE);
       v_zw2.setVisibility(View.GONE);
       tv_invite_code.setVisibility(View.VISIBLE);
        v_zw3.setVisibility(View.VISIBLE);
        tv_user_uszw1.setVisibility(View.INVISIBLE);
        v_zw4.setVisibility(View.INVISIBLE);
        tv_user_uszw2.setVisibility(View.INVISIBLE);
        App.getInstance().logout();
        ToastUtil.showToast(gankResults.getMsg());
    }


    public void setUserShare(UserShareBean gankResults) {
        UserShareBean.UserShare userShare = JSON.parseObject(AKOP.Decrypt(AKey.getString(), gankResults.getData(), AKey.getString1(), AKey.getString2()), UserShareBean.UserShare.class);
        TabCategorizeFourthFragmentPermissionsDispatcher.showSoftWithCheck(TabCategorizeFourthFragment.this,userShare);
    }


    public void setUstomerService(UstomerService gankResults) {
        ustomerServiceModel = JSON.parseObject(AKOP.Decrypt(AKey.getString(),gankResults.getData(),AKey.getString1(),AKey.getString2()), UstomerService.UstomerServiceModel.class);
        if(ustomerServiceModel!=null){
            tv_user_ustomer_service.setVisibility(View.VISIBLE);
            v_zw2.setVisibility(View.VISIBLE);
           tv_user_uszw2.setVisibility(View.GONE);
            v_zw3.setVisibility(View.GONE);
        }else{
            tv_user_ustomer_service.setVisibility(View.GONE);
            v_zw2.setVisibility(View.GONE);
            tv_user_uszw2.setVisibility(View.INVISIBLE);
            v_zw3.setVisibility(View.VISIBLE);
        }
    }

    public void setUserOfficial(UserOfficialBean gankResults) {
        userOfficialModel = JSON.parseObject(AKOP.Decrypt(AKey.getString(), gankResults.getData(), AKey.getString1(), AKey.getString2()), UserOfficialBean.UserOfficialModel.class);
        if(userOfficialModel!=null){
           tv_user_official.setVisibility(View.VISIBLE);
           v_zw1.setVisibility(View.VISIBLE);
            tv_user_uszw1.setVisibility(View.GONE);
            v_zw4.setVisibility(View.GONE);
        }else{
            tv_user_official.setVisibility(View.GONE);
            v_zw1.setVisibility(View.GONE);
            tv_user_uszw1.setVisibility(View.INVISIBLE);
            v_zw4.setVisibility(View.INVISIBLE);
        }
    }

    public void setFindInviteCode(BaseResponse gankResults) {
       ToastUtil.showToast(gankResults.getMsg());
        getP().getProfile();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            getP().getProfile();
            getP().loadUserOfficial();
            getP().loadUstomerService();
        }
    }


    BroadcastReceiver broadcastReceiver;
    private void userrefresh() {
        broadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent) {
                getP().getProfile();
                getP().loadUserOfficial();
                getP().loadUstomerService();
                //context.unregisterReceiver(this);
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction("updateuser");
        context.registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver!=null){
            context.unregisterReceiver(broadcastReceiver);
        }
    }
    /**
     * 清除app缓存
     *
     * @param
     */
    public void clearAppCache() {

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    DataCleanManager.clearAllCache(context);
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    ToastUtil.showToast("清除失败");
                    break;
                case CLEAN_SUC:
                    ToastUtil.showToast("清除成功");
                    break;
            }
        }

        ;
    };


    public class ConversionCodeView extends PopupWindow {

        public ConversionCodeView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_conversion_code, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

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
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            final EditText tv_content_code = (EditText) view.findViewById(R.id.tv_content_code);
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (tv_content_code.getText().toString().equals("") || tv_content_code.getText().toString() == null) {
                        ToastUtil.showToast("请输入邀请码");
                    } else {
                        dismiss();
                        getP().loadConversionCode(tv_content_code.getText().toString());
                    }

                }
            });
        }
    }


    public class UserSharView extends PopupWindow {

        public UserSharView(Context mContext, View parent, final UserShareBean.UserShare userShare) {

            View view = View.inflate(mContext, R.layout.view_user_share, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

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
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            final ImageView tv_content_code = (ImageView) view.findViewById(R.id.iv_usershare);
            ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
            ILFactory.getLoader().loadNet(tv_content_code, userShare.getQrcodeUrl(), null);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("downloadUrl", userShare.getDownloadUrl());
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showToast("复制成功！");
                }
            });
            btn_dialog_confirm.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {
                        tv_content_code.setDrawingCacheEnabled(true);
                      /*  tv_content_code.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                        tv_content_code.layout(0, 0, tv_content_code.getMeasuredWidth(), tv_content_code.getMeasuredHeight());*/
                        tv_content_code.buildDrawingCache();
                        ImgUtils.saveImageToGallery(context, Bitmap.createBitmap(tv_content_code.getDrawingCache()));
                        tv_content_code.setDrawingCacheEnabled(false);
                        ToastUtil.showToast("保存成功！");
                    } catch (Exception e) {
                        ToastUtil.showToast("保存失败！");
                    }
                    return false;
                }
            });
        }
    }


    public class AboutUsView extends PopupWindow {

        public AboutUsView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_about_us, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

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
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }


    public class UstomerServiceView extends PopupWindow {

        public UstomerServiceView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_user_service, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

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
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
            tv_content.setText(ustomerServiceModel.getPlatformName()+":"+ustomerServiceModel.getCreateAt());
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("createAt", ustomerServiceModel.getCreateAt()+"");
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showToast("复制成功！");
                }
            });
        }
    }


    public class UserOfficialView extends PopupWindow {

        public UserOfficialView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_user_service, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

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
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
            tv_content.setText(userOfficialModel.getOfficialGroup());
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("officialGroup", userOfficialModel.getOfficialGroup());
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showToast("复制成功！");
                }
            });

        }
    }


    public class InviteCodeView extends PopupWindow {

        public InviteCodeView(Context mContext, View parent,final String fillInviteCode) {

            View view = View.inflate(mContext, R.layout.view_invite_code, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

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
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            final EditText tv_content_code = (EditText) view.findViewById(R.id.tv_content_code);
            TextView tv_content=(TextView)view.findViewById(R.id.tv_content);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            if(fillInviteCode==null){
                tv_content_code.setEnabled(true);
                btn_dialog_cancel.setText("确定");
                tv_content.setText("输入邀请码");
            }else{
                tv_content_code.setEnabled(false);
                btn_dialog_cancel.setText("复制");
                tv_content.setText("我的邀请码");
                tv_content_code.setText(fillInviteCode);
            }
            ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fillInviteCode==null){
                        if(tv_content_code.getText().toString().isEmpty()){
                            ToastUtil.showToast("请输入邀请码！");
                            return;
                        }
                        getP().loadUserInviteCodeAdd(tv_content_code.getText().toString().trim());
                    }else{
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("fillInviteCode",fillInviteCode);
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        ToastUtil.showToast("复制成功！");
                    }
                }
            });

        }
    }

    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showSoft(UserShareBean.UserShare userShare) {
        new UserSharView(context, iv_user_name, userShare);
    }
    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(context)
                .setMessage("保存二维码")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.REQUEST_INSTALL_PACKAGES})
    void showDeniedForCamera() {
        Toast.makeText(context, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.REQUEST_INSTALL_PACKAGES})
    void showNeverAskForCamera() {
        Toast.makeText(context, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 代理权限处理到自动生成的方法
        TabCategorizeFourthFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
