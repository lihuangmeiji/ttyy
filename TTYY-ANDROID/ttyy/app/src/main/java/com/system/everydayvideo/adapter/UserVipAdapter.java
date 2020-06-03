package com.system.everydayvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.everydayvideo.Bean.HomeVideo;
import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class UserVipAdapter extends SimpleRecAdapter<UserVipBean.UserVip, UserVipAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public UserVipAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_vip;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserVipBean.UserVip userVip = data.get(position);
        holder.tv_title.setText(userVip.getTitle());
        holder.tv_vip_moneyxj.setText("¥" + userVip.getCurrentPrice());
        holder.tv_vip_moneyyj.setText("¥"+userVip.getOriginalPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, userVip, TAG_VIEW, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_vip_moneyxj)
        TextView tv_vip_moneyxj;
        @BindView(R.id.tv_vip_moneyyj)
        TextView tv_vip_moneyyj;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
