package com.ttonline.vestman.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    Gson gson= new GsonBuilder().setDateFormat("dd-mm-yyyy").create();

    ApiService apiservice= new Retrofit.Builder()
            .baseUrl("http://192.168.1.249:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("api/product/list")
    Call<Root> getProduct();

}
