package com.innovidio.androidbootstrap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Battery {

@SerializedName("power")
@Expose
private Integer power;
@SerializedName("unit")
@Expose
private String unit;

public Integer getPower() {
return power;
}

public void setPower(Integer power) {
this.power = power;
}

public String getUnit() {
return unit;
}

public void setUnit(String unit) {
this.unit = unit;
}

}