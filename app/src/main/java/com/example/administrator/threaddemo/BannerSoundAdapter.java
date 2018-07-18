package com.example.administrator.threaddemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;


public class BannerSoundAdapter extends PagerAdapter {

    private SparseArray<View> mViews;
    private List<String> mBannerDatas;
    private Context context;


    public BannerSoundAdapter(List<String> mBannerDatas, Context context) {
        this.mBannerDatas = mBannerDatas;
        this.context = context;
        mViews = new SparseArray<>();
    }

    public void notifyDatas(List<String> mBannerDatas) {
        this.mBannerDatas = mBannerDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mBannerDatas == null) return 0;
        return mBannerDatas.size() <= 1 ? mBannerDatas.size() : Integer.MAX_VALUE;
        //return mBannerDatas.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {

        View view = mViews.get(position);
        if (view == null) {
            position = position % mBannerDatas.size();
            String imagePath = mBannerDatas.get(position);
            view = LayoutInflater.from(context).inflate(R.layout.item_banner, container, false);
            ImageView imageView = view.findViewById(R.id.iv_banner);
            Glide.with(context).load(imagePath).into(imageView);
            mViews.put(position, view);
        }
      /*  if (view.getParent()!=null){
            container.removeView(view);
        }*/
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        mViews.clear();
        return POSITION_NONE;
    }

//    @Override
//    public void startUpdate(ViewGroup container) {
//        super.startUpdate(container);

    //ViewPager显示的页面数据有所改变的回调(还未处理)
//    }

//    @Override
//    public void finishUpdate(ViewGroup container) {
//        super.finishUpdate(container);

    //页面数据改变的处理结束后的回调
    //此处的处理其实就是 回调instantiateItem和destroyItem方法
    //当数据发生变化
//        int position = viewPager.getCurrentItem();
//        if (position == 0){
//            viewPager.setCurrentItem(8,false);
//        }else if (position == 9){
//            viewPager.setCurrentItem(1,false);
//        }
//    }


}
