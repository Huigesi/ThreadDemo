package com.example.administrator.threaddemo.mowen;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.threaddemo.R;

import java.util.ArrayList;
import java.util.List;


public class BannerDotsIndicator extends LinearLayout {
    private static final String TAG = "DotsIndicator";

    private static final int DEFAULT_POINT_COLOR = Color.CYAN;
    public static final float DEFAULT_WIDTH_FACTOR = 2.5f;

    private List<ImageView> dots;
    private ViewPager viewPager;
    private float dotsSize;
    private float dotsCornerRadius;
    private float dotsSpacing;
    private int currentPage;
    private float dotsWidthFactor;
    private int dotsColor;

    private boolean dotsClickable;
    private ViewPager.OnPageChangeListener pageChangedListener;

    public BannerDotsIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public BannerDotsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BannerDotsIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        dots = new ArrayList<>();
        setOrientation(HORIZONTAL);

        dotsSize = dpToPx(16); // 16dp
        dotsSpacing = dpToPx(4); // 4dp
        dotsCornerRadius = dotsSize / 2;

        dotsWidthFactor = DEFAULT_WIDTH_FACTOR;
        dotsColor = DEFAULT_POINT_COLOR;
        dotsClickable = true;

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DotsIndicator);

            dotsColor = a.getColor(R.styleable.DotsIndicator_dotsColor, DEFAULT_POINT_COLOR);
            setUpCircleColors(dotsColor);

            dotsWidthFactor = a.getFloat(R.styleable.DotsIndicator_dotsWidthFactor, 2.5f);
            if (dotsWidthFactor < 1) {
                dotsWidthFactor = 2.5f;
            }

            dotsSize = a.getDimension(R.styleable.DotsIndicator_dotsSize, dotsSize);
            dotsCornerRadius =
                    (int) a.getDimension(R.styleable.DotsIndicator_dotsCornerRadius, dotsSize / 2);
            dotsSpacing = a.getDimension(R.styleable.DotsIndicator_dotsSpacing, dotsSpacing);

            a.recycle();
        } else {
            setUpCircleColors(DEFAULT_POINT_COLOR);
        }

        if (isInEditMode()) {
            addDots(5);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refreshDots();
    }

    private void refreshDots() {
        if (viewPager != null && viewPager.getAdapter() != null) {
            // Check if we need to refresh the dots count
            PagerAdapter adapter = viewPager.getAdapter();
            int count = adapter instanceof Banner.BannerPagerAdapter ?
                    Math.max(0, adapter.getCount() - 2) : adapter.getCount();
            if (dots.size() < count) {
                addDots(count - dots.size());
            } else if (dots.size() > count) {
                removeDots(dots.size() - count);
            }
            setUpDotsAnimators();
        } else {
            Log.e(BannerDotsIndicator.class.getSimpleName(),
                    "You have to set an adapter to the view pager before !");
        }
    }

    private void addDots(int count) {
        for (int i = 0; i < count; i++) {
            View dot = LayoutInflater.from(getContext()).inflate(R.layout.dot_layout, this, false);
            ImageView imageView = (ImageView) dot.findViewById(R.id.dot);
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.width = params.height = (int) dotsSize;
            params.setMargins((int) dotsSpacing, 0, (int) dotsSpacing, 0);
            ((GradientDrawable) imageView.getBackground()).setCornerRadius(dotsCornerRadius);
            ((GradientDrawable) imageView.getBackground()).setColor(dotsColor);

            /*final int finalI = i;
            dot.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dotsClickable
                            && viewPager != null
                            && viewPager.getAdapter() != null
                            && finalI < viewPager.getAdapter().getCount()) {
                        viewPager.setCurrentItem(finalI, true);
                    }
                }
            });*/

            dots.add(imageView);
            addView(dot);
        }
    }

    private void removeDots(int count) {
        for (int i = 0; i < count; i++) {
            removeViewAt(getChildCount() - 1);
            dots.remove(dots.size() - 1);
        }
    }

    private void setUpDotsAnimators() {
        if (viewPager != null
                && viewPager.getAdapter() != null
                && viewPager.getAdapter().getCount() > 0) {
            if (viewPager.getAdapter() instanceof Banner.BannerPagerAdapter) {
                if (viewPager.getAdapter().getCount() <= 2)
                    return;
            }
            if (currentPage < dots.size()) {
                View dot = dots.get(currentPage);

                if (dot != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dot.getLayoutParams();
                    params.width = (int) dotsSize;
                    dot.setLayoutParams(params);
                }
            }

            currentPage = viewPager instanceof BannerViewPager ? viewPager.getCurrentItem() - 1
                    : viewPager.getCurrentItem();
            if (currentPage >= dots.size()) {
                currentPage = dots.size() - 1;
                viewPager.setCurrentItem(currentPage, false);
            }
            View dot = dots.get(currentPage);

            if (dot != null) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dot.getLayoutParams();
                params.width = (int) (dotsSize * dotsWidthFactor);
                dot.setLayoutParams(params);
            }
            if (pageChangedListener != null) {
                viewPager.removeOnPageChangeListener(pageChangedListener);
            }
            setUpOnPageChangedListener();
            viewPager.addOnPageChangeListener(pageChangedListener);
        }
    }

    public int toRealPosition(int position) {
        if (FP.empty(dots))
            return position;
        int realPosition = (position - 1) % FP.size(dots);
        if (realPosition < 0)
            realPosition += FP.size(dots);
        return realPosition;
    }

    private void setUpOnPageChangedListener() {
        pageChangedListener = new ViewPager.OnPageChangeListener() {
            private int lastPage;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                lucency.logger.Log.d(TAG, "page scroll position " + position +
//                        ", current page " + currentPage + ", last page " + lastPage);
                if (viewPager instanceof BannerViewPager)
                    position = toRealPosition(position);

                if (position != currentPage && positionOffset == 0 || currentPage < position) {
                    setDotWidth(dots.get(currentPage), (int) dotsSize);
                    currentPage = position;
                }

                if (Math.abs(currentPage - position) > 1) {
                    setDotWidth(dots.get(currentPage), (int) dotsSize);
                    currentPage = lastPage;
                }

                ImageView dot = dots.get(currentPage);

                ImageView nextDot = null;
                if (currentPage == position && currentPage + 1 < dots.size()) {
                    nextDot = dots.get(currentPage + 1);
                } else if (currentPage > position) {
                    nextDot = dot;
                    dot = dots.get(currentPage - 1);
                }

                int dotWidth = (int) (dotsSize + (dotsSize * (dotsWidthFactor - 1) * (1 - positionOffset)));
                setDotWidth(dot, dotWidth);

                if (nextDot != null) {
                    int nextDotWidth =
                            (int) (dotsSize + (dotsSize * (dotsWidthFactor - 1) * (positionOffset)));
                    setDotWidth(nextDot, nextDotWidth);
                }
                Log.i(TAG, "onPageScrolled: "+position);
                lastPage = position;
            }

            private void setDotWidth(ImageView dot, int dotWidth) {
                ViewGroup.LayoutParams dotParams = dot.getLayoutParams();
                dotParams.width = dotWidth;
                dot.setLayoutParams(dotParams);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    }

    private void setUpCircleColors(int color) {
        if (dots != null) {
            for (ImageView elevationItem : dots) {
                ((GradientDrawable) elevationItem.getBackground()).setColor(color);
            }
        }
    }

    private void setUpViewPager() {
        if (viewPager.getAdapter() != null) {
            viewPager.getAdapter().registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    refreshDots();
                }
            });
        }
    }

    private int dpToPx(int dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp);
    }

    //*********************************************************
    // Users Methods
    //*********************************************************

    public void setPointsColor(int color) {
        setUpCircleColors(color);
    }

    public void setDotsClickable(boolean dotsClickable) {
        this.dotsClickable = dotsClickable;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        setUpViewPager();
        refreshDots();
    }
}
