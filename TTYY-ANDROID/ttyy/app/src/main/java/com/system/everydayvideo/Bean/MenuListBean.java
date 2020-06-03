package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuListBean extends BaseResponse implements Serializable {





    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;


    public static class Item  extends BaseModel implements Serializable{

        /**
         * archive : 0
         * bannerApi : zhibo/banners
         * createAt : 1544683814000
         * icon : https://s1.ax1x.com/2018/12/21/FsQqcn.png
         * id : 1
         * listApi : zhibo/plats
         * pageNum : 1
         * pageSize : 10
         * selectedIcon : https://s1.ax1x.com/2018/12/21/FslRC4.png
         * title : 电视直播
         * updateAt : 1545381830000
         */

        @SerializedName("archive")
        private int archive;
        @SerializedName("bannerApi")
        private String bannerApi;
        @SerializedName("createAt")
        private long createAt;
        @SerializedName("icon")
        private String icon;
        @SerializedName("id")
        private int id;
        @SerializedName("listApi")
        private String listApi;
        @SerializedName("pageNum")
        private int pageNum;
        @SerializedName("pageSize")
        private int pageSize;
        @SerializedName("selectedIcon")
        private String selectedIcon;
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

        public String getBannerApi() {
            return bannerApi;
        }

        public void setBannerApi(String bannerApi) {
            this.bannerApi = bannerApi;
        }

        public long getCreateAt() {
            return createAt;
        }

        public void setCreateAt(long createAt) {
            this.createAt = createAt;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getListApi() {
            return listApi;
        }

        public void setListApi(String listApi) {
            this.listApi = listApi;
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

        public String getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(String selectedIcon) {
            this.selectedIcon = selectedIcon;
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
