package com.innovidio.androidbootstrap.event;

import com.innovidio.androidbootstrap.entity.FavoriteItem;

public class FavoriteMarkedEvent {
    FavoriteItem favoriteItem;
    boolean favorite;

    public FavoriteMarkedEvent(FavoriteItem favoriteItem,boolean favorite) {
        this.favoriteItem = favoriteItem;
        this.favorite = favorite;
    }

    public FavoriteItem getFavoriteItem() {
        return favoriteItem;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
