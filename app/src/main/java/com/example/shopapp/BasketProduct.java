package com.example.shopapp;

import java.util.ArrayList;

public class BasketProduct {
    private int id;
    private int basketId;
    private int productId;
    private int count;
    private int boughtCount;
    private Basket bpBasket;
    private Product bpProduct;

    public Basket getBpBasket() {
        return bpBasket;
    }

    public void setBpBasket(Basket bpBasket) {
        this.bpBasket = bpBasket;
    }

    public Product getBpProduct() {
        return bpProduct;
    }

    public void setBpProduct(Product bpProduct) {
        this.bpProduct = bpProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBoughtCount() {
        return boughtCount;
    }

    public void setBoughtCount(int boughtCount) {
        this.boughtCount = boughtCount;
    }
}
