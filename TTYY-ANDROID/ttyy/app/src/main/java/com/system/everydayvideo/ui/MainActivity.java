package com.system.everydayvideo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.system.everydayvideo.AKey;
import com.system.everydayvideo.App;
import com.system.everydayvideo.Bean.MenuListBean;
import com.system.everydayvideo.Bean.TabBean;
import com.system.everydayvideo.Bean.VersionBean;
import com.system.everydayvideo.Bean.VersionShowBean;
import com.system.everydayvideo.R;
import com.system.everydayvideo.adapter.HomePageAdapter;
import com.system.everydayvideo.present.MainPresent;
import com.system.everydayvideo.ui.fragment.TabCategorizeFirstFragment;
import com.system.everydayvideo.ui.fragment.TabCategorizeFourthFragment;
import com.system.everydayvideo.ui.fragment.TabCategorizeSecondFragment;
import com.system.everydayvideo.ui.fragment.TabCategorizeThirdFragment;
import com.system.everydayvideo.util.ConstUtils;
import com.system.everydayvideo.util.DataKeeper;
import com.system.everydayvideo.util.DownFileHelper;
import com.system.everydayvideo.util.DownloadUtil;
import com.system.everydayvideo.util.InstallApk;
import com.system.everydayvideo.util.PermissionHelper;
import com.system.everydayvideo.util.SoftUpdate;
import com.system.everydayvideo.util.ToastUtil;
import com.system.everydayvideo.widget.CustomizeTabLayout;
import com.system.everydayvideo.widget.NoScrollViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends XActivity<MainPresent> {
    private final static String TAG = "MainActivity";
    @BindView(R.id.tabLayout)
    CustomizeTabLayout tabLayout;
    @BindView(R.id.vp_home)
    NoScrollViewPager vp_home;
    @BindView(R.id.lin_main)
    LinearLayout lin_main;

    private ArrayList<Fragment> fragments;
    private ArrayList<TabBean> mTabbeans = new ArrayList<>();

    private String[] mTitles = {"首页", "我的"};

    private int[] mUnSelectIcons = {
            R.mipmap.tab1wx, R.mipmap.tab2wx};
    private int[] mSelectIcons = {
            R.mipmap.tab1yx, R.mipmap.tab2yx};

    private int mSelectColor = R.color.tabyd;
    private int mUnSelectColor = R.color.tabwd;


    public static final String MENULISTBEAN = "menuListBean";
    MenuListBean menuListBean;

    protected String url;

    @Override
    public void initData(Bundle savedInstanceState) {
        App.setMainActivity(this);
        initFragments();
        popupHandler.sendEmptyMessageDelayed(0, 1500);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setMainActivity(null);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresent newP() {
        return new MainPresent();
    }


    /**
     * 初始化主页5个fragment
     */
    private void initFragments() {
        fragments = new ArrayList<>();
        menuListBean = (MenuListBean) getIntent().getSerializableExtra(MENULISTBEAN);
        if (menuListBean != null) {
            if (menuListBean.getData() != null) {
                addTab(0);
                List<MenuListBean.Item> itemList = JSON.parseArray(AKOP.Decrypt(AKey.getString(), menuListBean.getData(), AKey.getString1(), AKey.getString2()), MenuListBean.Item.class);
                for (int i = 0; itemList != null && i < itemList.size(); i++) {
                    TabBean tabBean = new TabBean();
                    tabBean.setTitle(itemList.get(i).getTitle());
                    tabBean.setSelectUrl(itemList.get(i).getSelectedIcon());
                    tabBean.setUnSelectUrl(itemList.get(i).getIcon());
                    tabBean.setmSelectColor(context.getResources().getColor(mSelectColor));
                    tabBean.setmUnSelectColor(context.getResources().getColor(mUnSelectColor));
                   /* tabBean.setSelectIcons(0);
                    tabBean.setUnSelectIcon(0);*/
                    mTabbeans.add(tabBean);
                }
                addTab(1);
                fragments.add(new TabCategorizeFirstFragment());
                if (itemList != null && itemList.size() == 1) {
                    fragments.add(new TabCategorizeSecondFragment().newInstance(itemList.get(0)));
                } else if (itemList != null && itemList.size() == 2) {
                    fragments.add(new TabCategorizeSecondFragment());
                    fragments.add(new TabCategorizeThirdFragment());
                }
                fragments.add(TabCategorizeFourthFragment.newInstance());
                //设置viewpager的适配器
                vp_home.setAdapter(new HomePageAdapter(getSupportFragmentManager(), fragments));
                //viewpager会初始化左右两边各3个
                vp_home.setOffscreenPageLimit(0);
                tabLayout.setTabDate(mTabbeans);
                tabLayout.setmListener(new CustomizeTabLayout.OnTabSelectListener() {
                    @Override
                    public void onTabSelect(int position) {
                        vp_home.setCurrentItem(position, false);
                    }

                    /**
                     * 连续点击调用此方法
                     */
                    @Override
                    public void onTabReselect(int position) {

                    }
                });
            } else {
                Log.e(TAG, "menuListBean.getData()没有数据");
            }
        } else {
            Log.e(TAG, "menuListBean没有数据");
        }
    }

    public void addTab(int index) {
        TabBean tabBean1 = new TabBean();
        tabBean1.setTitle(mTitles[index]);
        tabBean1.setSelectUrl(null);
        tabBean1.setUnSelectUrl(null);
        tabBean1.setmSelectColor(context.getResources().getColor(mSelectColor));
        tabBean1.setmUnSelectColor(context.getResources().getColor(mUnSelectColor));
        tabBean1.setSelectIcons(mSelectIcons[index]);
        tabBean1.setUnSelectIcon(mUnSelectIcons[index]);
        mTabbeans.add(tabBean1);
    }

    public static void launch(Activity activity, MenuListBean desc) {
        Router.newIntent(activity)
                .to(MainActivity.class)
                .putSerializable(MENULISTBEAN, desc)
                .launch();
    }


    /**
     * 下载应用
     */
    private void downloadApp(final String APP_DOWNLOAD_WEBSITE) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = DownloadUtil.downLoadFile(context, "ttyy", ".apk", APP_DOWNLOAD_WEBSITE);
                //dismissProgressDialog();
                DownloadUtil.openFile(context, file);
            }
        }).start();
    }

    public class DownLoadingView extends PopupWindow {

        public DownLoadingView(Context mContext, View parent, final VersionShowBean versionShowBean) {

            View view = View.inflate(mContext, R.layout.down_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(false);
            setOutsideTouchable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_content.setText(versionShowBean.getMsg().replace("\\n", "\n"));
            if (versionShowBean.getForceUpdate() == 1) {
                btn_dialog_cancel.setVisibility(View.GONE);
            } else {
                btn_dialog_cancel.setVisibility(View.VISIBLE);
            }
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
                    url = versionShowBean.getUrl();
                    MainActivityPermissionsDispatcher.showSoftWithCheck(MainActivity.this,url);
                }
            });
        }
    }
    DownLoadingView downLoadingView;
    public void showVersion(VersionBean gankResults) {
        Log.i("VersionBean", gankResults.getData().getVersionName());
        VersionShowBean versionShowBean = gankResults.getData();
        if (versionShowBean != null) {
            if (versionShowBean.getVersion() >ConstUtils.getVersioncode(context) && Build.VERSION.SDK_INT >= versionShowBean.getOsMinVersion()) {
                downLoadingView= new DownLoadingView(context, lin_main, versionShowBean);
            }
        }
    }


    private Handler popupHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    getP().loadVersionData();
                    break;
            }
        }

    };

    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showSoft(String url) {
        downLoadingView.dismiss();
        SoftUpdate manager = new SoftUpdate(MainActivity.this, url);
        manager.showDownloadDialog();
    }

    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("软件更新")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults!=null&&grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, 10012);
                }
                break;
        }
        // 代理权限处理到自动生成的方法
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10012:
                Log.d("resultCode", resultCode + "");
                if (Build.VERSION.SDK_INT >= 26) {
                    boolean b = context.getPackageManager().canRequestPackageInstalls();
                    if (b) {
                        new InstallApk(context)
                                .installApk(new File(DataKeeper.fileRootPath, "ttyy.apk"));
                    } else {
                        ToastUtil.showToast("请赋予权限后在操作！");
                        // 退出程序
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                }
                break;
            default:
                break;
        }
    }

}
