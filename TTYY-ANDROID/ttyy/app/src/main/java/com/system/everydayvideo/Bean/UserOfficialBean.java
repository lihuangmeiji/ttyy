package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

public class UserOfficialBean extends BaseResponse{

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class UserOfficialModel{
        /**
         * officialGroup : QQ群：9999999
         * timeMillis : 1547211056052
         */

        @SerializedName("officialGroup")
        private String officialGroup;
        @SerializedName("timeMillis")
        private long timeMillis;

        public String getOfficialGroup() {
            return officialGroup;
        }

        public void setOfficialGroup(String officialGroup) {
            this.officialGroup = officialGroup;
        }

        public long getTimeMillis() {
            return timeMillis;
        }

        public void setTimeMillis(long timeMillis) {
            this.timeMillis = timeMillis;
        }
    }
}
