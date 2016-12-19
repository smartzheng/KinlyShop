package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by thinkpad on 2016/11/26.
 */
public class HelpInfo implements IResponse {

    /**
     * response : help
     * helpList : [{"id":"1","title":"购物指南"},{"id":"2","title":"配送方式"}]
     */

    private String response;
    /**
     * id : 1
     * title : 购物指南
     */

    private List<HelpListBean> helpList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<HelpListBean> getHelpList() {
        return helpList;
    }

    public void setHelpList(List<HelpListBean> helpList) {
        this.helpList = helpList;
    }

    public static class HelpListBean {
        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
