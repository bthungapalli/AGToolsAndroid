package com.agtools.agtools.Model;

/**
 * Created by soulsticesoftware on 03-02-2018.
 */

public class TerminalPriceResponse {
    String terLastUpdateDate ;
    String terPackage;
    String terType;
    String terLowHigh;
    String terOrigin ;
    String terSize;

    public TerminalPriceResponse(String terLastUpdateDate, String terPackage, String terType, String terLowHigh, String terOrigin, String terSize) {
        this.terLastUpdateDate = terLastUpdateDate;
        this.terPackage = terPackage;
        this.terType = terType;
        this.terLowHigh = terLowHigh;
        this.terOrigin = terOrigin;
        this.terSize = terSize;
    }

    public String getTerLastUpdateDate() {
        return terLastUpdateDate;
    }

    public void setTerLastUpdateDate(String terLastUpdateDate) {
        this.terLastUpdateDate = terLastUpdateDate;
    }

    public String getTerPackage() {
        return terPackage;
    }

    public void setTerPackage(String terPackage) {
        this.terPackage = terPackage;
    }

    public String getTerType() {
        return terType;
    }

    public void setTerType(String terType) {
        this.terType = terType;
    }

    public String getTerLowHigh() {
        return terLowHigh;
    }

    public void setTerLowHigh(String terLowHigh) {
        this.terLowHigh = terLowHigh;
    }

    public String getTerOrigin() {
        return terOrigin;
    }

    public void setTerOrigin(String terOrigin) {
        this.terOrigin = terOrigin;
    }

    public String getTerSize() {
        return terSize;
    }

    public void setTerSize(String terSize) {
        this.terSize = terSize;
    }


}
