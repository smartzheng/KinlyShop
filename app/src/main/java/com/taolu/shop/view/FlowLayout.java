package com.taolu.shop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by admin on 2016/11/17.
 */
public class FlowLayout extends ViewGroup {

    private int horizontalSpacing = 15;//水平间距
    private int verticalSpacing = 15; //垂直间距
    //用来存放所有的Line对象
    private ArrayList<Line> lineList = new ArrayList<Line>();


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置水平间距
     */
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    /**
     * 设置垂直间距
     */
    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    /**
     * 分行,遍历所有子view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lineList.clear();
        //1.获取FlowLayout的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.获取用于实际宽度
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
        //3.遍历所有的子View
        Line line = new Line();
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(0, 0);
            //4.保证每行至少有一个子View;
            if (line.getViewList().size() == 0) {
                line.addLineView(childView);
                //5.如果当前line的宽+水平间距+子View的宽大于noPaddingWidth,则child需要换行
            } else if (line.getLineWidth() + horizontalSpacing + childView.getMeasuredWidth()
                    > noPaddingWidth) {
                lineList.add(line);
                line = new Line();
                line.addLineView(childView);
            } else {
                //6.说明当前child应该放入当前Line中
                line.addLineView(childView);
            }
            //7.如果当前child是最后的子View，那么需要保存最后的line对象
            if (i == (getChildCount() - 1)) {
                lineList.add(line);
            }
        }
        int height = getPaddingTop() + getPaddingTop();

        for (int i = 0; i < lineList.size(); i++) {
            height += lineList.get(i).getLineHeight();
        }
        height += (lineList.size() - 1) * verticalSpacing;
        //设置当前控件的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);//获取Line对象

            if (i > 0) {
                paddingTop += verticalSpacing + lineList.get(i - 1).getLineHeight();
            }

            ArrayList<View> viewList = line.getViewList();//获取line的view的集合

            //1.获取每行的留白的宽度
            int remainSpacing = getLineRemainSpacing(line);
            //2.计算每个view平均得到的值
            float perSpacing = remainSpacing / viewList.size();

            for (int j = 0; j < viewList.size(); j++) {
                View childView = viewList.get(j);
                //3.将得到的perSpacing增加到view的宽度上面
               // int widthSpec = MeasureSpec.makeMeasureSpec((int) (childView.getMeasuredWidth() + perSpacing), MeasureSpec.EXACTLY);
               // childView.measure(widthSpec, 0);

                if (j == 0) {
                    //如果是每行的第一个，那么直接靠左边摆放
                    childView.layout(paddingLeft, paddingTop, paddingLeft + childView.getMeasuredWidth(),
                            paddingTop + childView.getMeasuredHeight());
                } else {
                    View preView = viewList.get(j - 1);
                    //当前view的left是前一个view的right+水平间距
                    int left = preView.getRight() + horizontalSpacing;
                    childView.layout(left, preView.getTop(), left + childView.getMeasuredWidth(), preView.getBottom());
                }
            }
        }

    }

    /**
     * 获取指定line的留白
     */
    private int getLineRemainSpacing(Line line) {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - line.getLineWidth();
    }

    /**
     * 封装所有子view
     */
    class Line {
        private ArrayList<View> viewList;
        private int width;
        private int height;

        public Line() {
            viewList = new ArrayList<View>();
        }

        /**
         * 记录子view
         */
        public void addLineView(View child) {
            if (!viewList.contains(child)) {
                viewList.add(child);
                //更新Line的width
                if (viewList.size() == 1) {
                    width = child.getMeasuredWidth();
                } else {
                    width += child.getMeasuredWidth() + horizontalSpacing;
                }
                //更新line的height
                height = Math.max(height, child.getMeasuredHeight());
            }
        }

        public int getLineWidth() {
            return width;
        }

        public int getLineHeight() {
            return height;
        }

        public ArrayList<View> getViewList() {
            return viewList;
        }


    }
}
