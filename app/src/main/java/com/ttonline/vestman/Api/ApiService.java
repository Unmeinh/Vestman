package com.ttonline.vestman.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.ResMessage;
import com.ttonline.vestman.models.Root;
import com.ttonline.vestman.models.Root_cart;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson= new GsonBuilder().setDateFormat("dd-mm-yyyy").create();

    ApiService apiservice= new Retrofit.Builder()
            .baseUrl("http://192.168.45.108:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("api/product/list")
    Call<Root> getProduct();

    @GET("api/cart/list/{id_client}")
    Call<Root_cart> getCartItems(@Path("id_client") String id_client);

    @POST("api/cart/insert/{id_Client}")
    Call<Root_cart> addToCart(@Path("id_Client") String idClient, @Body Datum datum);

    @DELETE("api/cart/delete/{idCart}")
    Call<ResMessage> deleteToCart(@Path("idCart") String _id);

}
