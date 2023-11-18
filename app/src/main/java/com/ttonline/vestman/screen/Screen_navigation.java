package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityMainBinding;
import com.ttonline.vestman.databinding.ActivityScreenNavigationBinding;
import com.ttonline.vestman.fragment.AccountFragment;
import com.ttonline.vestman.fragment.HomeFragment;
import com.ttonline.vestman.fragment.ProductFragment;
import com.ttonline.vestman.models.ClientModel;

public class Screen_navigation extends AppCompatActivity {
    ActivityScreenNavigationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        String json = "";
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("loginUser")) {
            json = intent.getStringExtra("loginUser");
        }
        Gson gson = new Gson();
        ClientModel client = gson.fromJson(json, ClientModel.class);
//        Log.d("zvzzz", "onCreate: "+client.get_id());

        // Lưu chuỗi user id
        String userID = "";

        if (getUserId()!=""){
            userID = getUserId();
        }

        if (userID == ""){
            userID = client.get_id();
        }
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Screen_navigation.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", userID);
        editor.apply();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.product:
                    replaceFragment(new ProductFragment());
                    break;

                case R.id.account:
                    replaceFragment(new AccountFragment());
                    break;
            }
            return true;
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

    private void replaceFragment(Fragment fragment) {
        String json = "";
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("loginUser")) {
            json = intent.getStringExtra("loginUser");
        }
        Bundle bundle = new Bundle();
        bundle.putString("userInfo",json );
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.freame_layout, fragment);
        fragmentTransaction.commit();
    }
}