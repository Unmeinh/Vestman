package com.ttonline.vestman.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
//        callApiGetProduct();
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
                Log.d("modelSlideShow", String.valueOf(modelSlideShow));
                mListProduct.add(modelSlideShow.getProductModel()) ;
                String description = modelSlideShow.getDescription();
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

// Log ngày bắt đầu
                Date startDate = null;
                try {
                    startDate = inputFormat.parse(modelSlideShow.getCreated_at());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String formattedStartDate = outputFormat.format(startDate);
                Log.d("Ngày bắt đầu:", formattedStartDate);

// Log ngày kết thúc
                Date endDate = null;
                try {
                    endDate = inputFormat.parse(modelSlideShow.getExpires_at());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String formattedEndDate = outputFormat.format(endDate);
                Log.d("Ngày kết thúc:", formattedEndDate);
                binding.startdate.setText("start date: "+formattedStartDate);
                binding.enddate.setText("end date: "+formattedEndDate);
                List<ModelSlideShow> mlistpro=new ArrayList<>();
                Picasso.get().load(modelSlideShow.getThumbnailImage()).into(binding.imgSlideshow);
                binding.tvDescription.setText(description);
                HotdealArapter hotdealArapter = new HotdealArapter(mListProduct, Screen_hot_deal.this);
                binding.gridProd.setAdapter(hotdealArapter);
                // ...
            }
        }


    }


}