package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by yy on 2016/11/25.
 */
public class BabyBean implements IResponse {

    /**
     * comment : [{"content":"裙子不错裙子不错裙子不错裙子不错裙子不错裙子不错裙子","time":2014,"title":"裙子","username":"赵刚"}]
     * response : productComment
     */

    private String response;
    private List<CommentBean> comment;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * content : 裙子不错裙子不错裙子不错裙子不错裙子不错裙子不错裙子
         * time : 2014
         * title : 裙子
         * username : 赵刚
         */

        private String content;
        private int time;
        private String title;
        private String username;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
