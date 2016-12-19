package com.taolu.shop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.adapter.MyFavoritesAdapter;
import com.taolu.shop.bean.CollectorBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.MySqliteHelper;
import com.taolu.shop.utils.RedBabyDao;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/28.
 */
public class FavoritesHolder extends BaseHolder {

    private TextView mName;
    private TextView mMoney;
    private ImageView mImage;
    private ImageView mDelete;
    private ArrayList<CollectorBean> list = new ArrayList<>();
    private int poision;
    private MyFavoritesAdapter a ;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.favorites_item, null);
        mName = (TextView) view.findViewById(R.id.favor_name);
        mMoney = (TextView) view.findViewById(R.id.favor_money);
        mImage = (ImageView) view.findViewById(R.id.favor_image);
        mDelete = (ImageView) view.findViewById(R.id.iv_delete);
        return view;
    }
    public void setCollectorBean(ArrayList<CollectorBean> list, int poision,MyFavoritesAdapter a) {
        this.list = list;
        this.poision = poision;
        this.a = a;
    }
    @Override
    public void bindData(Object data) {
        CollectorBean c = (CollectorBean) data;
        mMoney.setText("¥" + c.getPrice());
        mName.setText(c.getName());
        HttpLoader.getInstance(RedBabyApplication.context).display(mImage, HttpApi.URL_Service + c.getUrl(),R.drawable.image_loader_failure, R.drawable.image_loader_failure, 0, 0, ImageView.ScaleType.CENTER_CROP);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqliteHelper my = new MySqliteHelper(RedBabyApplication.context);
                RedBabyDao dao = new RedBabyDao(RedBabyApplication.context);
                dao.delete(list.get(poision).getNumid());
                list.remove(poision);
                a.notifyDataSetChanged();
            }
        });
    }
}
