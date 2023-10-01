package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenCartBinding;
import com.ttonline.vestman.databinding.ActivityScreenNavigationBinding;

public class Screen_cart extends AppCompatActivity {
    ActivityScreenCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Screen_cart.this, "da thoat", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Screen_cart.this,Screen_navigation.class);
                startActivity(intent);
            }
        });
    }
}