package com.agtools.agtools;

/**
 * Created by ranjithkumar.g on 3/22/2018.
 */

public class ModelPackage {
    private String packagename;

    public ModelPackage(String name) {
        this.packagename=name;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }
}
