package com.thunderboarsolution.quicksell.Model;

public class Delivery {
    private String bookingid;
    private String phn;
    private String address;
    private String cartno;
    private String itemid;
    private String adminid;
    private String userid;

    public Delivery(String bookingid, String phn, String address, String cartno, String itemid, String adminid, String userid) {
        this.bookingid = bookingid;
        this.phn = phn;
        this.address = address;
        this.cartno = cartno;
        this.itemid = itemid;
        this.adminid = adminid;
        this.userid = userid;
    }

    public Delivery() {
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCartno() {
        return cartno;
    }

    public void setCartno(String cartno) {
        this.cartno = cartno;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
