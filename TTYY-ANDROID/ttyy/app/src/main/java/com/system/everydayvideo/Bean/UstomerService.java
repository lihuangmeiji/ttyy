package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class UstomerService extends BaseResponse{

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class UstomerServiceModel{

        /**
         * archive : 0
         * createAt : 1547103972000
         * id : 1
         * memo :
         * name : 测试客服
         * pageNum : 1
         * pageSize : 20
         * platformName : QQ
         * platformNumber : 110
         * timeMillis : 1547211578311
         * updateAt : 1547119915000
         */

        @SerializedName("archive")
        private int archive;
        @SerializedName("createAt")
        private long createAt;
        @SerializedName("id")
        private int id;
        @SerializedName("memo")
        private String memo;
        @SerializedName("name")
        private String name;
        @SerializedName("pageNum")
        private int pageNum;
        @SerializedName("pageSize")
        private int pageSize;
        @SerializedName("platformName")
        private String platformName;
        @SerializedName("platformNumber")
        private String platformNumber;
        @SerializedName("timeMillis")
        private long timeMillis;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getPlatformName() {
            return platformName;
        }

        public void setPlatformName(String platformName) {
            this.platformName = platformName;
        }

        public String getPlatformNumber() {
            return platformNumber;
        }

        public void setPlatformNumber(String platformNumber) {
            this.platformNumber = platformNumber;
        }

        public long getTimeMillis() {
            return timeMillis;
        }

        public void setTimeMillis(long timeMillis) {
            this.timeMillis = timeMillis;
        }

        public long getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(long updateAt) {
            this.updateAt = updateAt;
        }
    }
}
