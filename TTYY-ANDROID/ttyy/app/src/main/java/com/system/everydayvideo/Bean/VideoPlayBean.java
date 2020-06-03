package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class VideoPlayBean extends BaseResponse {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class VideoPlay extends BaseModel {


        @SerializedName("videoPlayUrl")
        private String videoPlayUrl;

        public String getVideoPlayUrl() {
            return videoPlayUrl;
        }

        public void setVideoPlayUrl(String videoPlayUrl) {
            this.videoPlayUrl = videoPlayUrl;
        }
    }
}
