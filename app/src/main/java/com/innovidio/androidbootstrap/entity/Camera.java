package com.innovidio.androidbootstrap.entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Camera {

    @SerializedName("primary")
    @Expose
    private List<Primary> primary = null;
    @SerializedName("secondary")
    @Expose
    private Secondary secondary;

    public List<Primary> getPrimary() {
        return primary;
    }

    public void setPrimary(List<Primary> primary) {
        this.primary = primary;
    }

    public Secondary getSecondary() {
        return secondary;
    }

    public void setSecondary(Secondary secondary) {
        this.secondary = secondary;
    }

}