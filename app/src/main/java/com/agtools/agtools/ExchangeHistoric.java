package com.agtools.agtools;

/**
 * Created by Ranjith on 3/25/2018.
 */

public class ExchangeHistoric  {
    private String value;private String value2;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ExchangeHistoric(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public ExchangeHistoric(String value, String value2) {
        this.value = value;
        this.value2 = value2;
    }
    private  String value3;

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public ExchangeHistoric(String value, String value2, String value3) {
        this.value = value;
        this.value2 = value2;
        this.value3 = value3;
    }
}
