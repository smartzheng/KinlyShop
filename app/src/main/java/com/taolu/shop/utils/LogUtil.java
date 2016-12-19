package com.taolu.shop.utils;

import android.util.Log;

/**
 * Log调试信息工具类
 * Created by m1858 on 2016/11/22.
 */
public class LogUtil {
    private static final boolean isDebug = true;


    public static void e(String tag,String msg) {
        if(isDebug) {
            Log.e(tag,msg);
        }
    }
}
