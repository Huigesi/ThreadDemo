package com.example.administrator.threaddemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemHotVideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HotVideoBean> videoBeans = new ArrayList<HotVideoBean>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemHotVideoListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public void setData(List<HotVideoBean> videoBeans) {
        this.videoBeans = videoBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hot_video_list, parent, false);
        return new HotVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HotVideoBean hotVideoBean = videoBeans.get(position);
        Glide.with(context).load(hotVideoBean.getVideoPath()).into(((HotVideoViewHolder) holder).imgHotVideoList);
        ((HotVideoViewHolder) holder).tvHotVideoList.setText(hotVideoBean.getDetail());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return videoBeans.size();
    }


    protected class HotVideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHotVideoList;
        private TextView tvHotVideoList;

        public HotVideoViewHolder(View view) {
            super(view);
            imgHotVideoList = (ImageView) view.findViewById(R.id.img_hot_video_list);
            tvHotVideoList = (TextView) view.findViewById(R.id.tv_hot_video_list);
        }
    }
}
