package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Device;
import com.innovidio.androidbootstrap.entity.Devices;
import com.innovidio.androidbootstrap.entity.FavoriteItem;
import com.innovidio.androidbootstrap.network.StateLiveData;
import com.innovidio.androidbootstrap.repository.SampleRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MyViewModel extends ViewModel {
    SampleRepository sampleRepository;
    @Inject
    public MyViewModel(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public StateLiveData<List<Device>> getMobilesList() {
        return sampleRepository.getMobilesList();
    }
}
