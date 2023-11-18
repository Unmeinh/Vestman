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
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenConfirmBinding;
import com.ttonline.vestman.models.BillRequest;
import com.ttonline.vestman.models.CustomerInfo;
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.RootBill;
import com.ttonline.vestman.models.Root_cart;
import com.ttonline.vestman.models.SignupResponse;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_confirm extends AppCompatActivity {
    ActivityScreenConfirmBinding binding;
    List<Datum> list_cart=new ArrayList<>();

    private BillRequest billRequest ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenConfirmBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        callApiGetCart();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Screen_confirm.this,Screen_payment.class);
                startActivity(intent);
            }
        });

        binding.btnN4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int confirm_bill_total = convertMoneyStringToInteger(String.valueOf(binding.tvConfirmTotal.getText()));
                Log.d("zzzzzz", "confirm total: "+confirm_bill_total);
                if (confirm_bill_total>0){
                    callApiCreateBill();
                }
            }
        });
    }

    private static int convertMoneyStringToInteger(String moneyString) {
        // Loại bỏ ký tự không phải số từ chuỗi
        String cleanString = moneyString.replaceAll("[^0-9]", "");

        // Chuyển đổi thành số nguyên
        try {
            return Integer.parseInt(cleanString);
        } catch (NumberFormatException e) {
            // Xử lý nếu không thể chuyển đổi
            e.printStackTrace();
            return 0; // hoặc giá trị mặc định bạn muốn trả về
        }
    }
    /////////////////////////////
    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("user_id", "");
        Log.d("zzzz", "user id from product: "+userId);
        return userId;
    }
    ///////////////////////////////
    private void callApiCreateBill() {
        Intent intent = getIntent();
        BillRequest billRequest = new BillRequest();
        billRequest.setCustomerInfo((CustomerInfo) intent.getSerializableExtra("customerInfo"));
        billRequest.setPaymentMethod(intent.getIntExtra("paymentMethod", 0));
        billRequest.setTotal(convertMoneyStringToInteger(String.valueOf(binding.tvConfirmTotal.getText())));
        Log.d("z", "cal create bill: "+billRequest.getCustomerInfo().getNameCustomer());
        ApiService.apiservice.createOrder(getUserId(),billRequest).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()){
                    SignupResponse signupResponse = response.body();
                    if (signupResponse.isSuccess()){
                        Log.d("zzzz", "onResponse: "+signupResponse.getMessage());
                        Toast.makeText(Screen_confirm.this,signupResponse.getMessage(),Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(Screen_confirm.this,Screen_Odersucces.class);
                        startActivity(intent);
                    }else{
                        Log.e("zzz", "onFailure: "+signupResponse.getMessage());
                        Toast.makeText(Screen_confirm.this,signupResponse.getMessage(),Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(Screen_confirm.this,Screen_navigation.class);
                        startActivity(intent);
                    }
                }else{
                    Log.e("zz", "onFailure: "+response.message());
                    Toast.makeText(Screen_confirm.this,response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {

            }
        });

    }


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
                        CartAdapter cartAdapter = new CartAdapter(list_cart,Screen_confirm.this);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Screen_cart.this);
//                        binding.rcvCart.setLayoutManager(linearLayoutManager);
//                        DividerItemDecoration itemDecoration= new DividerItemDecoration(Screen_cart.this,DividerItemDecoration.VERTICAL);
//                        binding.rcvCart.addItemDecoration(itemDecoration);
                        binding.rcvCart.setAdapter(cartAdapter);
                        // Xóa dữ liệu cũ
                        // Thêm dữ liệu mới
                        cartAdapter.notifyDataSetChanged();
                        cartAdapter.setTotal(binding.tvConfirmTotal);
                        cartAdapter.setTv_total(binding.tvConfirmTotal);
                    } else {
                        Toast.makeText(Screen_confirm.this, "Error in API response: " + rootCart.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Screen_confirm.this, "Error in response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root_cart> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi khác nếu có
                Toast.makeText(Screen_confirm.this, "Failed to fetch cart data. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}