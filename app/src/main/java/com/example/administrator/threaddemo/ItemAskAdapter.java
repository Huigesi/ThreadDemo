package com.example.administrator.threaddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemAskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserBean> userBeans = new ArrayList<UserBean>();

    private Context context;

    public ItemAskAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UserBean> userBeans) {
        this.userBeans = userBeans;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ask, parent, false);

        return new AskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserBean userBean = userBeans.get(position);
        Glide.with(context).load(R.drawable.newicon).into(((AskViewHolder) holder).imgNew);
        ((AskViewHolder) holder).tvAnswer.setText(userBean.getAnswer());
        ((AskViewHolder) holder).tvAsk.setText(userBean.getAsk());
        ((AskViewHolder) holder).tvReward.setText("奖励 +3魅力");
        ((AskViewHolder) holder).tvAnswerCount.setText(userBean.getAnsCount() + "个回答");
        Glide.with(context).load(R.drawable.newicon).into(((AskViewHolder) holder).imgAns);
        Glide.with(context).load(R.drawable.main).into(((AskViewHolder) holder).imgUser);
        ((AskViewHolder) holder).tvUser.setText(userBean.getUserName());
        ((AskViewHolder) holder).tvGood.setText(userBean.getGoods()+"");
        ((AskViewHolder) holder).tvComment.setText(userBean.getComment()+"");
        Glide.with(context).load(R.drawable.good).into(((AskViewHolder) holder).imgGood);
        Glide.with(context).load(R.drawable.comment).into(((AskViewHolder) holder).imgComment);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return userBeans.size();
    }


    protected class AskViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgNew;
        private TextView tvAsk;
        private TextView tvReward;
        private TextView tvAnswerCount;
        private ImageView imgAns;
        private ImageView imgUser;
        private TextView tvUser;
        private ImageView imgGood;
        private TextView tvGood;
        private ImageView imgComment;
        private TextView tvComment;
        private TextView tvAnswer;

        public AskViewHolder(View view) {
            super(view);
            imgNew = (ImageView) view.findViewById(R.id.img_new);
            tvAsk = (TextView) view.findViewById(R.id.tv_ask);
            tvReward = (TextView) view.findViewById(R.id.tv_reward);
            tvAnswerCount = (TextView) view.findViewById(R.id.tv_answer_count);
            imgAns = (ImageView) view.findViewById(R.id.img_ans);
            imgUser = (ImageView) view.findViewById(R.id.img_user);
            tvUser = (TextView) view.findViewById(R.id.tv_user);
            imgGood = (ImageView) view.findViewById(R.id.img_good);
            tvGood = (TextView) view.findViewById(R.id.tv_good);
            imgComment = (ImageView) view.findViewById(R.id.img_comment);
            tvComment = (TextView) view.findViewById(R.id.tv_comment);
            tvAnswer = (TextView) view.findViewById(R.id.tv_answer);
        }
    }
}
