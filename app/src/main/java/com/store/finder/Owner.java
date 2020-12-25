package com.store.finder;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Owner {

    public ArrayList<String> garrList= new ArrayList<>();
    public ArrayAdapter<String> garrAdp;

    private String Phone_Number;
    private String Owner_Name;
    private String Address;
    private String District;
    private String Password;
    private String Shop_Name;
    private String Shop_Type;
    private String State;
    private String Mobile_Number;
    private String AccStatus;
    private String Rating;

    public Owner() {

    }

    public Owner(String phone_Number, String owner_Name, String address, String district, String password, String shop_Name, String shop_Type, String state, String mobile_Number, String accStatus, String rating) {
        Phone_Number = phone_Number;
        Owner_Name = owner_Name;
        Address = address;
        District = district;
        Password = password;
        Shop_Name = shop_Name;
        Shop_Type = shop_Type;
        State = state;
        Mobile_Number = mobile_Number;
        AccStatus = accStatus;
        Rating = rating;
    }

    public Owner(String phone_Number, String owner_Name, String address, String district, String password, String shop_Name, String shop_Type, String state, String mobile_Number) {
        Phone_Number = phone_Number;
        Owner_Name = owner_Name;
        Address = address;
        District = district;
        Password = password;
        Shop_Name = shop_Name;
        Shop_Type = shop_Type;
        State = state;
        Mobile_Number = mobile_Number;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getOwner_Name() {
        return Owner_Name;
    }

    public void setOwner_Name(String owner_Name) {
        Owner_Name = owner_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    public String getShop_Type() {
        return Shop_Type;
    }

    public void setShop_Type(String shop_Type) {
        Shop_Type = shop_Type;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getMobile_Number(String s) {
        return Mobile_Number;
    }

    public String setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
        return mobile_Number;
    }

    public String getAccStatus() {
        return AccStatus;
    }

    public void setAccStatus(String accStatus) {
        AccStatus = accStatus;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}