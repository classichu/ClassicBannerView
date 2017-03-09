# ClassicBannerView
经典简洁BannerView 循环滚动 BannerView

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency  [![](https://jitpack.io/v/classichu/ClassicBannerView.svg)](https://jitpack.io/#classichu/ClassicBannerView)

	dependencies {
	        compile 'com.github.classichu:ClassicBannerView:x.x.x'
	}




attrs

	  <com.classichu.bannerview.widget.ClassicBannerView
            android:id="@+id/id_classic_banner_view"
            android:layout_width="match_parent"
            app:classic_indicatorTextSize="16dp"
            app:classic_indicatorContainerBackgroundColor="@color/colorAccent"
            app:classic_indicatorTextColor="@color/colorPrimary"
            android:layout_height="230dp"/>