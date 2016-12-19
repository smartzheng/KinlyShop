package com.taolu.shop.global;

/**
 * Created by ${ghx} on 2016/11/23 0023.
 */
public class Api {
    //杨丁主机
    public static final String URL_SERVER = "http://192.168.12.41:8080/RedBabyServer";
    //版本更新
    public static final String URL_version = URL_SERVER + "/version";
    //注册页面
    public static final String URL_register = URL_SERVER + "/register";
    //登陆页面
    public static final String URL_login = URL_SERVER + "/login";
    //help页面
    public static final String URL_help = URL_SERVER + "/help";
    //addresslist页面
    public static final String URL_addresslist = URL_SERVER + "/addresslist";
    //帮助内容获取
    public static final String URL_helpDetail = URL_SERVER + "/helpDetail";
    //收藏内容获取
    public static final String URL_product_favorites = URL_SERVER + "/product/favorites";
    //收藏中心的请求码
    public static final int REQUEST_CODE_product_favorites = 308;

    //注册中心的验证码
    public static final int REQUEST_CODE_register = 300;
    //注册中心的验证码
    public static final int REQUEST_CODE_login = 301;
    //版本请求码
    public static final int REQUEST_CODE_version = 302;
    //版本help列表
    public static final int REQUEST_CODE_help = 303;
    //帮助内容请求码
    public static final int REQUEST_CODE_helpDetail = 304;
    //商品分类
    public static final String URL_CATEGORY = URL_SERVER + "/category";
    public static final int REQUEST_CODE_CATEGORY = 600;//商品分类
    //商品列表
    public static final String URL_PRODUCLIST = URL_SERVER + "/productlist";
    public static final int REQUEST_CODE_PRODUCLIST = 601;//商品分类


    //结算中心
    public static final String PAYMENT_URL_SERVER = URL_SERVER;
    public static final String URL_checkout = PAYMENT_URL_SERVER + "/checkout";
    // 结算中心的验证码
    public static final int REQUEST_CODE_checkout = 500;

    //少年锦时主机
    public static final String URL_SEARCH_SERVER = URL_SERVER;
    //搜索界面
    public static final String URL_SEARCH_SEEK = URL_SEARCH_SERVER + "/search/recommend";
    //搜索列表
    public static final String URL_SEARCH_LIST = URL_SEARCH_SERVER + "/search";
    //搜索界面
    public static final int SEARCH_CODE_SHOW = 400;
    //搜索界面2
    public static final int SEARCH_CODE_SHOW_TOW = 401;
    //账户中心
    public static final String URL_ACCOUNT = URL_SERVER + "/userinfo";
    //账户中心请求码
    public static final int CODE_ACCOUNT = 302;
}
