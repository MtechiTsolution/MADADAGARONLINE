package com.example.madadagaronline.Models;

public class MyUser {

    String first_name, last_name, phonenumber, userid, catogory, image, status, usernum,cnic, location;

    public MyUser() {
    }

    public MyUser(String first_name, String last_name, String phonenumber, String userid, String catogory, String image, String status, String usernum, String cnic, String location) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phonenumber = phonenumber;
        this.userid = userid;
        this.catogory = catogory;
        this.image = image;
        this.status = status;
        this.usernum = usernum;
        this.cnic = cnic;
        this.location = location;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCatogory() {
        return catogory;
    }

    public void setCatogory(String catogory) {
        this.catogory = catogory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
