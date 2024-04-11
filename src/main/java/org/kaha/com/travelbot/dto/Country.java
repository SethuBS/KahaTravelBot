package org.kaha.com.travelbot.dto;

import java.util.List;

public class Country {

    private String name;
    private String capital;
    private float latitude;
    private float longitude;
    private List<String> officialLanguages;
    private String drivingSide;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public List<String> getOfficialLanguages() {
        return officialLanguages;
    }

    public void setOfficialLanguages(List<String> officialLanguages) {
        this.officialLanguages = officialLanguages;
    }

    public String getDrivingSide() {
        return drivingSide;
    }

    public void setDrivingSide(String drivingSide) {
        this.drivingSide = drivingSide;
    }
}
