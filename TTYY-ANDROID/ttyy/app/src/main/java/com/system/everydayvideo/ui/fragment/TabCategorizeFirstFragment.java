package com.system.everydayvideo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.HomeBannerBean;
import com.system.everydayvideo.Bean.HomeVideo;
import com.system.everydayvideo.Bean.HomeVideoListBean;
import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.R;
import com.system.everydayvideo.adapter.HomeVideoAdapter;
import com.system.everydayvideo.present.TabCategorizeFirstPresent;
import com.system.everydayvideo.ui.HomeDetailedActivity;
import com.system.everydayvideo.ui.account.LoginActivity;
import com.system.everydayvideo.ui.user.UserVipActivity;
import com.system.everydayvideo.util.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class TabCategorizeFirstFragment extends XFragment<TabCategorizeFirstPresent> implements OnBannerListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.home_video_Layout)
    XRecyclerView home_video_Layout;

    //设置图片资源:url或本地资源
    List<String> stringList;

    HomeVideoAdapter homeVideoAdapter;
    protected static final int MAX_PAGENUM = 20;
    private int pageNum = 1;



    @Override
    public void initData(Bundle savedInstanceState) {
        stringList = new ArrayList<>();
        homeVideoAdapter = new HomeVideoAdapter(context);
        home_video_Layout.gridLayoutManager(context, 4);
        home_video_Layout.setAdapter(homeVideoAdapter);
        homeVideoAdapter.setRecItemClick(new RecyclerItemCallback<HomeVideo, HomeVideoAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, HomeVideo model, int tag, HomeVideoAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                getP().loadHomeUserState(model);
            }
        });
        //home_video_Layout.useDefLoadMoreView();
        getP().loadHomeVideoData(pageNum, MAX_PAGENUM);
        getP().loadHomeBannersData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_categorize_first;
    }

    @Override
    public TabCategorizeFirstPresent newP() {
        return new TabCategorizeFirstPresent();
    }

    private void loadTestDatas() {
        banner.setOnBannerListener(this);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置图片集合
        banner.setImages(stringList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void OnBannerClick(int position) {

    }


    public void setHomeBanners(HomeBannerBean gankResults) {
        List<HomeBannerBean.HomeBanner> homeBanners = JSON.parseArray(AKOP.Decrypt(AKey.getString(), gankResults.getData(),AKey.getString1(),AKey.getString2()), HomeBannerBean.HomeBanner.class);
        if (gankResults.getData() != null && homeBanners.size() > 0) {
            for (int i = 0; i < homeBanners.size(); i++) {
                stringList.add(homeBanners.get(i).getImg());
            }
        } else {
            stringList.add("");
        }
        loadTestDatas();
    }

    public void setHomeVideoList(HomeVideoListBean gankResults, int page) {
        List<HomeVideo> homeVideoList = JSON.parseArray(AKOP.Decrypt(AKey.getString(), gankResults.getData(),AKey.getString1(),AKey.getString2()), HomeVideo.class);
        if (page > 1) {
            homeVideoAdapter.addData(homeVideoList);
            if (homeVideoList == null) {
                ToastUtil.showToast("暂无更多数据");
            }
        } else {
            homeVideoAdapter.setData(homeVideoList);
        }
    }

    public void setHomeUserState(UserVipBean gankResults, final HomeVideo model) {
        //HomeDetailedActivity.launch(context, model.getId(), model.getTarget());

        if (gankResults.getCode().equals("-1")) {
            LoginActivity.launch();
        } else {
            UserVipBean.IsVip  isVIP = JSON.parseObject(AKOP.Decrypt(AKey.getString(), gankResults.getData(), AKey.getString1(),AKey.getString2()), UserVipBean.IsVip.class);
            if(gankResults.getCode().equals("0") && isVIP.getIsVIP() == 1){
                if(model!=null && !TextUtils.isEmpty(model.getTarget())) {
                    HomeDetailedActivity.launch(context, model.getId(), model.getTarget());
                }else{
                    ToastUtil.showToast("紧张开发中,敬请期待");
                }
            }else{
                UserVipActivity.launch(context);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }


    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

}
