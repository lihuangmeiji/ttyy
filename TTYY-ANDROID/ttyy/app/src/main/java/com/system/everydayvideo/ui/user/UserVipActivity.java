package com.system.everydayvideo.ui.user;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.UserRecharge;
import com.system.everydayvideo.Bean.UserRechargeUrl;
import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.R;
import com.system.everydayvideo.adapter.UserVipAdapter;
import com.system.everydayvideo.present.UserVipPresent;
import com.system.everydayvideo.ui.account.LoginActivity;
import com.system.everydayvideo.util.ConstUtils;
import com.system.everydayvideo.util.ToastUtil;
import com.system.everydayvideo.widget.GridDividerItemDecoration;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;
import sign.AuthResult;
import sign.PayResult;

public class UserVipActivity extends XActivity<UserVipPresent> {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_vip_Layout)
    XRecyclerView user_vip_Layout;
    @BindView(R.id.prl_uservip)
    PullToRefreshLayout prl_uservip;
    UserVipAdapter userVipAdapter;
    protected static final int MAX_PAGENUM = 10;
    private int pageNum = 1;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbarTitle.setText("成为VIP会员");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorVip));
        getP().loadUserVip(pageNum, MAX_PAGENUM, ConstUtils.getVersioncode(context));
        userVipAdapter = new UserVipAdapter(context);
        user_vip_Layout.gridLayoutManager(context, 2);
        user_vip_Layout.addItemDecoration(new GridDividerItemDecoration(40, context.getResources().getColor(R.color.colorVip)));
        user_vip_Layout.setAdapter(userVipAdapter);
        prl_uservip.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                pageNum = 1;
                prl_uservip.finishRefresh();
                getP().loadUserVip(pageNum, MAX_PAGENUM,ConstUtils.getVersioncode(context));
            }

            @Override
            public void loadMore() {
                pageNum++;
                getP().loadUserVip(pageNum, MAX_PAGENUM,ConstUtils.getVersioncode(context));
                prl_uservip.finishLoadMore();
            }
        });
        userVipAdapter.setRecItemClick(new RecyclerItemCallback<UserVipBean.UserVip, UserVipAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, UserVipBean.UserVip model, int tag, UserVipAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                getP().loadUserRecharge(model.getId(),ConstUtils.getVersioncode(context));
            }
        });
    }

    @OnClick({
            R.id.toolbar,
    })
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_vip;
    }

    @Override
    public UserVipPresent newP() {
        return new UserVipPresent();
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(UserVipActivity.class)
                .launch();
    }

    public void showError(NetError error) {
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

    public void setUserVip(UserVipBean gankResults, int page) {
        List<UserVipBean.UserVip> userVipList = JSON.parseArray(AKOP.Decrypt(AKey.getString(), gankResults.getData(),AKey.getString1(),AKey.getString2()), UserVipBean.UserVip.class);
        if (page > 1) {
            userVipAdapter.addData(userVipList);
            if (userVipList == null) {
                ToastUtil.showToast("暂无更多数据");
            }
        } else {
            userVipAdapter.setData(userVipList);
        }
    }

    public void setUserRecharge(UserRecharge gankResults) {
        //pay(AESOperator.Decrypt(AKey.getString(), gankResults.getData()));
        UserRechargeUrl userRechargeUrl = JSON.parseObject(AKOP.Decrypt(AKey.getString(),gankResults.getData(),AKey.getString1(),AKey.getString2()), UserRechargeUrl.class);
        UserPayActivity.launch(context,userRechargeUrl.getUrl());
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(final String ranking) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(UserVipActivity.this);
                String rankings = ranking;

                if (rankings == null) {
                } else {
                    Map<String, String> result = alipay.payV2(rankings, true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }


            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */

                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;

                }
                default:
                    break;
            }
        }

        ;
    };
}
