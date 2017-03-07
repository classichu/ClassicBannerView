package com.classichu.bannerview.helper;

import android.content.Context;
import android.widget.LinearLayout;

import com.classichu.bannerview.adapter.BannerPageAdapter;
import com.classichu.bannerview.bean.BannerBean;
import com.classichu.bannerview.widget.BannerViewPager;
import com.classichu.bannerview.widget.ClassicBannerView;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/7.
 */

public class ClassicBannerViewHelper {
    private static BannerViewPager mBannerViewPager;
    public static void initCallAtOnCreated(ClassicBannerView classicBannerView,
                                           List<BannerBean> bannerBeanList,
                                           int autoPlayDelaySeconds,boolean indicatorIsDot,int indicatorSize) {
        mBannerViewPager= classicBannerView.getBannerViewPager();
        mBannerViewPager.setAutoPlayDelaySeconds(autoPlayDelaySeconds);
        //
        LinearLayout mBannerIndicatorContainer= classicBannerView.getBannerIndicatorContainer();
        BannerPageAdapter pagerAdapter = new BannerPageAdapter(bannerBeanList,
                mBannerViewPager, mBannerIndicatorContainer, indicatorIsDot,indicatorSize);
        mBannerViewPager.setAdapter(pagerAdapter);
    }
    public static void initCallAtOnCreated(ClassicBannerView classicBannerView,
                                           List<BannerBean> bannerBeanList) {
        initCallAtOnCreated(classicBannerView,bannerBeanList,4,true,dp2px(classicBannerView.getContext(),6));
    }

    public static void startAutoPlayCallAtOnResume() {
        if (mBannerViewPager!=null){
        mBannerViewPager.startAutoPlay();
    }
    }
    public static void stopAutoPlayCallAtOnStop() {
        if (mBannerViewPager!=null){
            mBannerViewPager.stopAutoPlay();
        }
    }

    /**
     * tool
     * @param context
     * @param dp
     * @return
     */
    private static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
