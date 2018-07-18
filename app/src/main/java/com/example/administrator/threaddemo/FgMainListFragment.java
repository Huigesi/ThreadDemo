package com.example.administrator.threaddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.threaddemo.viewPagerIndicator.BannerComponent;
import com.example.administrator.threaddemo.viewPagerIndicator.ColorBar;
import com.example.administrator.threaddemo.viewPagerIndicator.Indicator;
import com.example.administrator.threaddemo.viewPagerIndicator.IndicatorViewPager;
import com.example.administrator.threaddemo.viewPagerIndicator.ScrollBar;

import java.util.ArrayList;
import java.util.List;

//FgMainListFragment
public class FgMainListFragment extends Fragment {
    private static final String TAG = "FgMainListFragment";
    private FgMainListAdapter adapter;
    private ViewPager viewPager, viewPager2;

    private Indicator dotsIndicator, dotsIndicator2;

    private List<String> mImageList = new ArrayList<>();
    private List<UserBean> userBeans = new ArrayList<>();
    private List<HotVideoBean> soundList = new ArrayList<HotVideoBean>();
    private RecyclerView rvMainList;
    private int type;
    private View headView;
    private BannerComponent bannerComponent;

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
       // headView = LayoutInflater.from(getContext()).inflate(R.layout.banner, container, false);
        return inflater.inflate(R.layout.fg_main_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        rvMainList = (RecyclerView) view.findViewById(R.id.rv_main_list);
       /* viewPager = (ViewPager) headView.findViewById(R.id.vp_banner);
        dotsIndicator = (Indicator) headView.findViewById(R.id.banner_indicator);*/
        //dotsIndicator.setScrollBar(new ColorBar(getActivity(), Color.WHITE, 8, ScrollBar.Gravity.CENTENT_BACKGROUND));


        initData();
    }

    private void initData() {
        for (int i = 0; i < 8; i++) {
            UserBean userBean = new UserBean();
            userBean.setUserName("用户" + i);
            userBean.setAnsCount(i);
            userBean.setAnswer("答案答案答案答案答案答案");
            userBean.setGoods(i);
            userBean.setAsk("问题问题问题" + i);
            userBean.setComment(i);
            userBean.setImgAns(R.drawable.newicon);
            userBean.setImgNew(R.drawable.newicon);
            userBean.setImgUserPath(R.drawable.my2 + "");
            userBeans.add(userBean);

        }
        for (int i = 0; i < 9; i++) {
            HotVideoBean hotVideoBean = new HotVideoBean();
            hotVideoBean.setVideoPath("http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png");
            hotVideoBean.setDetail("描述描述描述描述");
            soundList.add(hotVideoBean);
        }
        String image2 = "http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png";
        String image1 = "http://www.wanandroid.com/blogimgs/fb0ea461-e00a-482b-814f-4faca5761427.png";
        String image3 = "http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png";

        mImageList.add(image1);
        mImageList.add(image2);
        mImageList.add(image3);
        if (type == FgMainFragment.MAIN_TYPE_MAIN) {
            adapter = new FgMainListAdapter(getActivity());
            adapter.setData(mImageList, userBeans);
            rvMainList.setAdapter(adapter);

        } else if (type == FgMainFragment.MAIN_TYPE_ASK) {
            ItemAskAdapter itemAskAdapter = new ItemAskAdapter(getActivity());
            itemAskAdapter.setData(userBeans);
            rvMainList.setAdapter(itemAskAdapter);

        } else if (type == FgMainFragment.MAIN_TYPE_SOUND) {
            SoundAdapter soundAdapter = new SoundAdapter(getActivity());
            soundAdapter.setData(soundList, mImageList);
            rvMainList.setAdapter(soundAdapter);

        }
        rvMainList.addItemDecoration(new MyItemDecoration());
        rvMainList.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

}
