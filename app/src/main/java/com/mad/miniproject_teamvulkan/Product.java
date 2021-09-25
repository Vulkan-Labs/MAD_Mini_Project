package com.mad.miniproject_teamvulkan;

import com.google.firebase.database.Exclude;

public class Product {


    @Exclude
    private String PROID;
    private String productName;
    private float price;
    private int quantity;

    public Product() {
        this.PROID = PROID;
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

    public String getPROID() {
        return PROID;
    }

    public void setPROID(String PROID) {
        this.PROID = PROID;
    }


}
