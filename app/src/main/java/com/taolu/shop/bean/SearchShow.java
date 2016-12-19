package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by admin on 2016/11/26.
 */
public class SearchShow implements IResponse {

    /**
     * productList : [{"id":14,"marketPrice":300,"name":"萌妹外套","pic":"/images/product/detail/q12.jpg","price":160},{"id":15,"marketPrice":300,"name":"韩版粉嫩外套","pic":"/images/product/detail/q13.jpg","price":160},{"id":16,"marketPrice":400,"name":"韩版秋装","pic":"/images/product/detail/q14.jpg","price":300},{"id":17,"marketPrice":300,"name":"春装新款","pic":"/images/product/detail/q15.jpg","price":200},{"id":18,"marketPrice":200,"name":"短裙","pic":"/images/product/detail/q16.jpg","price":160},{"id":19,"marketPrice":200,"name":"新款秋装","pic":"/images/product/detail/q17.jpg","price":160},{"id":20,"marketPrice":200,"name":"妈妈新款","pic":"/images/product/detail/q18.jpg","price":160},{"id":21,"marketPrice":300,"name":"春秋新款外套","pic":"/images/product/detail/q19.jpg","price":200},{"id":22,"marketPrice":200,"name":"新款毛衣","pic":"/images/product/detail/q20.jpg","price":160},{"id":23,"marketPrice":150,"name":"新款打底上衣","pic":"/images/product/detail/q21.jpg","price":160},{"id":24,"marketPrice":200,"name":"春秋新款外套","pic":"/images/product/detail/q22.jpg","price":160},{"id":25,"marketPrice":150,"name":"新款秋装","pic":"/images/product/detail/q23.jpg","price":160},{"id":26,"marketPrice":200,"name":"粉色系暖心套装","pic":"/images/product/detail/q24.jpg","price":200},{"id":27,"marketPrice":150,"name":"韩版粉嫩外套","pic":"/images/product/detail/q25.jpg","price":160},{"id":28,"marketPrice":300,"name":"春装新款","pic":"/images/product/detail/q26.jpg","price":200},{"id":29,"marketPrice":180,"name":"日本奶粉","pic":"/images/product/detail/q26.jpg","price":160}]
     * response : search
     */

    private String response;
    /**
     * id : 14
     * marketPrice : 300
     * name : 萌妹外套
     * pic : /images/product/detail/q12.jpg
     * price : 160
     */

    private List<ProductListBean> productList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        private int id;
        private int marketPrice;
        private String name;
        private String pic;
        private int price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
