package com.ttonline.vestman.screen;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.Arapter.CartAdapter;
import com.ttonline.vestman.Arapter.ProductArapter;
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenCartBinding;
import com.ttonline.vestman.databinding.ActivityScreenNavigationBinding;
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.IdProduct;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.Root;
import com.ttonline.vestman.models.Root_cart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_cart extends AppCompatActivity {
    ActivityScreenCartBinding binding;
    List<Datum> list_cart=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        callApiGetCart();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Screen_cart.this,Screen_navigation.class);
                startActivity(intent);
            }
        });
    }
    private void callApiGetCart() {
        String userId = "650c27f6cbe42ee7d05816d8"; // Thay thế bằng ID người dùng thực tế

        Call<Root_cart> call = ApiService.apiservice.getCartItems(userId);

        call.enqueue(new Callback<Root_cart>() {
            @Override
            public void onResponse(Call<Root_cart> call, Response<Root_cart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Root_cart rootCart = response.body();
                    if (rootCart.isSuccess()) {
                        ArrayList<Datum> cartData = rootCart.getData();
                        list_cart.clear();
                        list_cart.addAll(cartData);
                        // Tạo và cấu hình adapter
                        CartAdapter cartAdapter = new CartAdapter(list_cart,Screen_cart.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Screen_cart.this);
                        binding.rcvCart.setLayoutManager(linearLayoutManager);
                        DividerItemDecoration itemDecoration= new DividerItemDecoration(Screen_cart.this,DividerItemDecoration.VERTICAL);
                        binding.rcvCart.addItemDecoration(itemDecoration);
                        binding.rcvCart.setAdapter(cartAdapter);
                        // Xóa dữ liệu cũ
                        // Thêm dữ liệu mới
//                        cartAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Screen_cart.this, "Error in API response: " + rootCart.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Screen_cart.this, "Error in response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root_cart> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi khác nếu có
                Toast.makeText(Screen_cart.this, "Failed to fetch cart data. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}