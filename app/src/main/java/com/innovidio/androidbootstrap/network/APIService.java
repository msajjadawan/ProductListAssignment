package com.innovidio.androidbootstrap.network;

import com.innovidio.androidbootstrap.entity.Device;
import com.innovidio.androidbootstrap.entity.Devices;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIService {
    @Headers("secret-key: $2b$10$XuTKbXQO7gzfR6sW6NG0NuDRHpFXXA33IyXWWhXpiIpQV.qkxy3ZK")
    @GET("latest")
    Call<List<Device>> getMobiles();

}
