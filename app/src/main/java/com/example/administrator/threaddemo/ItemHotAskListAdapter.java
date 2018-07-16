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

public class ItemHotAskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HotAskBean> hotAskBeans = new ArrayList<HotAskBean>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemHotAskListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<HotAskBean> hotAskBeans) {
        this.hotAskBeans = hotAskBeans;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hot_ask_list, parent, false);
        return new HotAskViewListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        HotAskBean hotAskBean = hotAskBeans.get(position);
        Glide.with(context).load(R.drawable.medal).into(((HotAskViewListHolder) holder).imgHotAskList);
        ((HotAskViewListHolder) holder).tvHotAskList.setText(hotAskBean.getAsk());
        ((HotAskViewListHolder) holder).tvHotAskListDe.setText(hotAskBean.getAnswer() + "个回答");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return hotAskBeans.size();
    }


    protected class HotAskViewListHolder extends RecyclerView.ViewHolder {
        private ImageView imgHotAskList;
        private TextView tvHotAskList;
        private TextView tvHotAskListDe;

        public HotAskViewListHolder(View view) {
            super(view);
            imgHotAskList = (ImageView) view.findViewById(R.id.img_hot_ask_list);
            tvHotAskList = (TextView) view.findViewById(R.id.tv_hot_ask_list);
            tvHotAskListDe = (TextView) view.findViewById(R.id.tv_hot_ask_list_de);
        }
    }
}
