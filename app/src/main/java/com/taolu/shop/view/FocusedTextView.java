

package com.taolu.shop.view;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 自定义view重新写view复写里面的方法
 */
public class FocusedTextView extends TextView {
	public FocusedTextView(Context context) {
		super(context);
	}

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	//重写父类的方法,欺骗系统 textview直接获取到焦点的.
	@Override
	public boolean isFocused() {
		return true;
	}
}
