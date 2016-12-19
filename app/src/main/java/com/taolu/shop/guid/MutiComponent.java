package com.taolu.shop.guid;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blog.www.guideview.Component;
import com.taolu.shop.R;

/**
 * Created by binIoter on 16/6/17.
 */
public class MutiComponent implements Component {

  @Override public View getView(LayoutInflater inflater) {
    LinearLayout ll = new LinearLayout(inflater.getContext());
    LinearLayout.LayoutParams param =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    ll.setOrientation(LinearLayout.VERTICAL);
    ll.setLayoutParams(param);
    TextView textView = new TextView(inflater.getContext());
    textView.setText("这里有折扣哦！");
    textView.setTextColor(inflater.getContext().getResources().getColor(R.color.color_white));
    textView.setTextSize(16);
    ImageView imageView = new ImageView(inflater.getContext());
    imageView.setImageResource(R.mipmap.arrow);
    ll.removeAllViews();
    ll.addView(textView);
    ll.addView(imageView);
    return ll;
  }

  @Override public int getAnchor() {
    return Component.ANCHOR_BOTTOM;
  }

  @Override public int getFitPosition() {
    return Component.FIT_CENTER;
  }

  @Override public int getXOffset() {
    return 0;
  }

  @Override public int getYOffset() {
    return 20;
  }
}
