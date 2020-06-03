package com.system.everydayvideo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.system.everydayvideo.AKey;
import com.system.everydayvideo.Bean.BaseResponse;
import com.system.everydayvideo.Bean.HomeBannerBean;
import com.system.everydayvideo.Bean.HomeVideo;
import com.system.everydayvideo.Bean.HomeVideoListBean;
import com.system.everydayvideo.Bean.MenuListBean;
import com.system.everydayvideo.R;
import com.system.everydayvideo.adapter.HomeVideoAdapter;
import com.system.everydayvideo.net.Constant;
import com.system.everydayvideo.present.TabCategorizeSecondPresent;
import com.system.everydayvideo.ui.account.LoginActivity;
import com.system.everydayvideo.ui.user.UserVipActivity;
import com.system.everydayvideo.util.HttpModel;
import com.system.everydayvideo.util.HttpUtil;
import com.system.everydayvideo.util.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.AKOP;
import cn.droidlover.xdroidmvp.kit.JSON;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class TabCategorizeSecondFragment extends XFragment<TabCategorizeSecondPresent> implements OnBannerListener, Constant {
    @BindView(R.id.banner_tab2)
    Banner banner_tab2;
    @BindView(R.id.home_tab2_Layout)
    XRecyclerView home_tab2_Layout;
    //设置图片资源:url或本地资源
    List<String> stringList;

    HomeVideoAdapter homeVideoAdapter;
    protected static final int MAX_PAGENUM = 20;
    private int pageNum = 1;
    MenuListBean.Item item;


    public TabCategorizeSecondFragment newInstance(MenuListBean.Item item) {
        this.item = item;
        TabCategorizeSecondFragment fragment = new TabCategorizeSecondFragment();
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        stringList = new ArrayList<>();
        homeVideoAdapter = new HomeVideoAdapter(context);
        home_tab2_Layout.gridLayoutManager(context, 4);
        home_tab2_Layout.setAdapter(homeVideoAdapter);
        homeVideoAdapter.setRecItemClick(new RecyclerItemCallback<HomeVideo, HomeVideoAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, HomeVideo model, int tag, HomeVideoAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                getP().loadHomeUserState(model);
            }
        });
        if (item != null) {

            //getP().loadTabTwoVideoListData(pageNum, MAX_PAGENUM);
            coordinates(item.getBannerApi());
        }
    }

    public void coordinates(String url) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("timeMillis", System.currentTimeMillis() + "");
        HttpModel.loadTabTwoBannersData(objectMap, url, new HttpUtil.ResponeCallBack() {
            @Override
            public void respone(String respone) {
                System.out.println("返回结果：" + respone);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_categorize_second;
    }

    @Override
    public TabCategorizeSecondPresent newP() {
        return new TabCategorizeSecondPresent();
    }

    @Override
    public void OnBannerClick(int position) {

    }

    private void loadTestDatas() {
        banner_tab2.setOnBannerListener(this);
        //设置轮播时间
        banner_tab2.setDelayTime(2000);
        //设置banner样式
        banner_tab2.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner_tab2.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        //设置banner动画效果
        banner_tab2.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置图片集合
        banner_tab2.setImages(stringList);
        //banner设置方法全部调用完毕时最后调用
        banner_tab2.start();
    }



    public void setTabTwoBanners(HomeBannerBean gankResults) {
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

    public void setTabTwoVideoList(HomeVideoListBean gankResults, int page) {
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

    public void setHomeUserState(BaseResponse gankResults, final HomeVideo model) {
        if (gankResults.getCode().equals("-1")) {
            LoginActivity.launch();
        } else {
            if (gankResults.getCode().equals("1")) {
                //HomeDetailedActivity.launch(context, model.getTarget());
            } else {
                UserVipActivity.launch(context);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner_tab2.startAutoPlay();
    }


    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner_tab2.stopAutoPlay();
    }

}
