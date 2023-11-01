package com.ttonline.vestman.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.Arapter.HotdealArapter;
import com.ttonline.vestman.Arapter.PhotoArapter;
import com.ttonline.vestman.Arapter.ProductArapter;
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenHotDealBinding;
import com.ttonline.vestman.databinding.ActivityScreenNavigationBinding;
import com.ttonline.vestman.models.ModelSlideShow;
import com.ttonline.vestman.models.Photo;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_hot_deal extends AppCompatActivity {
    ActivityScreenHotDealBinding binding;
    private List<ProductModel> mListProduct=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenHotDealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        callApiGetProduct();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Screen_hot_deal.this,Screen_navigation.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            ModelSlideShow modelSlideShow = intent.getParcelableExtra("modelSlideShow");
            if (modelSlideShow != null) {
                // Bạn có thể sử dụng modelSlideShow để hiển thị dữ liệu trên màn hình Screen_hot_deal
                String thumbnailImage = modelSlideShow.getThumbnailImage();
                mListProduct.add(modelSlideShow.getProductModel()) ;
                String description = modelSlideShow.getDescription();
                List<ModelSlideShow> mlistpro=new ArrayList<>();

                Picasso.get().load(modelSlideShow.getThumbnailImage()).into(binding.imgSlideshow);
                binding.tvDescription.setText(description);
                HotdealArapter hotdealArapter = new HotdealArapter(mListProduct, Screen_hot_deal.this);
                binding.gridProd.setAdapter(hotdealArapter);
                // ...
            }
        }


    }

    private void callApiGetProduct() {
        ApiService.apiservice.getProduct().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Root root = response.body();
                    if (root.isSuccess()) {
                        ArrayList<ProductModel> productList = root.getData();
                        mListProduct.addAll(productList);

                        // Tạo và cấu hình adapter
                        HotdealArapter hotdealArapter = new HotdealArapter(mListProduct, Screen_hot_deal.this);
                        binding.gridProd.setAdapter(hotdealArapter);
                    } else {
                        Toast.makeText(Screen_hot_deal.this, "Error in API response: " + root.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Screen_hot_deal.this, "Error in response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(Screen_hot_deal.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("tttt", t.getMessage());
            }
        });
    }
}