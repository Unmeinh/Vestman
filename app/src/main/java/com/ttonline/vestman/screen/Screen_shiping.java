package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenShipingBinding;

public class Screen_shiping extends AppCompatActivity {
    ActivityScreenShipingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shiping);
        binding= ActivityScreenShipingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Screen_shiping.this,Screen_cart.class);
                startActivity(intent);
            }
        });
        binding.btnN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Screen_shiping.this,Screen_payment.class);
                startActivity(intent);
            }
        });
    }
}