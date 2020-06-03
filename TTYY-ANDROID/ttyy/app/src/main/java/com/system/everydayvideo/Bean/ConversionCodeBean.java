package com.system.everydayvideo.Bean;

public class ConversionCodeBean extends BaseResponse {
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;

    public  static  class  ConversionCodeModel{
        private String expireAt;

        public String getExpireAt() {
            return expireAt;
        }

        public void setExpireAt(String expireAt) {
            this.expireAt = expireAt;
        }
    }
}
