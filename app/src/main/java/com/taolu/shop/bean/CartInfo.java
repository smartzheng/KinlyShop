package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by m1858 on 2016/11/23.
 */
public class CartInfo implements IResponse {


    /**
     * cart : [{"prodNum":3,"product":{"buyLimit":10,"id":1,"name":"韩版时尚蕾丝裙","number":"100","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]}},{"prodNum":2,"product":{"buyLimit":10,"id":2,"name":"粉色毛衣","number":"13","pic":"/images/product/detail/q1.jpg","price":100,"productProperty":[{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"}]}}]
     * prom : ["促销信息一","促销信息二"]
     * response : cart
     * totalCount : 5
     * totalPoint : 100
     * totalPrice : 1250
     */

    private String response;
    private int totalCount;
    private int totalPoint;
    private int totalPrice;
    private List<CartBean> cart;
    private List<String> prom;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartBean> getCart() {
        return cart;
    }

    public void setCart(List<CartBean> cart) {
        this.cart = cart;
    }

    public List<String> getProm() {
        return prom;
    }

    public void setProm(List<String> prom) {
        this.prom = prom;
    }

    public class CartBean {
        /**
         * prodNum : 3
         * product : {"buyLimit":10,"id":1,"name":"韩版时尚蕾丝裙","number":"100","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]}
         */

        private int prodNum;
        private ProductBean product;

        public int getProdNum() {
            return prodNum;
        }

        public void setProdNum(int prodNum) {
            this.prodNum = prodNum;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public class ProductBean {
            /**
             * buyLimit : 10
             * id : 1
             * name : 韩版时尚蕾丝裙
             * number : 100
             * pic : /images/product/detail/c3.jpg
             * price : 350
             * productProperty : [{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]
             */

            private int buyLimit;
            private int id;
            private String name;
            private String number;
            private String pic;
            private int price;
            private List<ProductPropertyBean> productProperty;
            private boolean isLoader;
            private boolean isChecked;

            public boolean isLoader() {
                return isLoader;
            }

            public void setLoader(boolean loader) {
                isLoader = loader;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            @Override
            public String toString() {
                return "ProductBean{" +
                        "buyLimit=" + buyLimit +
                        ", id=" + id +
                        ", name='" + name + '\'' +
                        ", number='" + number + '\'' +
                        ", pic='" + pic + '\'' +
                        ", price=" + price +
                        ", productProperty=" + productProperty +
                        '}';
            }

            public int getBuyLimit() {
                return buyLimit;
            }

            public void setBuyLimit(int buyLimit) {
                this.buyLimit = buyLimit;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
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

            public List<ProductPropertyBean> getProductProperty() {
                return productProperty;
            }

            public void setProductProperty(List<ProductPropertyBean> productProperty) {
                this.productProperty = productProperty;
            }

            public class ProductPropertyBean {
                /**
                 * id : 1
                 * k : 颜色
                 * v : 红色
                 */

                private int id;
                private String k;
                private String v;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getK() {
                    return k;
                }

                public void setK(String k) {
                    this.k = k;
                }

                public String getV() {
                    return v;
                }

                public void setV(String v) {
                    this.v = v;
                }

                @Override
                public String toString() {
                    return "ProductPropertyBean{" +
                            "id=" + id +
                            ", k='" + k + '\'' +
                            ", v='" + v + '\'' +
                            '}';
                }
            }
        }
    }
}
