package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class HomeBannerBean extends BaseResponse {


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;

    public static class HomeBanner  extends BaseModel {

        /**
         * pageNum : 1
         * pageSize : 10
         * id : 5
         * archive : 0
         * createAt : 2018-12-14 11:30:33
         * updateAt : 2018-12-14 11:30:33
         * target : null
         * type : 0
         * title : 无敌破坏王
         * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543572159322&di=583e210fadd6501a474809b5f6d991a9&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fupload%2F20170710%2F609db0fc0bfa4c7fae6a3c4f58b8d932_th.png
         * desc : null
         */

        @SerializedName("pageNum")
        private int pageNum;
        @SerializedName("pageSize")
        private int pageSize;
        @SerializedName("id")
        private int id;
        @SerializedName("archive")
        private int archive;
        @SerializedName("createAt")
        private String createAt;
        @SerializedName("updateAt")
        private String updateAt;
        @SerializedName("target")
        private Object target;
        @SerializedName("type")
        private int type;
        @SerializedName("title")
        private String title;
        @SerializedName("img")
        private String img;
        @SerializedName("desc")
        private Object desc;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getArchive() {
            return archive;
        }

        public void setArchive(int archive) {
            this.archive = archive;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
            this.desc = desc;
        }
    }
}
