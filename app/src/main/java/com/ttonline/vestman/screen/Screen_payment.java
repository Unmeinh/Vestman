package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenPaymentBinding;

public class Screen_payment extends AppCompatActivity {
ActivityScreenPaymentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenPaymentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Screen_payment.this,Screen_shiping.class);
                startActivity(intent);
            }
        });

        binding.btnN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Screen_payment.this, " ", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Screen_payment.this,Screen_confirm.class);
                startActivity(intent);
            }
        });
    }
}