package com.example.administrator.threaddemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "ViewPagerAdapter";
    private Context context;
    private List<String> mImageList = new ArrayList<>();
    private SparseArray<View> list = new SparseArray<>();
    public ViewPagerAdapter(Context context) {
        this.context=context;
    }

    public ViewPagerAdapter() {
        super();
    }

    public void setData( List<String> mImageList) {
        this.mImageList=mImageList;
    }
    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = list.get(position);
        if (view==null){
            String imagePath = mImageList.get(position);
            view = LayoutInflater.from(context).inflate(R.layout.item_banner, container, false);
            ImageView imageView = view.findViewById(R.id.iv_banner);
            Glide.with(context)
                    .load(imagePath)
                   .into(imageView);
            list.put(position,view);
        }

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

}
