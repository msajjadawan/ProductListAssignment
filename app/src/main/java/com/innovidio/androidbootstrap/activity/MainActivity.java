package com.innovidio.androidbootstrap.activity;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils;
import com.innovidio.androidbootstrap.adapter.ProductsAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityMainBinding;
import com.innovidio.androidbootstrap.entity.Device;
import com.innovidio.androidbootstrap.entity.Devices;
import com.innovidio.androidbootstrap.network.NetworkError;
import com.innovidio.androidbootstrap.utils.SpacesItemDecoration;
import com.innovidio.androidbootstrap.viewmodel.MyViewModel;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import java.util.List;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    MyViewModel myViewModel ;
    private List<Device> mobilesList;

    ProductsAdapter adapter = new ProductsAdapter();


  //  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS));
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.textColorDark));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.productsRecView.setLayoutManager(layoutManager);
        int spacingInPixels = Utils.dpToPx(1);
        binding.productsRecView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        binding.productsRecView.setAdapter(adapter);

        observeMobilesList();

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshData();
                    }
                }
        );
    }

        private void refreshData() {
            mobilesList.clear();
            adapter.setData(mobilesList);
            observeMobilesList();
        }


    private void observeMobilesList(){

        myViewModel.getMobilesList().observe(this, listStateData -> {

            switch (listStateData.getStatus()) {
                case SUCCESS:
                    mobilesList = listStateData.getData();
                    if (mobilesList != null && mobilesList.size() > 0){
                        Toast.makeText(this, "List Loaded", Toast.LENGTH_SHORT).show();
                        binding.tvCount.setText(String.format("%,d", Long.parseLong(String.valueOf(mobilesList.size()))) + " Results");
                        adapter.setData(mobilesList);
                    }else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    }
                    binding.swiperefresh.setRefreshing(false);

                    break;
                case ERROR:
                    Throwable e = listStateData.getError();
                    NetworkError networkError = new NetworkError(e);
                    Log.e("Error:", networkError.getAppErrorMessage());
                    Toast.makeText(MainActivity.this, networkError.getAppErrorMessage(), Toast.LENGTH_SHORT).show();
                    binding.swiperefresh.setRefreshing(false);
                    break;
                case LOADING:
                    Toast.makeText(this, "Loading....", Toast.LENGTH_SHORT).show();
                    break;
                case COMPLETE:
                    Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show();
                    break;
            }

        });

    }

}
