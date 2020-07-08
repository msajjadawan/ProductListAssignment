package com.innovidio.androidbootstrap.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ProductsListItemBinding;
import com.innovidio.androidbootstrap.entity.Device;
import com.innovidio.androidbootstrap.entity.FavoriteItem;
import com.innovidio.androidbootstrap.event.FavoriteMarkedEvent;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Device> list = new ArrayList<>();
    // this is just added for local filter whereas in main application it will be handled using api
    private List<Device> duplicateList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.products_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Device device = list.get(position);

        holder.itemBinding.setDevice(device);

        String url = device.getImage();


        Picasso.get()
                .load(url)
                .resize(512, 512)
                .into(holder.itemBinding.imgProduct, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.itemBinding.progressBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.itemBinding.progressBar2.setVisibility(View.GONE);
                        holder.itemBinding.imgProduct.setImageResource(R.drawable.placeholder);
                    }
                });


        if (device.isFavourite()) {
            holder.itemBinding.imgFav.setImageResource(R.drawable.ic_wishlist_red);
        } else {
            holder.itemBinding.imgFav.setImageResource(R.drawable.ic_wishlist_disabled);
        }

        holder.itemBinding.imgFav.setOnClickListener(v -> {
            FavoriteItem favoriteItem = new FavoriteItem(device.getName());
            if (!device.isFavourite()) {
                holder.itemBinding.imgFav.setImageResource(R.drawable.ic_wishlist_red);
                device.setFavourite(true);
                EventBus.getDefault().post(new FavoriteMarkedEvent(favoriteItem,true));

            } else {
                holder.itemBinding.imgFav.setImageResource(R.drawable.ic_wishlist_disabled);
                device.setFavourite(false);
                EventBus.getDefault().post(new FavoriteMarkedEvent(favoriteItem,false));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ProductsListItemBinding itemBinding;

        public ViewHolder(@NonNull ProductsListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

    public void setData(List<Device> list) {
        this.list.clear();
        this.list = list;
        duplicateList = new ArrayList<>();
        this.duplicateList.addAll(list);
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(duplicateList);
        } else {
            for (Device device : duplicateList) {
                if (device.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(device);
                }
            }
        }
        notifyDataSetChanged();
    }

}
