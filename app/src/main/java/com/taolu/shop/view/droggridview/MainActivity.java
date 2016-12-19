package com.taolu.shop.view.droggridview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.taolu.shop.R;
import com.taolu.shop.view.droggridview.depend.DragCallback;
import com.taolu.shop.view.droggridview.depend.DragGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{


    private DragGridView dragGridView;
    private List<Integer> datas=new ArrayList<>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dragGridView= (DragGridView) findViewById(R.id.gridview);
        init();
    }

    private void init()
    {
        for(int i=0;i<46;i++)
        {
            datas.add(i);
        }
        adapter=new MyAdapter(this);
        adapter.setDatas(datas);
        dragGridView.setAdapter(adapter);


        dragGridView.setDragCallback(new DragCallback() {
            @Override
            public void startDrag(int position) {
                LogUtil.i("start drag at " + position);
            }

            @Override
            public void endDrag(int position) {
                LogUtil.i("end drag at " + position);
            }
        });
        dragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                dragGridView.clicked(position);
            }
        });
        dragGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                dragGridView.startDrag(position);
                return false;
            }
        });
    }
}
