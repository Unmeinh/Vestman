package com.ttonline.vestman.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttonline.vestman.models.ClientUpdateModel;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson= new GsonBuilder().setDateFormat("dd-mm-yyyy").create();

    ApiService apiservice= new Retrofit.Builder()
            .baseUrl("http://192.168.46.102:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("api/product/list")
    Call<Root> getProduct();

    @PUT("api/client/updateClient/{id}")
    Call<ClientUpdateModel> updateClient(@Path("id") String id, @Body ClientUpdateModel clientUpdateModel);

}
