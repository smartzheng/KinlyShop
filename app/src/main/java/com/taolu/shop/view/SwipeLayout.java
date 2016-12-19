package com.taolu.shop.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * 侧滑面板
 * @author poplar
 *
 */
public class SwipeLayout extends FrameLayout {
	
	public static enum Status {
		Close, Open, Swiping
	}
	
	public interface OnSwipeListener{
		void onClose(SwipeLayout layout);
		void onOpen(SwipeLayout layout);
		
		void onStartOpen(SwipeLayout layout);
		void onStartClose(SwipeLayout layout);
	}

	private Status status = Status.Close;
	private OnSwipeListener onSwipeListener;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public OnSwipeListener getOnSwipeListener() {
		return onSwipeListener;
	}

	public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
		this.onSwipeListener = onSwipeListener;
	}

	private ViewDragHelper mHelper;
	
	public SwipeLayout(Context context) {
		this(context, null);
	}

	public SwipeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		// 1. 创建ViewDragHelper
		mHelper = ViewDragHelper.create(this, callback);
	}
	
	// 3. 重写回调的方法
	ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
		
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return true;
		}
		
		public int getViewHorizontalDragRange(View child) {
			return mRange;
		};
		
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// 返回的值决定了将要移动到的位置.
			if(child == mFrontView){
				if(left < - mRange){
					// 限定左范围
					return - mRange;
				}else if (left > 0) {
					// 限定右范围
					return 0;
				}
			}else if (child == mBackView) {
				if(left < mWidth - mRange){
					// 限定左范围
					return mWidth - mRange;
				}else if (left > mWidth) {
					// 限定右范围
					return mWidth;
				}
			}
			return left;
		};
		
		// 位置发生改变的时候, 把水平方向的偏移量传递给另一个布局
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			if(changedView == mFrontView){
				// 拖拽的是前布局,  把刚刚发生的 偏移量dx 传递给 后布局
				mBackView.offsetLeftAndRight(dx);
			} else if (changedView == mBackView) {
				// 拖拽的是后布局,  把刚刚发生的 偏移量dx 传递给 前布局
				mFrontView.offsetLeftAndRight(dx);
			}
			
			dispatchDragEvent();
			
			invalidate();
		};
		
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			// 松手时候会被调用
			
			// xvel 向右+, 向左-
			if(xvel == 0 && mFrontView.getLeft() < - mRange * 0.5f){
				open();
			}else if(xvel < 0){
				open();
			}else {
				close();
			}
		};
	};
	private View mBackView;
	private View mFrontView;
	private int mRange;
	private int mWidth;
	private int mHeight;
	
	// 2. 转交触摸事件拦截判断, 处理触摸事件
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mHelper.shouldInterceptTouchEvent(ev);
	};
	
	/**
	 * 更新当前的状态
	 */
	protected void dispatchDragEvent() {
		
		
		Status lastStatus = status;
		// 获取最新的状态
		status = updateStatus();
		
		// 状态改变的时候, 调用监听里的方法
		if(lastStatus != status && onSwipeListener != null){
			if(status == Status.Open){
				onSwipeListener.onOpen(this);
			}else if (status == Status.Close) {
				onSwipeListener.onClose(this);
			}else if (status == Status.Swiping) {
				if(lastStatus == Status.Close){
					onSwipeListener.onStartOpen(this);
				}else if (lastStatus == Status.Open) {
					onSwipeListener.onStartClose(this);
				}
			}
		}
		
		
		
	}

	private Status updateStatus() {
		int left = mFrontView.getLeft();
		if(left == 0){
			return Status.Close;
		}else if (left == -mRange) {
			return Status.Open;
		}
		
		return Status.Swiping;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		// 维持平滑动画继续
		if(mHelper.continueSettling(true)){
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	/**
	 * 关闭
	 */
	protected void close() {
		close(true);
	}
	public void close(boolean isSmooth){
		
		int finalLeft = 0;
		if(isSmooth){
			// 触发平滑动画
			if(mHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)){
				ViewCompat.postInvalidateOnAnimation(this);
			}
			
		}else {
			layoutContent(false);
		}
	}

	/**
	 * 打开
	 */
	protected void open() {
		open(true);
	}
	public void open(boolean isSmooth){
		
		int finalLeft = -mRange;
		if(isSmooth){
			// 触发平滑动画
			if(mHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)){
				ViewCompat.postInvalidateOnAnimation(this);
			}
			
		}else {
			layoutContent(false);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y2=0 ;
		float y1=0;
		float x1=0;
		float x2=0;

		switch (event.getAction()){
			case MotionEvent.ACTION_HOVER_MOVE:
				y2 = event.getY();
				ViewParent parent = this.getParent();
				if(Math.abs(y2-y1)>(Math.abs(x1-x2)+10)){

					parent.requestDisallowInterceptTouchEvent(false);

					return true;
				}else {

					parent.requestDisallowInterceptTouchEvent(true);

				}
				break;
			case MotionEvent.ACTION_UP:

				break;
			case MotionEvent.ACTION_DOWN:
				 y1 = event.getY();


		}
		try {
			mHelper.processTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		// 默认是关闭状态
		layoutContent(false);
	}

	/**
	 * 根据当前的开启状态摆放内容
	 * @param isOpen
	 */
	private void layoutContent(boolean isOpen) {
		// 设置前布局位置
		Rect rect = computeFrontRect(isOpen);
		mFrontView.layout(rect.left, rect.top, rect.right, rect.bottom);
		// 根据前布局位置设置后布局位置
		Rect backRect = computeBackRectViaFront(rect);
		mBackView.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);
		
		// 把任意布局顺序调整到最上
		bringChildToFront(mFrontView);
	}

	/**
	 * 计算后布局的矩形区域
	 * @param rect
	 * @return
	 */
	private Rect computeBackRectViaFront(Rect rect) {
		int left = rect.right;
		return new Rect(left, 0, left + mRange , 0 + mHeight);
	}

	/**
	 * 计算前布局的矩形区域
	 * @param isOpen
	 * @return
	 */
	private Rect computeFrontRect(boolean isOpen) {
		int left = 0;
		if(isOpen){
			left = -mRange;
		}
		return new Rect(left, 0, left + mWidth, 0 + mHeight);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		mRange = mBackView.getMeasuredWidth();
		
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		mBackView = getChildAt(0);
		mFrontView = getChildAt(1);
		
	}

}
