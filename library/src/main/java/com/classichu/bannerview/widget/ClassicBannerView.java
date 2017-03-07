package com.classichu.bannerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classichu.bannerview.R;

/**
 * Created by louisgeek on 2017/3/7.
 */

public class ClassicBannerView extends FrameLayout {
    private BannerViewPager id_banner_view_pager;
    private LinearLayout id_ll_banner_indicator_container;
    private TextView id_tv_banner_indicator_title;
    private LinearLayout id_ll_banner_indicator;

    public ClassicBannerView(@NonNull Context context) {
        this(context, null);
    }

    public ClassicBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicBannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ClassicBannerView);
        initAttrs(typedArray);
        typedArray.recycle();
    }

    private void initAttrs(TypedArray typedArray) {
        for (int i = 0; i <typedArray.getIndexCount() ; i++) {
           int index=typedArray.getIndex(i);
            if (index==R.styleable.ClassicBannerView_classic_indicatorContainerBackgroundColor){
                setIndicatorContainerBackgroundColor(typedArray.getColor(index, Color.WHITE));
            }else if (index==R.styleable.ClassicBannerView_classic_indicatorTextSize){
                 /**
                 * getDimension和getDimensionPixelOffset差不多，前者返回float，后者返回int
                 * 如果单位是dp或sp，则需要将其乘以density。如果是px，则不乘。
                 * 而getDimensionPixelSize则不管写的是dp，sp，px, 都会乘以denstiy.
                 */
                setIndicatorTextSize(typedArray.getDimensionPixelSize(index, dp2px(getContext(),14)));
            }else if (index==R.styleable.ClassicBannerView_classic_indicatorTextColor){
                setIndicatorTextColor(typedArray.getColor(index, Color.WHITE));
            }
        }
    }

    private void setIndicatorTextColor(int color) {
        id_tv_banner_indicator_title.setTextColor(color);
    }

    private void setIndicatorTextSize(int size) {
        id_tv_banner_indicator_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }

    private void setIndicatorContainerBackgroundColor(int color) {
        id_ll_banner_indicator_container.setBackgroundColor(color);
    }

    /**
     * tool
     * @param context
     * @param dp
     * @return
     */
    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    private void init(Context context) {
        View view = View.inflate(context, R.layout.layout_classic_banner_view, this);
        id_banner_view_pager = (BannerViewPager) view.findViewById(R.id.id_banner_view_pager);
        id_ll_banner_indicator_container = (LinearLayout) view.findViewById(R.id.id_ll_banner_indicator_container);
        id_tv_banner_indicator_title = (TextView) view.findViewById(R.id.id_tv_banner_indicator_title);
        id_ll_banner_indicator = (LinearLayout) view.findViewById(R.id.id_ll_banner_indicator);
    }
    public BannerViewPager getBannerViewPager() {
        return id_banner_view_pager;
    }

    public LinearLayout getBannerIndicatorContainer() {
        return id_ll_banner_indicator_container;
    }
}
