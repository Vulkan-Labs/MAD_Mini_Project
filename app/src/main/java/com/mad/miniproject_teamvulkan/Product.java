package com.mad.miniproject_teamvulkan;

import com.google.firebase.database.Exclude;

public class Product {
    @Exclude
    private String key;
    private String productName;
    private float price;
    private int quantity;

    public Product() {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
