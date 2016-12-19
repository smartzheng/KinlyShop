package com.taolu.shop.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;

/**
 * Created by m1858 on 2016/11/22.
 */
public class RedBabyApplication extends Application {
    public static Context context;
    public static Handler mainHandler;//主线程的handler
    public static ViewPager mViewPager;
    /**
     * 数据库
     */
    /**
     * app的入口函数
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //1.初始化Context,Android三大Context:Application,Activity,Service

        context = this;
        //2.初始化mainHandler
        mainHandler = new Handler();
//		new Thread(){
//			public void run() {
//				//在子线程创建handler
//				mainHandler = new Handler();
//				Looper.prepare();//创建轮询器
//				Looper.loop();//开启轮询器
//				mainHandler.sendEmptyMessage(0);
//			};
//		}.start();

        //初始化ImageLoader
        //ImageLoader.getInstance().init( ImageLoaderConfiguration.createDefault(this));
    }
}
