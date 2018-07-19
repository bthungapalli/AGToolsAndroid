package com.agtools.agtools.Model;

/**
 * Created by soulsticesoftware on 27-02-2018.
 */

public class JuiceModel {
    String weekDay;
    String week;
    String accum;

    public JuiceModel(String weekDay, String week, String accum) {
        this.weekDay = weekDay;
        this.week = week;
        this.accum = accum;
    }
    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getAccum() {
        return accum;
    }

    public void setAccum(String accum) {
        this.accum = accum;
    }

}
