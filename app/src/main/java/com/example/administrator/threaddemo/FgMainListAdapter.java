package com.example.administrator.threaddemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.threaddemo.mowen.Banner;
import com.example.administrator.threaddemo.mowen.BannerConfig;
import com.example.administrator.threaddemo.mowen.BannerDotsIndicator;
import com.example.administrator.threaddemo.mowen.listener.OnBannerListener;
import com.example.administrator.threaddemo.mowen.loader.ImageLoader;
import com.example.administrator.threaddemo.viewPagerIndicator.BannerComponent;
import com.example.administrator.threaddemo.viewPagerIndicator.IndicatorViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FgMainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "FgMainListAdapter";
    private static final int TYPE_HEADER = 01;
    private static final int TYPE_HOT_ASK = 02;
    private static final int TYPE_HOT_VIDEO = 03;
    private static final int TYPE_SING = 04;
    private static final int TYPE_PIA = 05;
    private static final int TYPE_RECOMMEND = 06;
    private static final int TYPE_FOOTER = 07;

    private List<String> mImageList = new ArrayList<String>();
    private List<UserBean> userBeans = new ArrayList<>();
    private List<View> dots = new ArrayList<View>();
    private RecyclerView.ViewHolder bannerView;
    private int oldPoints = 0;
    private TimerTask timerTask;
    private Timer timer = new Timer();
    private Context context;
    private boolean isAddItemDecoration = false;
    private View headerView;
    MyItemDecoration itemDecoration = new MyItemDecoration();
    private BannerComponent bannerComponent;

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public int getRealPosition(BannerViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    public FgMainListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> mImageList, List<UserBean> userBeans) {
        this.mImageList = mImageList;
        this.userBeans = userBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            if (bannerView != null) {
                return bannerView;
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.banner_main, parent, false);
                bannerView = new BannerViewHolder(view);
                return bannerView;
            }
        } else if (viewType == TYPE_HOT_ASK) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hot_ask, parent, false);
            return new HotAskViewHolder(view);
        } else if (viewType == TYPE_HOT_VIDEO) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hot_video, parent, false);
            return new HotVideoViewHolder(view);
        } else if (viewType == TYPE_SING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sing, parent, false);
            return new SingViewHolder(view);
        } else if (viewType == TYPE_PIA) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pia, parent, false);
            return new PiaViewHolder(view);
        } else if (viewType == TYPE_RECOMMEND) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recommend, parent, false);
            return new RecommendViewHolder(view);
        }  else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            return new ListViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
    /*    if (bannerComponent != null) {
            bannerComponent.setAutoPlayTime(2000);
            bannerComponent.startAutoPlay();

        }*/
        if (holder instanceof BannerViewHolder) {
          /*  if (bannerComponent == null) {
                bannerComponent = new BannerComponent(((BannerViewHolder) holder).dotsIndicator,
                        ((BannerViewHolder) holder).viewPager, false);
            }
            ((BannerViewHolder) holder).dotsIndicator.setDotsCount(mImageList.size());
            ((BannerViewHolder) holder).dotsIndicator.setViewPager(((BannerViewHolder) holder).viewPager);
            bannerComponent.setAdapter(bannerAdapter);*/
            ((BannerViewHolder) holder).viewPager.setImages(mImageList);
            ((BannerViewHolder) holder).viewPager.start();
            ((BannerViewHolder) holder).dotsIndicator.setViewPager(((BannerViewHolder) holder).viewPager.getViewPager());
        } else if (holder instanceof ListViewHolder) {
            ItemAskAdapter itemAskAdapter = new ItemAskAdapter(context);
            itemAskAdapter.setData(userBeans);
            ((ListViewHolder) holder).rvAskList.setAdapter(itemAskAdapter);

            if (isAddItemDecoration == false) {
                ((ListViewHolder) holder).rvAskList.addItemDecoration(itemDecoration);
                isAddItemDecoration = true;
            }
            Log.i(TAG, "onBindViewHolder: add");
            ((ListViewHolder) holder).rvAskList.setLayoutManager(new LinearLayoutManager(context));
        } else if (holder instanceof HotAskViewHolder) {
            /* */
            Glide.with(context).load(R.drawable.main).into(((HotAskViewHolder) holder).imgHotAsk);
            ItemHotAskListAdapter askListAdapter = new ItemHotAskListAdapter(context);
            List<HotAskBean> hotAskBeans = new ArrayList<HotAskBean>();
            ((HotAskViewHolder) holder).linHot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AllAskActivity.class);
                    context.startActivity(intent);
                    Log.i(TAG, "onClick: tvHot");
                }
            });
            for (int i = 0; i < 3; i++) {
                HotAskBean hotAskBean = new HotAskBean();
                hotAskBean.setAnswer(i);
                hotAskBean.setAsk("问题：" + i);
                hotAskBean.setImgPath("");
                hotAskBeans.add(hotAskBean);
            }
            askListAdapter.setData(hotAskBeans);
            ((HotAskViewHolder) holder).rvHotAsk.setLayoutManager(new LinearLayoutManager(context));
            ((HotAskViewHolder) holder).rvHotAsk.setAdapter(askListAdapter);
        } else if (holder instanceof HotVideoViewHolder) {
            Glide.with(context).load(R.drawable.video).into(((HotVideoViewHolder) holder).imgHotVideo);
            ItemHotVideoListAdapter askListAdapter = new ItemHotVideoListAdapter(context);
            List<HotVideoBean> hotVideoBeans = new ArrayList<HotVideoBean>();
            for (int i = 0; i < 4; i++) {
                HotVideoBean hotVideoBean = new HotVideoBean();
                hotVideoBean.setVideoPath("http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png");
                hotVideoBean.setDetail("视频标题" + i);
                hotVideoBeans.add(hotVideoBean);
            }
            askListAdapter.setData(hotVideoBeans);
            ((HotVideoViewHolder) holder).rvHotVideo.setLayoutManager(new GridLayoutManager(context, 2));
            ((HotVideoViewHolder) holder).rvHotVideo.setAdapter(askListAdapter);
        } else if (holder instanceof SingViewHolder) {
            Glide.with(context).load(R.drawable.music).into(((SingViewHolder) holder).imgSingVideo);
            ItemSingListAdapter singListAdapter = new ItemSingListAdapter(context);
            List<HotVideoBean> hotVideoBeans = new ArrayList<HotVideoBean>();
            for (int i = 0; i < 3; i++) {
                HotVideoBean hotVideoBean = new HotVideoBean();
                hotVideoBean.setVideoPath("http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png");
                hotVideoBean.setDetail("歌唱" + i);
                hotVideoBeans.add(hotVideoBean);
            }
            singListAdapter.setData(hotVideoBeans);
            ((SingViewHolder) holder).rvSingVideo.setLayoutManager(new GridLayoutManager(context, 3));
            ((SingViewHolder) holder).rvSingVideo.setAdapter(singListAdapter);
        } else if (holder instanceof PiaViewHolder) {
            Glide.with(context).load(R.drawable.pia).into(((PiaViewHolder) holder).imgPiaVideo);
            ItemPiaListAdapter piaListAdapter = new ItemPiaListAdapter(context);
            List<HotVideoBean> hotVideoBeans = new ArrayList<HotVideoBean>();
            for (int i = 0; i < 3; i++) {
                HotVideoBean hotVideoBean = new HotVideoBean();
                hotVideoBean.setVideoPath("http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png");
                hotVideoBean.setDetail("pia" + i);
                hotVideoBeans.add(hotVideoBean);
            }
            piaListAdapter.setData(hotVideoBeans);
            ((PiaViewHolder) holder).rvPiaVideo.setLayoutManager(new GridLayoutManager(context, 3));
            ((PiaViewHolder) holder).rvPiaVideo.setAdapter(piaListAdapter);
        } else if (holder instanceof RecommendViewHolder) {
            ((RecommendViewHolder) holder).tvAsk.setText("dasdasdasdasdsa");
            Glide.with(context).load(R.drawable.newicon).into(((RecommendViewHolder) holder).imgNew);
            Glide.with(context).load(R.drawable.stars).into(((RecommendViewHolder) holder).imgRec);

            ((RecommendViewHolder) holder).tvAnswer.setText("ddddddddddddddddd");
            ((RecommendViewHolder) holder).tvGood.setText(5 + "");
            ((RecommendViewHolder) holder).tvComment.setText(5 + "");
            ((RecommendViewHolder) holder).tvReward.setText("奖励 +3魅力");
            Glide.with(context).load(R.drawable.good).into(((RecommendViewHolder) holder).imgGood);
            Glide.with(context).load(R.drawable.comment).into(((RecommendViewHolder) holder).imgComment);
            ((RecommendViewHolder) holder).tvAnswerCount.setText(9 + "个回答");
            Glide.with(context).load(R.drawable.newicon).into(((RecommendViewHolder) holder).imgAns);
            Glide.with(context).load(R.drawable.pia).into(((RecommendViewHolder) holder).imgUser);
            ((RecommendViewHolder) holder).tvUser.setText("dasdsadas");

        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_HOT_ASK;
        } else if (position == 2) {
            return TYPE_HOT_VIDEO;
        } else if (position == 3) {
            return TYPE_SING;
        } else if (position == 4) {
            return TYPE_PIA;
        } else if (position == 5) {
            return TYPE_RECOMMEND;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return headerView == null ? userBeans.size() : userBeans.size() + 1;
    }

    protected class ListViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvAskList;

        public ListViewHolder(View view) {
            super(view);
            rvAskList = (RecyclerView) view.findViewById(R.id.rv_ask_list);
        }
    }


    protected class BannerViewHolder extends RecyclerView.ViewHolder {
        //private Banner banner_main;
        private Banner viewPager;
        //private AutoScrollViewPager viewPager;
        //private BannerViewPager viewPager;
        //private DotsIndicator dotsIndicator;
        private BannerDotsIndicator dotsIndicator;
        private SmartRefreshLayout refreshLayout;
        public BannerViewHolder(View view) {
            super(view);

            //banner_main = (Banner) view.findViewById(R.id.banner_main);
            viewPager = (Banner) view.findViewById(R.id.banner_m);

            dotsIndicator = (BannerDotsIndicator) view.findViewById(R.id.banner_indicator);
            viewPager.setBannerStyle(BannerConfig.NOT_INDICATOR);
            viewPager.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            viewPager.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    String url = null;
                    switch (position) {
                        case 0:
                            url = "http://www.wanandroid.com/navi";
                            break;
                        case 1:
                            url = "http://www.wanandroid.com/blog/show/2";
                            break;
                        case 2:
                            url = "http://www.wanandroid.com/project";
                            break;
                    }
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", url);
                    context.startActivity(intent);
                }
            });

        }
    }

    protected class HotAskViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHotAsk;
        private TextView tvHotTitle;
        private RecyclerView rvHotAsk;
        private LinearLayout linHot;

        public HotAskViewHolder(View view) {
            super(view);
            imgHotAsk = (ImageView) view.findViewById(R.id.img_hot_ask);
            tvHotTitle = (TextView) view.findViewById(R.id.tv_hot_title);
            rvHotAsk = (RecyclerView) view.findViewById(R.id.rv_hot_ask);
            linHot = (LinearLayout) view.findViewById(R.id.hot_ask);
        }

    }

    protected class HotVideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHotVideo;
        private RecyclerView rvHotVideo;
        private TextView tvHotVideo;


        public HotVideoViewHolder(View view) {
            super(view);
            imgHotVideo = (ImageView) view.findViewById(R.id.img_hot_video);
            rvHotVideo = (RecyclerView) view.findViewById(R.id.rv_hot_video);
            tvHotVideo = (TextView) view.findViewById(R.id.tv_hot_video);
        }
    }

    protected class SingViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSingVideo;
        private RecyclerView rvSingVideo;
        private TextView tvSingVideo;


        public SingViewHolder(View view) {
            super(view);
            imgSingVideo = (ImageView) view.findViewById(R.id.img_sing);
            rvSingVideo = (RecyclerView) view.findViewById(R.id.rv_sing);
            tvSingVideo = (TextView) view.findViewById(R.id.tv_sing);
        }
    }

    protected class PiaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPiaVideo;
        private RecyclerView rvPiaVideo;
        private TextView tvPiaVideo;


        public PiaViewHolder(View view) {
            super(view);
            imgPiaVideo = (ImageView) view.findViewById(R.id.img_pia);
            rvPiaVideo = (RecyclerView) view.findViewById(R.id.rv_pia);
            tvPiaVideo = (TextView) view.findViewById(R.id.tv_pia);
        }
    }

    protected class RecommendViewHolder extends RecyclerView.ViewHolder {
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
        private ImageView imgRec;


        public RecommendViewHolder(View view) {
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
            imgRec = (ImageView) view.findViewById(R.id.img_rec);
        }
    }


    private IndicatorViewPager.IndicatorViewPagerAdapter bannerAdapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        @Override
        public int getCount() {
            return mImageList.size();
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
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_banner);
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(context).load(mImageList.get(position)).into(imageView);
            return convertView;
        }

    };
}
