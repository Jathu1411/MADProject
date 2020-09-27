package com.thunderboarsolution.quicksell.Model;

public class UserInfo {
    private String userId;
    private String userName;
    private String userMobile;
    private String userAddress;

    public UserInfo(String userId, String userName, String userMobile, String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userAddress = userAddress;
    }

    public UserInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
