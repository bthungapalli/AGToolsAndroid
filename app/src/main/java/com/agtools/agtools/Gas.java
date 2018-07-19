package com.agtools.agtools;

/**
 * Created by Ranjith on 3/23/2018.
 */

public class Gas {
    public String value;
    public String valDate;

    public Gas(String value, String valDate) {
        this.value = value;
        this.valDate = valDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValDate() {
        return valDate;
    }

    public void setValDate(String valDate) {
        this.valDate = valDate;
    }
}
