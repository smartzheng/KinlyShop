package com.taolu.shop.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.taolu.shop.bean.CollectorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2016/11/27.
 */
public class RedBabyDao {
    private MySqliteHelper helper;

    public RedBabyDao(Context context) {
        helper = new MySqliteHelper(context);
    }
    //name varchar(20), url varchar(100),price

    /**
     *
     * @param name 名称
     * @param url  商品的URL
     * @param price 价格
     * @return -1表示失败
     */
    public long add(String numid,String name,String url,String price){
        SQLiteDatabase db = helper.getWritableDatabase();
        //,numid  varchar(20) ,name varchar(20), url varchar(100),price varchar(20
        ContentValues values =new ContentValues();
        values.put("numid", numid);
        values.put("name", name);
        values.put("url", url);
        values.put("price",price);
        long result = db.insert("merchandise", null, values); //组拼sql语句实现的.带返回值
        db.close();//释放资源
        return result;
    }

    /**
     * 删除商品
     * @param id ID
     * @return result 删除了几行 0 代表删除失败
     */
    public int delete(String id){
        SQLiteDatabase  db = helper.getWritableDatabase();
        //db.execSQL("delete from student where name=?",new Object[]{name});
        int result = db.delete("merchandise", "numid=?", new String[]{id});
        db.close();//释放资源
        return result;
    }
    /**
     * 获取全部的学生信息
     * @return
     */
    public List<CollectorBean> findAll(){
        List<CollectorBean> students =new ArrayList<CollectorBean>();
        SQLiteDatabase  db = helper.getReadableDatabase();
        //,numid  varchar(20) ,name varchar(20), url varchar(100),price varchar(20
        Cursor cursor =  db.query("merchandise", new String[]{"numid","name","url","price"}, null, null, null, null, null);
        //补充错误写法
        //num myId ,name varchar(20), url varchar(100),price varchar(20
        while(cursor.moveToNext()){
            String numid = cursor.getString(0);
            String name = cursor.getString(1);
            String url = cursor.getString(2);
            String price = cursor.getString(3);
            CollectorBean student = new CollectorBean();
            student.setName(name);
           student.setNumid(numid);
            student.setUrl(url);
            student.setPrice(price);
            students.add(student);
        }
        cursor.close();
        db.close();
        return students;
    }
/*    public  String find(String numid){
        String  id=null;

    }*/


}
