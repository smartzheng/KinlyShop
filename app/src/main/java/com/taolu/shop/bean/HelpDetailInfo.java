package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by thinkpad on 2016/11/26.
 */
public class HelpDetailInfo implements IResponse {

    /**
     * helpDetailList : [{"content":"不退","title":"购买的商品如何退货？"},{"content":"测试标题","title":"测试标题"},{"content":"看说明书","title":"如何使用"},{"content":"多少内容","title":"多少内容"}]
     * response : helpDetail
     */

    private String response;
    /**
     * content : 不退
     * title : 购买的商品如何退货？
     */

    private List<HelpDetailListBean> helpDetailList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<HelpDetailListBean> getHelpDetailList() {
        return helpDetailList;
    }

    public void setHelpDetailList(List<HelpDetailListBean> helpDetailList) {
        this.helpDetailList = helpDetailList;
    }

    public static class HelpDetailListBean {
        private String content;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
