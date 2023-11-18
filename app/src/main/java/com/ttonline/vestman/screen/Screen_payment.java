package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenPaymentBinding;
import com.ttonline.vestman.models.BillRequest;
import com.ttonline.vestman.models.CustomerInfo;

public class Screen_payment extends AppCompatActivity {
ActivityScreenPaymentBinding binding;
    private int paymentMethod = 0;
    private String userID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenPaymentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int billTotal = intent.getIntExtra("shipping_bill_total",0);
        userID = intent.getStringExtra("shipping_user_id");
        Log.d("zzz", "payment userid: "+userID);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rgPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.rbtn_cod:
                        paymentMethod = 0;

                        break;
                    case R.id.rbtn_card:
//                        paymentMethod = 1;
                        Toast.makeText(Screen_payment.this,"This function is in deverlop",Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        });

        binding.btnN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerInfo customerInfo = (CustomerInfo) getIntent().getSerializableExtra("shipping_customer_info");


                /////
                if (customerInfo != null){

//                    BillRequest billRequest = new BillRequest();
//                    billRequest.setCustomerInfo(customerInfo);
//                    billRequest.setPaymentMethod(paymentMethod);
//                    billRequest.setTotal(billTotal);

                    //gửi paymentmethod va cusinfo đi
                    Intent intent= new Intent(Screen_payment.this,Screen_confirm.class);
                    intent.putExtra("customerInfo",customerInfo);
                    intent.putExtra("paymentMethod",paymentMethod);
                    intent.putExtra("billTotal",billTotal);
                    startActivity(intent);

                    Log.d("zzz", "payment: "+customerInfo.getNameCustomer()+" "+paymentMethod);
                }else {
                    Toast.makeText(Screen_payment.this,"Choose a payment method",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}