package com.system.everydayvideo.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginBean extends BaseResponse implements Serializable{


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;

    public static class Login extends BaseModel implements Serializable{

        /**
         * pageNum : 1
         * pageSize : 10
         * id : 7
         * archive : 0
         * createAt : null
         * updateAt : null
         * mobile : 15835568257
         * userNo : null
         * username : null
         * password : null
         * nickname : tt_292604
         * realname : null
         * sex : null
         * age : null
         * birthday : null
         * signature : null
         * email : null
         * avatar : https://s1.ax1x.com/2018/12/17/F09x54.png
         * longitude : null
         * latitude : null
         * coord : null
         * country : null
         * province : null
         * city : null
         * district : null
         * loginIp : null
         * loginTime : null
         * deviceType : null
         * deviceId : null
         * disable : 0
         * wxNumber : null
         * tencentNumber : null
         * other :
         * vipLevel : null
         * expireAt : null
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
        private Object createAt;
        @SerializedName("updateAt")
        private Object updateAt;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("userNo")
        private Object userNo;
        @SerializedName("username")
        private Object username;
        @SerializedName("password")
        private Object password;
        @SerializedName("nickname")
        private String nickname;
        @SerializedName("realname")
        private Object realname;
        @SerializedName("sex")
        private Object sex;
        @SerializedName("age")
        private Object age;
        @SerializedName("birthday")
        private Object birthday;
        @SerializedName("signature")
        private Object signature;
        @SerializedName("email")
        private Object email;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("longitude")
        private Object longitude;
        @SerializedName("latitude")
        private Object latitude;
        @SerializedName("coord")
        private Object coord;
        @SerializedName("country")
        private Object country;
        @SerializedName("province")
        private Object province;
        @SerializedName("city")
        private Object city;
        @SerializedName("district")
        private Object district;
        @SerializedName("loginIp")
        private Object loginIp;
        @SerializedName("loginTime")
        private Object loginTime;
        @SerializedName("deviceType")
        private Object deviceType;
        @SerializedName("deviceId")
        private Object deviceId;
        @SerializedName("disable")
        private int disable;
        @SerializedName("wxNumber")
        private Object wxNumber;
        @SerializedName("tencentNumber")
        private Object tencentNumber;
        @SerializedName("other")
        private String other;
        @SerializedName("vipLevel")
        private Object vipLevel;
        @SerializedName("expireAt")
        private String expireAt;
        @SerializedName("isGiveVip")
        private int isGiveVip;

        private String fillInviteCode;

        public String getFillInviteCode() {
            return fillInviteCode;
        }

        public void setFillInviteCode(String fillInviteCode) {
            this.fillInviteCode = fillInviteCode;
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

        public Object getCreateAt() {
            return createAt;
        }

        public void setCreateAt(Object createAt) {
            this.createAt = createAt;
        }

        public Object getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(Object updateAt) {
            this.updateAt = updateAt;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getUserNo() {
            return userNo;
        }

        public void setUserNo(Object userNo) {
            this.userNo = userNo;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getRealname() {
            return realname;
        }

        public void setRealname(Object realname) {
            this.realname = realname;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getCoord() {
            return coord;
        }

        public void setCoord(Object coord) {
            this.coord = coord;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getDistrict() {
            return district;
        }

        public void setDistrict(Object district) {
            this.district = district;
        }

        public Object getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(Object loginIp) {
            this.loginIp = loginIp;
        }

        public Object getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(Object loginTime) {
            this.loginTime = loginTime;
        }

        public Object getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Object deviceType) {
            this.deviceType = deviceType;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        public int getDisable() {
            return disable;
        }

        public void setDisable(int disable) {
            this.disable = disable;
        }

        public Object getWxNumber() {
            return wxNumber;
        }

        public void setWxNumber(Object wxNumber) {
            this.wxNumber = wxNumber;
        }

        public Object getTencentNumber() {
            return tencentNumber;
        }

        public void setTencentNumber(Object tencentNumber) {
            this.tencentNumber = tencentNumber;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public Object getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(Object vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getExpireAt() {
            return expireAt;
        }

        public void setExpireAt(String expireAt) {
            this.expireAt = expireAt;
        }

        public int getIsGiveVip() {
            return isGiveVip;
        }

        public void setIsGiveVip(int isGiveVip) {
            this.isGiveVip = isGiveVip;
        }
    }
}
