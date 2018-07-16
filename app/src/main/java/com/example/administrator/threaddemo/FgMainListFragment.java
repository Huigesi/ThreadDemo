package com.example.administrator.threaddemo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//FgMainListFragment
public class FgMainListFragment extends Fragment {
    private FgMainListAdapter adapter;

    private List<String> mImageList = new ArrayList<>();
    private List<UserBean> userBeans = new ArrayList<>();
    private RecyclerView rvMainList;
    private int type;


    public static FgMainListFragment newInstance(int type) {
        Bundle args = new Bundle();
        FgMainListFragment fragment = new FgMainListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_main_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        rvMainList = (RecyclerView) view.findViewById(R.id.rv_main_list);
        initData();
    }


    private void initData() {
        for (int i = 0; i < 8; i++) {
            UserBean userBean = new UserBean();
            userBean.setUserName("用户"+i);
            userBean.setAnsCount(i);
            userBean.setAnswer("答案答案答案答案答案答案");
            userBean.setGoods(i);
            userBean.setAsk("问题问题问题"+i);
            userBean.setComment(i);
            userBean.setImgAns(R.drawable.newicon);
            userBean.setImgNew(R.drawable.newicon);
            userBean.setImgUserPath(R.drawable.my2+"");
            userBeans.add(userBean);
        }
        if (type == FgMainFragment.MAIN_TYPE_MAIN) {
            String image1 = "http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png";
            String image2 = "http://www.wanandroid.com/blogimgs/fb0ea461-e00a-482b-814f-4faca5761427.png";
            String image3 = "http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png";

            mImageList.add(image1);
            mImageList.add(image2);
            mImageList.add(image3);
            adapter = new FgMainListAdapter(getActivity());


            adapter.setData(mImageList,userBeans);
            rvMainList.setAdapter(adapter);

        } else if (type == FgMainFragment.MAIN_TYPE_ASK) {
            ItemAskAdapter itemAskAdapter = new ItemAskAdapter(getActivity());
            itemAskAdapter.setData(userBeans);
            rvMainList.setAdapter(itemAskAdapter);

        } else if (type == FgMainFragment.MAIN_TYPE_SOUND) {
        }
        rvMainList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMainList.addItemDecoration(new MyItemDecoration());


    }

}
