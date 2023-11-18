package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.Arapter.CartAdapter;
import com.ttonline.vestman.databinding.ActivityScreenCartBinding;
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.Root_cart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_cart extends AppCompatActivity{
    ActivityScreenCartBinding binding;
    List<Datum> list_cart=new ArrayList<>();
    private int billTotal = 0;
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
                onBackPressed();
            }
        });
        binding.btnCartt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Screen_cart.this,Screen_shiping.class);
                Log.d("zzz", "onClick: "+billTotal);
                intent.putExtra("billtotal",billTotal);

                startActivity(intent);
            }
        });
    }

/////////////////////////////
    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("user_id", "");
        Log.d("zzzz", "user id from product: "+userId);
        return userId;
    }
///////////////////////////////


    private void callApiGetCart() {

        Call<Root_cart> call = ApiService.apiservice.getCartItems(getUserId());
        call.enqueue(new Callback<Root_cart>() {
            @Override
            public void onResponse(Call<Root_cart> call, Response<Root_cart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Root_cart rootCart = response.body();
                    if (rootCart.isSuccess()) {
                        ArrayList<Datum> cartData = rootCart.getData();
                        list_cart.addAll(cartData);

                        Log.d("TAG", String.valueOf(response.body().getData()));
                        // Tạo và cấu hình adapter
                        CartAdapter cartAdapter = new CartAdapter(list_cart,Screen_cart.this);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Screen_cart.this);
//                        binding.rcvCart.setLayoutManager(linearLayoutManager);
//                        DividerItemDecoration itemDecoration= new DividerItemDecoration(Screen_cart.this,DividerItemDecoration.VERTICAL);
//                        binding.rcvCart.addItemDecoration(itemDecoration);
                        binding.rcvCart.setAdapter(cartAdapter);
                        // Xóa dữ liệu cũ
                        // Thêm dữ liệu mới
                        cartAdapter.notifyDataSetChanged();
                        cartAdapter.setTotal(binding.tvCartBillTotal);
                        cartAdapter.setTv_total(binding.tvCartBillTotal);
                        ///

//                        if (list_cart != null){
//                            billTotal = cartAdapter.calculateTotal(list_cart);
//                        }
//
//                        showTotal(billTotal);

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
            }
        });
    }

    public void showTotal(int billTotal) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String total = format.format(billTotal);

        binding.tvCartBillTotal.setText(total);
    }
}