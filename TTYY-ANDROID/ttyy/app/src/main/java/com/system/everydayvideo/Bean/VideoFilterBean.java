package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class VideoFilterBean extends BaseResponse {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class VideoFilter  extends BaseModel {

        @SerializedName("advertUrlPattern")
        private String advertUrlPattern;

        @SerializedName("videoDetailPattern")
        private String videoDetailPattern;

        @SerializedName("pageNum")
        private int pageNum;

        @SerializedName("pageSize")
        private int pageSize;

        @SerializedName("videoPlatId")
        private long videoPlatId;

        public String getAdvertUrlPattern() {
            return advertUrlPattern;
        }

        public void setAdvertUrlPattern(String advertUrlPattern) {
            this.advertUrlPattern = advertUrlPattern;
        }

        public String getVideoDetailPattern() {
            return videoDetailPattern;
        }

        public void setVideoDetailPattern(String videoDetailPattern) {
            this.videoDetailPattern = videoDetailPattern;
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

        public long getVideoPlatId() {
            return videoPlatId;
        }

        public void setVideoPlatId(long videoPlatId) {
            this.videoPlatId = videoPlatId;
        }
    }
}
