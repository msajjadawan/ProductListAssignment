package com.innovidio.androidbootstrap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Primary {

    @SerializedName("imagesensor")
    @Expose
    private Double imagesensor;
    @SerializedName("focallength")
    @Expose
    private String focallength;
    @SerializedName("unit")
    @Expose
    private String unit;

    public Double getImagesensor() {
        return imagesensor;
    }

    public void setImagesensor(Double imagesensor) {
        this.imagesensor = imagesensor;
    }

    public String getFocallength() {
        return focallength;
    }

    public void setFocallength(String focallength) {
        this.focallength = focallength;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}