package com.system.everydayvideo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.everydayvideo.Bean.HomeVideo;
import com.system.everydayvideo.Bean.HomeVideoListBean;
import com.system.everydayvideo.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class HomeVideoAdapter extends SimpleRecAdapter<HomeVideo, HomeVideoAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public HomeVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_home_video;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HomeVideo homeVideo = data.get(position);
        holder.tv_home_video_content.setText(homeVideo.getTitle());
        ILFactory.getLoader().loadNet(holder.iv_home_video_ico, homeVideo.getImg(), null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, homeVideo, TAG_VIEW, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_home_video_ico)
        ImageView iv_home_video_ico;
        @BindView(R.id.tv_home_video_content)
        TextView tv_home_video_content;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
