package com.example.administrator.threaddemo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class Test extends Fragment {

    // FIXME: 2018/7/13 
    private static final int ONE = 646;

    public static Test getInstance() {
        return getInstance();

    }
    public void setO(){

    }

    /* no-op */
    // TODO: 2018/7/13

    // STOPSHIP: 2018/7/13
    public static void start(Context context) {
        Intent starter = new Intent(context, Test.class);
        starter.putExtra("d", 1);
        context.startActivity(starter);

    }
    ///////////////////////////////////////////////////////////////////////////
    private static final String KEY_ONE = "ONE";

    ///////////////////////////////////////////////////////////////////////////
    private static final String TAG = "Test";
    public static void main(String[] args) {
        // FIXME: 2018/7/13

        try { if (args == null) { } } catch (Exception e) { e.printStackTrace(); }
        /* */

    }
    //

    public static Test newInstance() {

        Bundle args = new Bundle();

        Test fragment = new Test();
        fragment.setArguments(args);
        return fragment;
    }

    private String s;
    protected String d;
    public void j(){
        
    }
    

    public Test() {

    }
/*  ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context,imageList);
            ((SoundHeadViewHolder) holder).vpBanner.setAdapter(viewPagerAdapter);
            final Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == 1) {
                        if (((SoundHeadViewHolder) holder).vpBanner.getCurrentItem() == 2) {
                            ((SoundHeadViewHolder) holder).vpBanner.setCurrentItem(0);
                        } else {
                            ((SoundHeadViewHolder) holder).vpBanner.setCurrentItem(((SoundHeadViewHolder) holder).vpBanner.getCurrentItem() + 1);
                        }
                    }
                    return false;
                }
            });
            if (timerTask == null) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                };
                timer.schedule(timerTask, 1000, 2000);
            }
            ((SoundHeadViewHolder) holder).dotsIndicator.setViewPager(((SoundHeadViewHolder) holder).vpBanner);
            */


}
