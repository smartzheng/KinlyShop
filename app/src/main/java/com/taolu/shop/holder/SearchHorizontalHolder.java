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
public class SearchHorizontalHolder extends BaseHolder<SearchShow.ProductListBean> {
    /**
     * id : 14
     * marketPrice : 300
     * name : 萌妹外套
     * pic : /images/product/detail/q12.jpg
     * price : 160
     */
    private ImageView iv_serach_namel;    // 图片
    private TextView tv_serach_titlel;  // 标题
    private TextView tv_serach_marketPricel; // 市场价
    private TextView tv_serach_pricel; // 会员价
    private TextView tv_search_sales; //销量
    private TextView tv_search_people; //评论
    private TextView tv_search_buy; //抵消
    String[] Local = {"北京", "上海", "广州", "深圳", "重庆"};
    private TextView tv_search_local; //产地


    @Override
    public View initHolderView() {
        View inflate = View.inflate(RedBabyApplication.context, R.layout.search_show_horizontal, null);
        iv_serach_namel = (ImageView) inflate.findViewById(R.id.iv_serach_namel);
        tv_serach_titlel = (TextView) inflate.findViewById(R.id.tv_serach_titlel);
        tv_serach_marketPricel = (TextView) inflate.findViewById(R.id.tv_serach_marketPricel);
        tv_serach_pricel = (TextView) inflate.findViewById(R.id.tv_serach_pricel);
        tv_search_sales = (TextView) inflate.findViewById(R.id.tv_search_sales);
        tv_search_people = (TextView) inflate.findViewById(R.id.tv_search_people);
        tv_search_buy = (TextView) inflate.findViewById(R.id.tv_search_buy);
        tv_search_local = (TextView) inflate.findViewById(R.id.tv_search_local);
        return inflate;
    }

    @Override
    public void bindData(SearchShow.ProductListBean info) {
        HttpLoader.getInstance(RedBabyApplication.context).display(iv_serach_namel,
                Api.URL_SEARCH_SERVER + info.getPic(), R.drawable.image_loader_failure,
                R.drawable.image_loader_failure, 0, 0, ImageView.ScaleType.CENTER_CROP);
        tv_serach_titlel.setText(info.getName());
        tv_serach_marketPricel.setText(info.getMarketPrice() + "");
        tv_serach_pricel.setText(info.getPrice() + "");
        int msales = Sales(); //月销量
        tv_search_sales.setText("月销量" + msales + "笔");
        int mnumber = Number(); //购买人数
        tv_search_people.setText(mnumber + "人付款");
        int mbuy = Buy() + 1;//可抵消
        tv_search_buy.setText("趣金币可抵" + mbuy + "%");
        tv_search_local.setText(MyLocal());

    }

    /**
     * 销量随机数
     */
    private int Sales() {
        Random random = new Random();
        int mySales = random.nextInt(10000);
        return mySales;
    }

    /**
     * 购买随机数
     */
    private int Number() {
        Random random = new Random();
        int mynumber = random.nextInt(10000);
        return mynumber;
    }

    /**
     * 抵消随机数
     */
    private int Buy() {
        Random random = new Random();
        int mybuy = random.nextInt(3);
        return mybuy;
    }

    /**
     * 产地随机地址
     */
    private String MyLocal() {
        Random random = new Random();
        int mylocal = random.nextInt(5);
        return Local[mylocal];
    }

}
