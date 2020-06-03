package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class UserVipBean  extends BaseResponse {


    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public static class IsVip{
        @SerializedName("isVIP")
        private int isVIP;

        @SerializedName("isGiveVip")
        private int isGiveVip;


        public int getIsGiveVip() {
            return isGiveVip;
        }

        public void setIsGiveVip(int isGiveVip) {
            this.isGiveVip = isGiveVip;
        }

        public int getIsVIP() {
            return isVIP;
        }

        public void setIsVIP(int isVIP) {
            this.isVIP = isVIP;
        }
    }

    public static class UserVip{


        /**
         * archive : 0
         * createAt : 1545111505000
         * currentPrice : 0.01
         * desc : 购买续费一个月VIP
         * id : 1
         * month : 1
         * originalPrice : 1.0
         * pageNum : 1
         * pageSize : 10
         * title : 包月购买
         * updateAt : 1545383934000
         */

        @SerializedName("archive")
        private int archive;
        @SerializedName("createAt")
        private long createAt;
        @SerializedName("currentPrice")
        private double currentPrice;
        @SerializedName("desc")
        private String desc;
        @SerializedName("id")
        private int id;
        @SerializedName("month")
        private int month;
        @SerializedName("originalPrice")
        private double originalPrice;
        @SerializedName("pageNum")
        private int pageNum;
        @SerializedName("pageSize")
        private int pageSize;
        @SerializedName("title")
        private String title;
        @SerializedName("updateAt")
        private long updateAt;

        public int getArchive() {
            return archive;
        }

        public void setArchive(int archive) {
            this.archive = archive;
        }

        public long getCreateAt() {
            return createAt;
        }

        public void setCreateAt(long createAt) {
            this.createAt = createAt;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(long updateAt) {
            this.updateAt = updateAt;
        }
    }
}
