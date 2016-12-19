package com.taolu.shop.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.taolu.shop.global.RedBabyApplication;

public class CommonUtil {
	/**
	 * 在主线程执行一段任务
	 * @param r
	 */
	public static void runOnUIThread(Runnable r){
		RedBabyApplication.mainHandler.post(r);
	}
	/**
	 * 移除子View
	 * @param child
	 */
	public static void removeSelfFromParent(View child){
		if(child!=null){
			ViewParent parent = child.getParent();
			if(parent!=null && parent instanceof ViewGroup){
				ViewGroup group = (ViewGroup) parent;
				group.removeView(child);//移除子View
			}
		}
	}
	
	public static Drawable getDrawable(int id){
		return RedBabyApplication.context.getResources().getDrawable(id);
	}
	
	public static String getString(int id){
		return RedBabyApplication.context.getResources().getString(id);
	}
	public static String[] getStringArray(int id){
		return RedBabyApplication.context.getResources().getStringArray(id);
	}
	
	public static int getColor(int id){
		return RedBabyApplication.context.getResources().getColor(id);
	}
	/**
	 * 获取dp资源，并且会自动将dp值转为px值
	 * @param id
	 * @return
	 */
	public static int getDimens(int id){
		return RedBabyApplication.context.getResources().getDimensionPixelSize(id);
	}
}
