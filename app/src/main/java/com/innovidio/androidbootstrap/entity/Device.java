package com.innovidio.androidbootstrap.entity;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Device {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("display")
    @Expose
    private Display display;
    @SerializedName("cpu")
    @Expose
    private String cpu;
    @SerializedName("gpu")
    @Expose
    private String gpu;
    @SerializedName("memory")
    @Expose
    private Memory memory;
    @SerializedName("camera")
    @Expose
    private Camera camera;
    @SerializedName("battery")
    @Expose
    private Battery battery;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("isdeleted")
    @Expose
    private Boolean isdeleted;
    boolean favourite;
    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Price getPrice() {
        return price;
    }
    public String getDiscountPercentage() {
        return "Save 10%";
    }
    public String getDiscountPrice() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(price.getValue() - (price.getValue() * 0.1));
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
}