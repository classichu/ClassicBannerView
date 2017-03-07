package com.classichu.bannerview.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by louisgeek on 2016/12/13.
 */

public class BannerViewPager extends ViewPager {
    private boolean mIsTouching;
    private boolean mIsAutoPlay;
    private int mAutoPlayDelaySeconds=3;


    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAutoPlayDelaySeconds(int autoPlayDelaySeconds) {
        mAutoPlayDelaySeconds = autoPlayDelaySeconds;
    }
    /**
     * May in onResume
     */
    public void startAutoPlay() {
        mIsAutoPlay = true;
    }

    /**
     * May in onStop
     */
    public void stopAutoPlay() {
        mIsAutoPlay = false;
    }

    /**
     *
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startPlay();
    }

    /**
     *
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopPlay();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIsTouching = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsTouching = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     *
     */
    private ScheduledExecutorService mExecutor;

    private void startPlay() {
        mExecutor = Executors.newSingleThreadScheduledExecutor();
        Runnable runnableCmd = new Runnable() {
            @Override
            public void run() {
                if (!mIsTouching && mIsAutoPlay) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int newItemPos = getCurrentItem() + 1;
                            setCurrentItem(newItemPos);
                        }
                    });
                }
            }
        };
        /**
         * scheduleWithFixedDelay 把任务执行完成后再延迟固定时间后再执行下一次
         */
        mExecutor.scheduleWithFixedDelay(runnableCmd, mAutoPlayDelaySeconds*1000, mAutoPlayDelaySeconds*1000, TimeUnit.MILLISECONDS);
        /**
         * scheduleAtFixedRate  不受任务执行时间的影响
         */
        ///###mExecutor.scheduleAtFixedRate(runnableCmd, 3 * 1000, 3 * 1000, TimeUnit.MILLISECONDS);

    }

    /**
     * Tool
     * @param runnable
     */
    public void runOnUiThread(Runnable runnable) {
        Handler mHandler = new Handler(Looper.getMainLooper());
        //通过查看Thread类的当前线程
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    private void stopPlay() {
        if (mExecutor != null && !mExecutor.isShutdown()) {
            mExecutor.shutdown();
        }
    }
}
