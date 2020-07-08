package com.innovidio.androidbootstrap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Secondary {

@SerializedName("imagesensor")
@Expose
private Integer imagesensor;
@SerializedName("focallength")
@Expose
private String focallength;
@SerializedName("unit")
@Expose
private String unit;

public Integer getImagesensor() {
return imagesensor;
}

public void setImagesensor(Integer imagesensor) {
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