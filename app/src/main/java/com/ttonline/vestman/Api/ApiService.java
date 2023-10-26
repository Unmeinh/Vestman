package com.ttonline.vestman.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttonline.vestman.models.ClientModel;
import com.ttonline.vestman.models.ClientUpdateModel;
import com.ttonline.vestman.models.LoginRequest;
import com.ttonline.vestman.models.LoginResponse;
import com.ttonline.vestman.models.MsgModel;
import com.ttonline.vestman.models.ResetPassRequest;
import com.ttonline.vestman.models.Root;
import com.ttonline.vestman.models.RootBill;
import com.ttonline.vestman.models.SignupRequest;
import com.ttonline.vestman.models.SignupResponse;
import com.ttonline.vestman.models.ResMessage;
import com.ttonline.vestman.models.ClientUpdateModel;

import com.ttonline.vestman.models.Root;
import com.ttonline.vestman.models.Root_cart;
import com.ttonline.vestman.models.YourRequestClass;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {
    Gson gson= new GsonBuilder().setDateFormat("dd-mm-yyyy").create();

    ApiService apiservice= new Retrofit.Builder()

            .baseUrl("http://192.168.45.108:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

            .create(ApiService.class);
    @GET("api/product/list")
    Call<Root> getProduct();

    @GET("api/bill/list/{id}")
    Call<RootBill> getBills(@Path("id") String id);

    @PUT("api/client/updateClient/{id}")
    Call<ClientUpdateModel> updateClient(@Path("id") String id, @Body ClientUpdateModel clientUpdateModel);

    @PUT("api/client/updatePassword/{id}")
    Call<SignupResponse> updatePassword(@Path("id") String id, @Body ResetPassRequest resetPassRequest);

    @GET("api/client/detailClient/{id}")
    Call<SignupResponse> getUserDetail(@Path("id") String id);

    @POST("api/client/login")
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);

    @POST("api/client/register")
    Call<SignupResponse> Signup(@Body SignupRequest signupRequest);



    @GET
    Call<MsgModel> getMessage(@Url String url);

    @GET("api/cart/list/{id_client}")
    Call<Root_cart> getCartItems(@Path("id_client") String id_client);

    @POST("api/cart/insert/{idClient}")
    Call<ResMessage> insertToCart(@Path("idClient") String idClient, @Body YourRequestClass request);

    @DELETE("api/cart/delete/{idCart}")
    Call<ResMessage> deleteToCart(@Path("idCart") String _id);
      @PUT("api/client/updateClient/{id}")
    Call<ClientUpdateModel> updateClient(@Path("id") String id, @Body ClientUpdateModel clientUpdateModel);


}
