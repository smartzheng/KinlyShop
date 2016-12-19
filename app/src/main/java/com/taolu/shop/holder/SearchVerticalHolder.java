package com.taolu.shop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.SearchShow;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

import java.util.Random;

/**
 * Created by admin on 2016/11/23.
 */
public class SearchVerticalHolder extends BaseHolder<SearchShow.ProductListBean> {
    private ImageView iv_search_foods; //图片
    private TextView tv_serach_titles; //名称
    private TextView tv_serach_marketPrices; //市场价
    private TextView tv_serach_prices; //会员价
    private TextView tv_search_saless; //销量
    private TextView tv_haoping; //评论
    private TextView tv_search_buyy; //抵消


    @Override
    public View initHolderView() {

        View view = View.inflate(RedBabyApplication.context, R.layout.search_show_vertical, null);
        iv_search_foods = (ImageView) view.findViewById(R.id.iv_search_foods);
        tv_serach_titles = (TextView) view.findViewById(R.id.tv_serach_titles);
        tv_serach_marketPrices = (TextView) view.findViewById(R.id.tv_serach_marketPrices);
        tv_serach_prices = (TextView) view.findViewById(R.id.tv_serach_prices);
        tv_search_saless = (TextView) view.findViewById(R.id.tv_search_saless);
        tv_haoping = (TextView) view.findViewById(R.id.tv_haoping);
        tv_search_buyy = (TextView) view.findViewById(R.id.tv_search_buyy);
        return view;
    }

    @Override
    public void bindData(SearchShow.ProductListBean info) {
        HttpLoader.getInstance(RedBabyApplication.context).display(iv_search_foods,
                Api.URL_SEARCH_SERVER + info.getPic(), R.drawable.image_loader_failure,
                R.drawable.image_loader_failure, 0, 0, ImageView.ScaleType.CENTER_CROP);
        tv_serach_titles.setText(info.getName());
        tv_serach_prices.setText("" + info.getPrice());
        tv_serach_marketPrices.setText("" + info.getMarketPrice());
        int i = test();
        tv_search_saless.setText("月销量" + i);
        int j = tetsx();
        tv_haoping.setText(j + "条评论");
        int K = Buy() + 1;
        tv_search_buyy.setText("趣金币可抵" + K + "%");
    }

    /**
     * 销量随机数
     */
    private int test() {
        Random random = new Random();
        int i = random.nextInt(10000);
        return i;
    }

    /**
     * 购买随机数
     */
    private int tetsx() {
        Random random = new Random();
        int j = random.nextInt(10000);
        return j;
    }

    /**
     * 抵消百分比
     */
    private int Buy() {
        Random random = new Random();
        int k = random.nextInt(3);
        return k;
    }

}
