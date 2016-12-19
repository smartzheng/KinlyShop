package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by thinkpad on 2016/11/26.
 */
public class VersionInfo implements IResponse {

    /**
     * response : version
     * version : {"version":"1.1","url":"http://"}
     */

    private String response;
    /**
     * version : 1.1
     * url : http://
     */

    private VersionBean version;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public static class VersionBean {
        private String version;
        private String url;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
