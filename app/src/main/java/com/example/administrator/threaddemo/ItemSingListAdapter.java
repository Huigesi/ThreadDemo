package com.example.administrator.threaddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemSingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HotVideoBean> videoBeans = new ArrayList<HotVideoBean>();

    private Context context;

    public ItemSingListAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<HotVideoBean> videoBeans) {
        this.videoBeans = videoBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sing_list, parent, false);
        return new SingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HotVideoBean hotVideoBean = videoBeans.get(position);
        Glide.with(context).load(hotVideoBean.getVideoPath()).into(((SingViewHolder) holder).imgSingList);
        ((SingViewHolder) holder).tvSingList.setText(hotVideoBean.getDetail());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return videoBeans.size();
    }


    protected class SingViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSingList;
        private TextView tvSingList;

        public SingViewHolder(View view) {
            super(view);
            imgSingList = (ImageView) view.findViewById(R.id.img_sing_list);
            tvSingList = (TextView) view.findViewById(R.id.tv_sing_list);
        }
    }
}
