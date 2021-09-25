package com.mad.miniproject_teamvulkan;

import com.google.firebase.database.Exclude;

public class Product {


    @Exclude
    private String PROID;
    private String productName;
    private  float bprice;
    private float price;
    private int quantity;
    private String description;

    public Product() {
        this.PROID = PROID;
        this.productName = productName;
        this.bprice = bprice;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getBprice() { return bprice;   }

    public void setBprice(float bprice) {  this.bprice = bprice;  }

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

    public String getDescription() {  return description; }

    public void setDescription(String description) {  this.description = description; }



}
