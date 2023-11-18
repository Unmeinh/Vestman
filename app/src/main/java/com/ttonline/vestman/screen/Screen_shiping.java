package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenShipingBinding;
import com.ttonline.vestman.models.CustomerInfo;

import java.io.Serializable;

public class Screen_shiping extends AppCompatActivity {
    ActivityScreenShipingBinding binding;
    private String userID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shiping);
        binding= ActivityScreenShipingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userID = intent.getStringExtra("userIdShipping");
        Log.d("zzz", "shipping userid: "+userID);
        binding.btnButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getShippingInfo();
            }
        });


    }
    public void getShippingInfo(){
        String nameCustomer,phoneNumber,address;
        nameCustomer = binding.shippingName.getText().toString();
        phoneNumber = binding.shippingPhone.getText().toString();
        address = binding.shippingAddress.getText().toString() + ", "+binding.shippingProvince.getText().toString() + ", " + binding.shippingCity.getText().toString();

        CustomerInfo customerInfo = new CustomerInfo(nameCustomer,phoneNumber,address);
        Log.d("zzzz", "getShippingInfo: "+customerInfo.getNameCustomer());

        int billTotal = getIntent().getIntExtra("billtotal",0);
        Log.d("zzzz", "getShippingInfo: "+billTotal);

        ////đủ thông tin
        Intent intent= new Intent(Screen_shiping.this,Screen_payment.class);
        intent.putExtra("shipping_customer_info", customerInfo);
        intent.putExtra("shipping_bill_total",billTotal);
        intent.putExtra("shipping_user_id",userID);
        startActivity(intent);
    }
}