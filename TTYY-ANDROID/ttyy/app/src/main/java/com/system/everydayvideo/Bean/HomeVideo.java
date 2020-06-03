package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class HomeVideo  extends BaseModel {


        /**
         * id : 1
         * archive : 0
         * createAt : 2018-12-13 17:01:13
         * updateAt : 2018-12-13 17:01:12
         * title : 爱奇艺
         * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544701746388&di=34ca7c471776f4c1e2d8e1916956f566&imgtype=0&src=http%3A%2F%2Fdownza.img.zz314.com%2Fedu%2Fpc%2Ftxyy-1010%2F2016-06-20%2F7c41d459c0f5f1e6091b59688d48ea85.jpg
         * target : null
         */

        @SerializedName("id")
        private int id;
        @SerializedName("archive")
        private int archive;
        @SerializedName("createAt")
        private String createAt;
        @SerializedName("updateAt")
        private String updateAt;
        @SerializedName("title")
        private String title;
        @SerializedName("img")
        private String img;
        @SerializedName("target")
        private String target;

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

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

}
