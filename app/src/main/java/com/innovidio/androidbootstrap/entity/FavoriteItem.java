package com.innovidio.androidbootstrap.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteItem {
    @PrimaryKey
    private int id;
    private String productTitle;

    public FavoriteItem(String productTitle) {
        this.productTitle = productTitle;
        this.id = productTitle.hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}
