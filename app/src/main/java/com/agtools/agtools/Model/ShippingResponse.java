package com.agtools.agtools.Model;

/**
 * Created by soulsticesoftware on 30-01-2018.
 */

public class ShippingResponse {
    public String lastUpdated_date;
    public String packagetext;
    public String type;

    public String high;
    public String size;

    public ShippingResponse(String lastUpdated_date, String packagetext, String type, String high, String size) {
        this.lastUpdated_date = lastUpdated_date;
        this.packagetext = packagetext;
        this.type = type;
        this.high = high;
        this.size = size;
    }

    public String getLastUpdated_date() {
        return lastUpdated_date;
    }

    public void setLastUpdated_date(String lastUpdated_date) {
        this.lastUpdated_date = lastUpdated_date;
    }

    public String getPackagetext() {
        return packagetext;
    }

    public void setPackagetext(String packagetext) {
        this.packagetext = packagetext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
