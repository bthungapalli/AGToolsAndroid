package com.agtools.agtools.Model;

/**
 * Created by soulsticesoftware on 03-02-2018.
 */

public class HolidayModal {
    String holidayDate;
    String description;
    public HolidayModal(String holidayDate, String description) {
        this.holidayDate = holidayDate;
        this.description = description;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
