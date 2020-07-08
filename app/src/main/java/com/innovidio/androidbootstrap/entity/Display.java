package com.innovidio.androidbootstrap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Display {

@SerializedName("size")
@Expose
private Double size;
@SerializedName("resolution")
@Expose
private String resolution;

public Double getSize() {
return size;
}

public void setSize(Double size) {
this.size = size;
}

public String getResolution() {
return resolution;
}

public void setResolution(String resolution) {
this.resolution = resolution;
}

}