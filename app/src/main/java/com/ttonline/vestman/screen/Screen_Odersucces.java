package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenOdersuccesBinding;

public class Screen_Odersucces extends AppCompatActivity {
    ActivityScreenOdersuccesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenOdersuccesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Screen_Odersucces.this,Screen_confirm.class);
                startActivity(intent);
            }
        });

        binding.btnN5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Screen_Odersucces.this,Screen_cart.class);
                startActivity(intent);
            }
        });
    }
}