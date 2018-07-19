package com.agtools.agtools;

/**
 * Created by ranjithkumar.g on 3/22/2018.
 */

public class ModelList {
    private String price;
    private String commodityCount;

    public ModelList(String price, String commodityCount) {
        this.price = price;
        this.commodityCount = commodityCount;
    }

    public ModelList() {

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(String commodityCount) {
        this.commodityCount = commodityCount;
    }
}
