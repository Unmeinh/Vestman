package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityMainBinding;
import com.ttonline.vestman.databinding.ActivityScreenNavigationBinding;
import com.ttonline.vestman.fragment.AccountFragment;
import com.ttonline.vestman.fragment.HomeFragment;
import com.ttonline.vestman.fragment.ProductFragment;

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
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.freame_layout, fragment);
        fragmentTransaction.commit();
    }
}