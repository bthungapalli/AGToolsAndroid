package com.agtools.agtools.Model;

/**
 * Created by soulsticesoftware on 26-01-2018.
 */

public class WeatherResponse {
    public String temparature;
    public String HighTemp;
    public String lowTemp;
    public String precipitation;
    public String windSpeed;
    public String humidity;
    public String uvIndex;
    public String colors;
    public String day;

    public WeatherResponse(String temparature, String highTemp, String lowTemp, String precipitation, String windSpeed, String humidity, String uvIndex, String colors,String day) {
        this.temparature = temparature;
        HighTemp = highTemp;
        this.lowTemp = lowTemp;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.colors = colors;
        this.day = day;
    }

    public String getTemparature() {
        return temparature;
    }

    public void setTemparature(String temparature) {
        this.temparature = temparature;
    }

    public String getHighTemp() {
        return HighTemp;
    }

    public void setHighTemp(String highTemp) {
        HighTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }
    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }



}
