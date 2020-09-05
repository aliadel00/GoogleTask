package com.example.googletask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LearningModel {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("hours")
    @Expose
    private String hours;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("badgeUrl")
    @Expose
    private String badgeUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }
    public String getInfo(){
        return hours + " learning hours, " + country;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }
}
