package com.itheima.rbcqt;

import android.app.Application;
import android.content.Context;

import org.senydevpkg.net.HttpLoader;

/**
 * Created by xiongmc on 2016/11/21.
 */
public class App extends Application{

    public static HttpLoader mLoader;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mLoader = HttpLoader.getInstance(this);
    }
}
