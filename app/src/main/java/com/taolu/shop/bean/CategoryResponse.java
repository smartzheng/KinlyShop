package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RainLi on 2016/11/23.
 */
public class CategoryResponse implements IResponse {

    public List<Category> category;

    public static class Category implements Serializable {
        public int id;
        public boolean isLeafNode;
        public String pic;
        public String name;
        public int parentId;
        public String tag;
        public boolean isChecked;
    }
}
