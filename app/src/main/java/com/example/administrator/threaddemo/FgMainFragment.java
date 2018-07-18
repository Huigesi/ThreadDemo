package com.example.administrator.threaddemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class FgMainFragment extends Fragment  {

    public static final int MAIN_TYPE_MAIN=0;
    public static final int MAIN_TYPE_ASK = 1;
    public static final int MAIN_TYPE_SOUND = 2;
    private TabLayout tbl_main;
    private ViewPager vp_main;
    private ImageView imageView;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_main, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tbl_main = view.findViewById(R.id.tbl_main);
        vp_main = view.findViewById(R.id.vp_main);
        imageView=view.findViewById(R.id.img_news);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setViewPage();
        vp_main.setOffscreenPageLimit(2);
        tbl_main.setupWithViewPager(vp_main);
    }

    private void setViewPage() {
        fragments.add(FgMainListFragment.newInstance(MAIN_TYPE_MAIN));
        fragments.add(FgMainListFragment.newInstance(MAIN_TYPE_ASK));
        fragments.add(FgMainListFragment.newInstance(MAIN_TYPE_SOUND));
        fragmentTitles.add("首页");
        fragmentTitles.add("问答");
        fragmentTitles.add("声控");
        MyFragmentAdapter adapter=new MyFragmentAdapter(getChildFragmentManager(),
                fragments,fragmentTitles);
        vp_main.setAdapter(adapter);
    }

}
