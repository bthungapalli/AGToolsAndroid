package com.agtools.agtools.Model;

/**
 * Created by soulsticesoftware on 29-01-2018.
 */

public class VolumeResponse {
    String index;
    String location;
    String count;

    public VolumeResponse(String index) {
        this.index = index;
    }


    public VolumeResponse(String index, String location, String count) {
        this.index = index;
        this.location = location;
        this.count = count;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
