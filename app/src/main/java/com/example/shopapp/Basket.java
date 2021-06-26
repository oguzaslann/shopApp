package com.example.shopapp;

import java.util.Date;

public class Basket {
    private int id;
    private String name;
    private String orderDate;
    private String orderLocName;
    private String orderLoc;
    private String imgPath;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderLocName() {
        return orderLocName;
    }

    public void setOrderLocName(String orderLocName) {
        this.orderLocName = orderLocName;
    }

    public String getOrderLoc() {
        return orderLoc;
    }

    public void setOrderLoc(String orderLoc) {
        this.orderLoc = orderLoc;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return name;
    }
}
