package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class UserShareBean extends BaseResponse{
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class UserShare{

        /**
         * downloadUrl : http://baidu.com
         * timeMillis : 1546849921554
         * qrcodeUrl : http://10.10.10.238:8089/user/qrcode/downloadQrcode.JPG
         */

        @SerializedName("downloadUrl")
        private String downloadUrl;
        @SerializedName("timeMillis")
        private long timeMillis;
        @SerializedName("qrcodeUrl")
        private String qrcodeUrl;

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public long getTimeMillis() {
            return timeMillis;
        }

        public void setTimeMillis(long timeMillis) {
            this.timeMillis = timeMillis;
        }

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
        }
    }
}
