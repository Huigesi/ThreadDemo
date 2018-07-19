package com.example.administrator.threaddemo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.threaddemo.popMenu.PopMenu;
import com.example.administrator.threaddemo.popMenu.PopMenuItem;
import com.example.administrator.threaddemo.popMenu.PopMenuItemListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    private LinearLayout bottom_main;
    private ImageView bottom_add;
    private LinearLayout bottom_my;
    private TextView tv_main;
    private TextView tv_my;
    private ViewPager vp_content;
    private MyFragmentAdapter adapter;
    private ImageView img_main;
    private ImageView img_my;
    private PopMenu mPopMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#06CFE8"));
        }
        ActionBar actionBar = getSupportActionBar();

        actionBar.hide();
        setCurrentItem(0);

        initContentFragment();
        mPopMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
                .addMenuItem(new PopMenuItem("文字", getResources().getDrawable(R.drawable.stars)))
                .addMenuItem(new PopMenuItem("照片", getResources().getDrawable(R.drawable.stars)))
                .addMenuItem(new PopMenuItem("文章", getResources().getDrawable(R.drawable.stars)))
                .columnCount(3)
                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onItemClick(PopMenu popMenu, int position) {
                        Toast.makeText(MainActivity.this, "你点击了第" + position + "个位置", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        bottom_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPopMenu.isShowing()){
                    mPopMenu.show();
                }
            }
        });
    }


    private void initContentFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FgMainFragment());
        fragments.add(new FgMyFragment());
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        vp_content.setAdapter(adapter);
    }

    private void setCurrentItem(int i) {
        vp_content.setCurrentItem(i);
        Glide.with(this).load(R.drawable.main).into(img_main);
        Glide.with(this).load(R.drawable.my1).into(img_my);
        tv_main.setTextColor(Color.parseColor("#B1ACAC"));
        tv_my.setTextColor(Color.parseColor("#B1ACAC"));
        switch (i) {
            case 0:
                Glide.with(this).load(R.drawable.main2).into(img_main);
                tv_main.setTextColor(Color.parseColor("#06CFE8"));
                break;
            case 1:
                Glide.with(this).load(R.drawable.my2).into(img_my);
                tv_my.setTextColor(Color.parseColor("#06CFE8"));
                break;
        }
    }

    private void initView() {
        bottom_main = (LinearLayout) findViewById(R.id.bottom_main);
        bottom_add = (ImageView) findViewById(R.id.bottom_add);
        bottom_my = (LinearLayout) findViewById(R.id.bottom_my);

        bottom_main.setOnClickListener(this);
        bottom_my.setOnClickListener(this);
        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_main.setOnClickListener(this);
        tv_my = (TextView) findViewById(R.id.tv_my);
        tv_my.setOnClickListener(this);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        vp_content.setOnClickListener(this);
        vp_content.setOnPageChangeListener(this);
        img_main = (ImageView) findViewById(R.id.img_main);
        img_main.setOnClickListener(this);
        img_my = (ImageView) findViewById(R.id.img_my);
        img_my.setOnClickListener(this);
        Glide.with(this).load(R.drawable.add1).into(bottom_add);

        // img_main.setImageDrawable(getResources().getDrawable(R.drawable.bar_main));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_main:
                setCurrentItem(0);
                break;
            case R.id.bottom_my:
                setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
