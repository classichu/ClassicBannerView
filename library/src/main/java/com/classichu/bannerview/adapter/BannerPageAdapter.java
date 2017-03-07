package com.classichu.bannerview.adapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.classichu.bannerview.R;
import com.classichu.bannerview.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/13.
 */

public class BannerPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private boolean mIndicatorIsDot;
    private List<BannerBean> mBannerBeanList = new ArrayList<>();
    private TextView mBannerTitleTextView;
    private List<ImageView> mBannerIndicatorImageViewList = new ArrayList<>();

    public BannerPageAdapter(List<BannerBean> bannerBeanList, ViewPager viewPager,
                             LinearLayout bannerIndicatorContainer,
                             boolean indicatorIsDot,int indicatorSize) {
        mBannerBeanList = bannerBeanList;
        mIndicatorIsDot = indicatorIsDot;
        /**
         *
         */
        viewPager.addOnPageChangeListener(this);
        /**
         * 默认标题
         */
        mBannerTitleTextView = (TextView) bannerIndicatorContainer.findViewById(R.id.id_tv_banner_indicator_title);
        mBannerTitleTextView.setText(mBannerBeanList.get(0).getImageText());

        /**
         * 默认指示器
         */
        LinearLayout indicatorLinearLayout = (LinearLayout) bannerIndicatorContainer.findViewById(R.id.id_ll_banner_indicator);
        for (int i = 0; i < mBannerBeanList.size(); i++) {
            ImageView imageView = new ImageView(indicatorLinearLayout.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(indicatorSize, indicatorSize);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (mIndicatorIsDot) {
                imageView.setBackgroundResource(R.drawable.selector_banner_dot);//默认
            } else {
                imageView.setBackgroundColor(Color.WHITE);//默认
            }
            //把指示作用的原点图片加入底部的视图中
            indicatorLinearLayout.addView(imageView);
            /**
             *加入List
             */
            mBannerIndicatorImageViewList.add(imageView);
        }
        changeIndicatorState(0);
    }

    @Override
    public int getCount() {
        //将count设置成整数最大值，虚拟实现无线轮播
        return Integer.MAX_VALUE;
        //return mBannerBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int realPos = position % mBannerBeanList.size();
        final BannerBean imageBannerBean = mBannerBeanList.get(realPos);
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_banner, null, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, realPos, imageBannerBean);
                }
            }
        });
        ImageView id_iv_image_banner = (ImageView) view.findViewById(R.id.id_iv_image_banner);

        Glide.with(id_iv_image_banner.getContext()).load(imageBannerBean.getImageUrl())
                //.placeholder(R.mipmap.ic_image_no)
                //.error(R.mipmap.ic_image_no)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(id_iv_image_banner);

        /***
         *addView
         */
        container.addView(view);
        //return super.instantiateItem(container, position);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int realPos = position % mBannerBeanList.size();
        mBannerTitleTextView.setText(mBannerBeanList.get(realPos).getImageText());
        /**
         *改变状态
         */
        changeIndicatorState(realPos);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        /**
         * setCurrentItem会回调
         * OnPageScrollStateChanged   state=2   ViewPager.SCROLL_STATE_SETTLING
         * OnPageScrollStateChanged   state=0   ViewPager.SCROLL_STATE_IDLE
         *
         * 手动触摸切换会触发
         * OnPageScrollStateChanged   state=1   ViewPager.SCROLL_STATE_DRAGGING
         * OnPageScrollStateChanged   state=2   ViewPager.SCROLL_STATE_SETTLING
         * OnPageScrollStateChanged   state=0   ViewPager.SCROLL_STATE_IDLE
         */
    }

    /**
     * 改变指示器状态
     *
     * @param nowIndex
     */
    private void changeIndicatorState(int nowIndex) {

        for (int i = 0; i < mBannerIndicatorImageViewList.size(); i++) {
            if (i == nowIndex) {
                if (mIndicatorIsDot) {
                    mBannerIndicatorImageViewList.get(i).setSelected(true);
                } else {
                    mBannerIndicatorImageViewList.get(i).setBackgroundColor(Color.WHITE);
                }

            } else {
                if (mIndicatorIsDot) {
                    mBannerIndicatorImageViewList.get(i).setSelected(false);
                } else {
                    mBannerIndicatorImageViewList.get(i).setBackgroundColor(Color.GRAY);
                }

            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position, Object data);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
