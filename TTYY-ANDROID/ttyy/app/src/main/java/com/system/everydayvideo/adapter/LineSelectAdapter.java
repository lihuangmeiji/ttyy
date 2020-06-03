package com.system.everydayvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.system.everydayvideo.Bean.UserVipBean;
import com.system.everydayvideo.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class LineSelectAdapter extends SimpleRecAdapter<String, LineSelectAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public LineSelectAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_line_select;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String str = data.get(position);
        holder.tv_name.setText(str);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, str, TAG_VIEW, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
