package com.classichu.classicbannerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.classichu.bannerview.bean.BannerBean;
import com.classichu.bannerview.helper.ClassicBannerViewHelper;
import com.classichu.bannerview.widget.ClassicBannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<BannerBean> mBannerBeanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        ClassicBannerView id_classic_banner_view = (ClassicBannerView) findViewById(R.id.id_classic_banner_view);
        ClassicBannerViewHelper.initCallAtOnCreated(id_classic_banner_view,mBannerBeanList);

    }

    private void initData() {
        for (int i = 0; i < ImagesDatas.imageThumbUrls.length; i++) {
            if (i > 4) {
                break;
            }
            BannerBean imageBannerBean = new BannerBean();
            imageBannerBean.setImageUrl(ImagesDatas.imageThumbUrls[i]);
            imageBannerBean.setImageText("图片xx" + i);
            mBannerBeanList.add(imageBannerBean);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        ClassicBannerViewHelper.startAutoPlayCallAtOnResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ClassicBannerViewHelper.stopAutoPlayCallAtOnStop();
    }
}