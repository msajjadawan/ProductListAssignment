package com.innovidio.androidbootstrap.repository;

import com.innovidio.androidbootstrap.db.dao.FavoriteItemDao;
import com.innovidio.androidbootstrap.entity.Device;
import com.innovidio.androidbootstrap.entity.FavoriteItem;
import com.innovidio.androidbootstrap.event.FavoriteMarkedEvent;
import com.innovidio.androidbootstrap.network.APIService;
import com.innovidio.androidbootstrap.network.StateLiveData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class SampleRepository {

    APIService apiService;
    FavoriteItemDao favoriteItemDao;
    private StateLiveData<List<Device>> mobilesLiveData;
    HashMap<String, FavoriteItem> favoriteItemHashMap = null;

    @Inject
    public SampleRepository(APIService apiService, FavoriteItemDao favoriteItemDao) {
        this.apiService = apiService;
        this.favoriteItemDao = favoriteItemDao;
        cacheFavoriteItems();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void finalize() throws Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }

    private void cacheFavoriteItems() {
        favoriteItemHashMap = new HashMap<>();
        List<FavoriteItem> favoriteItems = favoriteItemDao.findAll();
        for (FavoriteItem favoriteItem:favoriteItems) {
            favoriteItemHashMap.put(favoriteItem.getProductTitle(),favoriteItem);
        }
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMarkFavoriteEvent(FavoriteMarkedEvent event) {
        markFavorite(event.getFavoriteItem(),event.isFavorite());
    };
    private void markFavorite(FavoriteItem favoriteItem, boolean favorite){
       if(!favorite){
           favoriteItemDao.delete(favoriteItem);
           favoriteItemHashMap.remove(favoriteItem.getProductTitle());
       }else{
           favoriteItemDao.insert(favoriteItem);
           favoriteItemHashMap.put(favoriteItem.getProductTitle(),favoriteItem);
       }

    }

    public StateLiveData<List<Device>> getMobilesList() {
        mobilesLiveData = new StateLiveData<>();
        loadMobilesList();
        return mobilesLiveData;
    }

    private void loadMobilesList() {
        Call<List<Device>> call = apiService.getMobiles();
        mobilesLiveData.postLoading();

        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                List<Device> mobiles = response.body();
                for (Device device:mobiles) {
                   if( favoriteItemHashMap.containsKey(device.getName())){
                        device.setFavourite(true);
                    }
                }
                mobilesLiveData.postSuccess(mobiles);
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                mobilesLiveData.postError(t);
            }
        });

    }
}
