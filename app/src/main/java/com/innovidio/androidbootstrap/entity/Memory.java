package com.innovidio.androidbootstrap.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Memory {

@SerializedName("ram")
@Expose
private Integer ram;
@SerializedName("unit")
@Expose
private String unit;

public Integer getRam() {
return ram;
}

public void setRam(Integer ram) {
this.ram = ram;
}

public String getUnit() {
return unit;
}

public void setUnit(String unit) {
this.unit = unit;
}

}