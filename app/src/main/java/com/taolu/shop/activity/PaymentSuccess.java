package com.taolu.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.utils.LogUtil;

public class PaymentSuccess extends AppCompatActivity implements View.OnClickListener{


    private TextView success_foces_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
         inintView();
    }

    private void inintView() {
        TextView sucess_momey = (TextView) findViewById(R.id.sucess_momey);
        TextView sucess_style = (TextView) findViewById(R.id.sucess_style);
        TextView sucess_productsum = (TextView) findViewById(R.id.sucess_productsum);
        success_foces_info = (TextView) findViewById(R.id.success_foces_info);

        Button sucess_bt_gobuy = (Button) findViewById(R.id.sucess_bt_gobuy);
        sucess_bt_gobuy.setOnClickListener(this);

        Button sucess_bt_obser = (Button) findViewById(R.id.sucess_bt_obser);
        sucess_bt_obser.setOnClickListener(this);

        Intent intent = getIntent();
        String sumproduct = intent.getStringExtra("sumproduct");
        LogUtil.e("pp",sumproduct+"////");
        String realMoney = intent.getStringExtra("realMoney");
        String style = intent.getStringExtra("style");
        sucess_momey.setText("总金额:\t\t"+realMoney);
        sucess_style.setText("总件数:\t"+"\t"+sumproduct);
        sucess_productsum.setText("送货方式:" +
                "\t"+style);
        String s="您的购物信息:"+"总金额:"+realMoney+"\t"+"总件数:"+sumproduct+"\t"+"送货方式:" +style;


        success_foces_info.setText(s);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sucess_bt_obser:


                Intent intent=new Intent(this,Commodityorder.class);
                startActivity(intent);
                finish();

            break;
            case R.id.sucess_bt_gobuy:

                finish();


                break;

        }
    }
}
