package com.example.administrator.threaddemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.threaddemo.viewPagerIndicator.BannerComponent;
import com.example.administrator.threaddemo.viewPagerIndicator.Indicator;
import com.example.administrator.threaddemo.viewPagerIndicator.IndicatorViewPager;

public class SoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 001;
    private static final int TYPE_HOT = 002;
    private static final int TYPE_PIA = 003;
    private static final int TYPE_SING = 004;
    private static final int TYPE_SOUND = 005;
    private static final int TYPE_SOUND_V = 006;

    private Timer timer=new Timer();
    private TimerTask timerTask;
    private List<HotVideoBean> soundList = new ArrayList<HotVideoBean>();
    private List<String> imageList = new ArrayList<>();

    private Context context;
    private View headerView;
    private LayoutInflater layoutInflater;
    private BannerComponent bannerComponent;
    private RecyclerView.ViewHolder bannerView;
    public void setHeaderView(View headerView){
        this.headerView=headerView;
        notifyItemInserted(0);
    }

    public SoundAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<HotVideoBean> soundList, List<String> imageList) {
        this.soundList = soundList;
        this.imageList = imageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_HOT;
        } else if (position == 2) {
            return TYPE_PIA;
        } else if (position == 3) {
            return TYPE_SING;
        } else if (position == 4) {
            return TYPE_SOUND;
        } else if (position == 5) {
            return TYPE_SOUND_V;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            if (bannerView!=null){
                return bannerView;
            }else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sound, parent, false);
                bannerView=new SoundHeadViewHolder(view);
                return bannerView;
            }

        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sound_hot, parent, false);
            return new HotViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (bannerComponent!=null) {
            bannerComponent.setAutoPlayTime(2000);
            bannerComponent.startAutoPlay();
        }
        if (holder instanceof SoundHeadViewHolder) {
            if (bannerComponent==null){
                bannerComponent = new BannerComponent(((SoundHeadViewHolder) holder).dotsIndicator,
                        ((SoundHeadViewHolder) holder).vpBanner, false);

            }

            bannerComponent.setAdapter(bannerAdapter);
            ((SoundHeadViewHolder) holder).dotsIndicator.setViewPager(((SoundHeadViewHolder) holder).vpBanner);
            ((SoundHeadViewHolder) holder).dotsIndicator.setDotsCount(imageList.size());
            Glide.with(context).load(R.drawable.pia).into(((SoundHeadViewHolder) holder).imgSound1);
            Glide.with(context).load(R.drawable.music).into(((SoundHeadViewHolder) holder).imgSound2);
            Glide.with(context).load(R.drawable.pia).into(((SoundHeadViewHolder) holder).imgSound3);
            Glide.with(context).load(R.drawable.pia).into(((SoundHeadViewHolder) holder).imgSound4);
            ((SoundHeadViewHolder) holder).tvSound1.setText("PIA戏模仿");
            ((SoundHeadViewHolder) holder).tvSound2.setText("歌唱剧场");
            ((SoundHeadViewHolder) holder).tvSound3.setText("声控福利社");
            ((SoundHeadViewHolder) holder).tvSound4.setText("声控电台");

        } else if (holder instanceof HotViewHolder) {
            ItemHotVideoListAdapter itemHotVideoListAdapter = new ItemHotVideoListAdapter(context);
            itemHotVideoListAdapter.setData(soundList);
            ((HotViewHolder) holder).rvSoundHot.setAdapter(itemHotVideoListAdapter);
            ((HotViewHolder) holder).rvSoundHot.setLayoutManager(new GridLayoutManager(context,3));
            Glide.with(context).load(R.drawable.stars).into(((HotViewHolder) holder).imgSoundHot);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return soundList.size();
    }


    protected class SoundHeadViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linSound1;
        private ImageView imgSound1;
        private TextView tvSound1;
        private LinearLayout linSound2;
        private ImageView imgSound2;
        private TextView tvSound2;
        private LinearLayout linSound3;
        private ImageView imgSound3;
        private TextView tvSound3;
        private LinearLayout linSound4;
        private ImageView imgSound4;
        private TextView tvSound4;
        // private BannerViewPager vpBanner;
        private ViewPager vpBanner;
        private Indicator dotsIndicator;

        public SoundHeadViewHolder(View view) {
            super(view);
            linSound1 = (LinearLayout) view.findViewById(R.id.lin_sound_1);
            imgSound1 = (ImageView) view.findViewById(R.id.img_sound_1);
            tvSound1 = (TextView) view.findViewById(R.id.tv_sound_1);
            linSound2 = (LinearLayout) view.findViewById(R.id.lin_sound_2);
            imgSound2 = (ImageView) view.findViewById(R.id.img_sound_2);
            tvSound2 = (TextView) view.findViewById(R.id.tv_sound_2);
            linSound3 = (LinearLayout) view.findViewById(R.id.lin_sound_3);
            imgSound3 = (ImageView) view.findViewById(R.id.img_sound_3);
            tvSound3 = (TextView) view.findViewById(R.id.tv_sound_3);
            linSound4 = (LinearLayout) view.findViewById(R.id.lin_sound_4);
            imgSound4 = (ImageView) view.findViewById(R.id.img_sound_4);
            tvSound4 = (TextView) view.findViewById(R.id.tv_sound_4);
            //vpBanner = (BannerViewPager) view.findViewById(R.id.vp_banner_sound);
            vpBanner = (ViewPager) view.findViewById(R.id.vp_banner_sound);
            dotsIndicator = (Indicator) view.findViewById(R.id.dots_indicator_sound);
        }
    }
    protected class HotViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout hotTitle;
        private ImageView imgSoundHot;
        private TextView tvHotTitle;
        private RecyclerView rvSoundHot;
        private TextView tvSound;
        private BannerViewPager viewPager;
        private DotsIndicator dotsIndicator;

        public HotViewHolder(View view) {
            super(view);
            hotTitle = (LinearLayout) view.findViewById(R.id.hot_title);
            imgSoundHot = (ImageView) view.findViewById(R.id.img_sound_hot);
            tvHotTitle = (TextView) view.findViewById(R.id.tv_hot_title);
            rvSoundHot = (RecyclerView) view.findViewById(R.id.rv_sound_hot);
            tvSound = (TextView) view.findViewById(R.id.tv_sound);
            viewPager = (BannerViewPager) view.findViewById(R.id.vp_banner_sound);
            dotsIndicator = (DotsIndicator) view.findViewById(R.id.dots_indicator_sound);
        }
    }
    private IndicatorViewPager.IndicatorViewPagerAdapter bannerAdapter=new IndicatorViewPager.IndicatorViewPagerAdapter() {
        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(container.getContext());
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                //convertView = new ImageView(getActivity());
                convertView = LayoutInflater.from(context).inflate(R.layout.item_banner, container, false);
                convertView.setLayoutParams(new ViewGroup.LayoutParams(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL));
            }
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, container, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_banner);
            // ImageView imageView = convertView.findViewById(R.id.iv_banner);
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(context).load(imageList.get(position)).into(imageView);
            return convertView;
        }

    };
}
