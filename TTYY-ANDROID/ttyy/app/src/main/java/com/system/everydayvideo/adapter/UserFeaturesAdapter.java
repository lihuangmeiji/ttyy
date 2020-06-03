package com.system.everydayvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.everydayvideo.Bean.HomeVideo;
import com.system.everydayvideo.Bean.UserFeaturesModel;
import com.system.everydayvideo.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class UserFeaturesAdapter extends SimpleRecAdapter<UserFeaturesModel, UserFeaturesAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public UserFeaturesAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_features;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserFeaturesModel userFeaturesModel = data.get(position);
        holder.tv_content.setText(userFeaturesModel.getName());
        holder.iv_ico.setImageResource(userFeaturesModel.getIco());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, userFeaturesModel, TAG_VIEW, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_ico)
        ImageView iv_ico;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
