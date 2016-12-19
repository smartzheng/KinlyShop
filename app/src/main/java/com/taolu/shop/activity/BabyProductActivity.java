package com.taolu.shop.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.bean.BabyBean;
import com.taolu.shop.bean.BabyProductBean;
import com.taolu.shop.bean.CollectorBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.HttpCode;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.CommonUtil;
import com.taolu.shop.utils.MySqliteHelper;
import com.taolu.shop.utils.RedBabyDao;
import com.taolu.shop.view.ProductBottomDialog;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BabyProductActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mPager;
    private TextView mMarketprice;
    private TextView mLefttime;
    private TextView mName;
    private TextView mBaoyou;
    private TextView mCommit;
    private TextView mTitle;
    private TextView mUsername;
    private TextView mCommit_time;
    private TextView mCar;
    private ImageView mPig;
    private int mId;
    private TextView mViewById;
    int i = 0;
    private BabyProductBean.ProductBean mProduct;
    private TextView mHuiyuan;
    private TextView mNumbre;

    private ImageButton go_cart;
    private View mView;
    private TextView mCollect;
    private List<BabyProductBean.ProductBean.ProductPropertyBean> mProductProperty;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = View.inflate(RedBabyApplication.context,R.layout.activity_baby_product,null);
        setContentView(mView);
        initView();
        Dara();
        initData();

    }

    /**
     * 详情
     */
    int temp = 1;

    private void initData() {
        //接收数据
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        HttpParams pa = new HttpParams();
        Random r = new Random();
        temp = r.nextInt(3);

        pa.put("pId", mId+"");
        HttpLoader.getInstance(this).get(HttpApi.URL_PRODCT, pa, BabyProductBean.class, HttpCode.CODE_BabyProdct, new BasyListener(), true);

    }

    /**
     * 评价
     */
    public void Dara() {
        HttpParams pp = new HttpParams();
        pp.put("pId", new Random().nextInt(4) + 1 + "").put("page", "1").put("pageNum", new Random().nextInt(4) + 1 + "");
        HttpLoader.getInstance(this).get(HttpApi.URL_Commit, pp, BabyBean.class, HttpCode.CODE_comment, new ComnitListener(), true);
    }

    private ImageButton mBack;
    private void initView() {
        mPager = (LinearLayout) findViewById(R.id.pager_baby);
        mMarketprice = (TextView) findViewById(R.id.marketprice);
        mLefttime = (TextView) findViewById(R.id.lefttime);
        mName = (TextView) findViewById(R.id.baby_name);
        mBaoyou = (TextView) findViewById(R.id.baoyou);
        mTitle = (TextView) findViewById(R.id.title);
        mCommit = (TextView) findViewById(R.id.commit);
        mUsername = (TextView) findViewById(R.id.username);
        mCommit_time = (TextView) findViewById(R.id.commit_time);
        mCar = (TextView) findViewById(R.id.productcart);
        mViewById = (TextView) findViewById(R.id.shopping);
        mHuiyuan = (TextView) findViewById(R.id.huiyuan);
        mCollect = (TextView) findViewById(R.id.collect);//点击收藏
        go_cart = (ImageButton) findViewById(R.id.go_cart);
        mBack = (ImageButton) findViewById(R.id.product_title_hot);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = BabyProductActivity.this.getIntent();
           String acyivityName =  intent.getStringExtra("AcitivityName");
                if(acyivityName!=null){
                    if(acyivityName.equals("Panic")){
                        Intent intent1 = new Intent(BabyProductActivity.this,PanicActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                    }else if(acyivityName.equals("SalesActivity")){
                        Intent intent1 = new Intent(BabyProductActivity.this,SalesActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                    }else if(acyivityName.equals("XinpinAcyivity")){
                        Intent intent1 = new Intent(BabyProductActivity.this,XinpinActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                    }else if(acyivityName.equals("HotActivity")){
                        Intent intent1 = new Intent(BabyProductActivity.this,HotActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                    }else if(acyivityName.equals("RecommendActivity")){
                        Intent intent1 = new Intent(BabyProductActivity.this,RecommendActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                    }else if(acyivityName.equals("ProductListActivity")){
                        Intent intent1 = new Intent(BabyProductActivity.this,ProductListActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                    }else if(acyivityName.equals("SearchShowActivity")){
                        Intent intent1 = new Intent(BabyProductActivity.this,SearchShowActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                        //ProductCollectActivity
                    }else if(acyivityName.equals("ProductCollectActivity")){
                        Intent intent1 = new Intent(BabyProductActivity.this,ProductCollectActivity.class);
                        startActivity(intent1);
                        finish();
                        BabyProductActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                    }
                }else{
                    finish();
                }



            }
        });
        mViewById.setOnClickListener(this);
        mCar.setOnClickListener(this);
        mCollect.setOnClickListener(this);
        go_cart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.productcart:
                //传递数据到购物车
                toCart();
                break;
            case R.id.shopping:
                shopping();
                break;
            case R.id.collect:          //点击就添加到数据库
                System.out.println("mUrl----------------------->"+mUrl);
                System.out.println("已经可以添加+++++");
                MySqliteHelper helper = new MySqliteHelper(this);
                helper.getWritableDatabase();
                RedBabyDao dao = new RedBabyDao(this);
                List<CollectorBean> all = dao.findAll();
                for (int i = 0; i < all.size(); i++) {
                    CollectorBean collectorBean = all.get(i);
                    String numid = collectorBean.getNumid();
                    if(numid.contains(mProduct.getId()+"")){
                        Toast.makeText(BabyProductActivity.this,"已经收藏，快去选购吧",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //,numid  varchar(20) ,name varchar(20), url varchar(100),price varchar(20
                dao.add(mProduct.getId()+"",mProduct.getName(),mUrl, mProduct.getPrice()+"");
                break;

            case R.id.go_cart:
                if(RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(3);
                }
                finish();
                break;
        }
    }

    /**
     * 立即购买
     */
    private void shopping() {
        ProductBottomDialog dialog = new ProductBottomDialog(this,(View) mView.getParent());
        dialog.setData(this,mProduct,true);
        dialog.show();

    }
    /**
     * 传递数据到购物车
     */
    private void toCart() {
        ProductBottomDialog dialog = new ProductBottomDialog(this,(View) mView.getParent());
        dialog.setData(this,mProduct,false);
        dialog.show();
    }

    class BasyListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, final IResponse response) {

            RedBabyApplication.mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    babyProduct((BabyProductBean) response);
                }
            });

        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }

    private void babyProduct(BabyProductBean response) {
        int width = CommonUtil.getDimens(R.dimen.ww);
        int height = CommonUtil.getDimens(R.dimen.hh);
        int margin = CommonUtil.getDimens(R.dimen.ff);
        BabyProductBean bean = response;
        mProduct = bean.getProduct();
        //市场价
        mMarketprice.setText("￥" + bean.getProduct().getMarketPrice() + "");
        //会员价格
        mHuiyuan.setText("￥" + bean.getProduct().getPrice());
        mLefttime.setText("月销量" + mProduct.getLeftTime() + "件");
        mName.setText(mProduct.getName());
        mBaoyou.setText(mProduct.getInventoryArea() + "包邮");
        ArrayList<BabyProductBean.ProductBean.ProductPropertyBean> colorList = new ArrayList<>();
        ArrayList<BabyProductBean.ProductBean.ProductPropertyBean> sizeList = new ArrayList<>();
        mProductProperty = mProduct.getProductProperty();
        for (int i = 0; i < mProductProperty.size(); i++) {
            BabyProductBean.ProductBean.ProductPropertyBean productPropertyBean =  mProductProperty.get(i);
            String k = productPropertyBean.getK();
            if (k.equals("颜色")) {
                colorList.add(productPropertyBean);
            } else if (k.equals("尺寸")) {
                sizeList.add(productPropertyBean);
            }
        }

        LinearLayout.LayoutParams pa = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        LinearLayout la = new LinearLayout(this);
        la.setOrientation(LinearLayout.VERTICAL);
        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(pa);
        //TextView tv2 = new TextView(this);
        la.addView(tv1);


        final List<String> pics = bean.getProduct().getPics();


        for (int i = 0; i < pics.size(); i++) {
            final int temp = i;
            ImageView iv = new ImageView(RedBabyApplication.context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

            params.leftMargin = margin;
            params.rightMargin = margin;
            iv.setLayoutParams(params);
            mUrl = pics.get(i);
           // System.out.println("mUrl----------------------->"+mUrl);
            HttpLoader.getInstance(RedBabyApplication.context).display(iv, HttpApi.URL_Service + pics.get(i));
            //轮播图
            mPager.addView(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BabyProductActivity.this, ImageLoadActivity.class);
                    intent.putStringArrayListExtra("image", (ArrayList<String>) pics);
                    intent.putExtra("productUserName",productUserName);
                    intent.putExtra("commit",comment);
                    intent.putExtra("imagePosition",temp);
                    startActivity(intent);
                }
            });

        }

    }
    private String comment;
    private String productUserName;
    class ComnitListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            BabyBean be = (BabyBean) response;
          //  System.out.println(be.getComment().size() + "211111111111111111111111111");
            BabyBean.CommentBean commentBean = be.getComment().get(0);
            comment = commentBean.getContent();
            mCommit.setText(comment);
            // mTitle.setText(commentBean.getTitle());
            productUserName = commentBean.getUsername();
            mUsername.setText(productUserName);
            mCommit_time.setText(commentBean.getTime() + ".11.11");
        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }

}
