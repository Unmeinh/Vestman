package com.ttonline.vestman.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.ttonline.vestman.Arapter.PhotoArapter;
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenHotDealBinding;
import com.ttonline.vestman.databinding.ActivityScreenNavigationBinding;
import com.ttonline.vestman.models.Photo;

import java.util.ArrayList;
import java.util.List;

public class Screen_hot_deal extends AppCompatActivity {
    ActivityScreenHotDealBinding binding;
    private List<Photo>mlistPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenHotDealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager2.setOffscreenPageLimit(3);
        binding.viewPager2.setClipToPadding(false);
        binding.viewPager2.setClipChildren(false);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1 - Math.abs(position);
                page.setScaleY(0.90f+r*0.15f);
            }
        });

        binding.viewPager2.setPageTransformer(compositePageTransformer);

        mlistPhoto=getListPhoto();
        PhotoArapter photoArapter=new PhotoArapter(mlistPhoto);
        binding.viewPager2.setAdapter(photoArapter);
        binding.CircleIndicator3.setViewPager(binding.viewPager2);

    }
    private List<Photo> getListPhoto(){
        List<Photo> list=new ArrayList<>();
        list.add(new Photo(R.drawable.img_silideshow_1));
        list.add(new Photo(R.drawable.img_silideshow_2));
        list.add(new Photo(R.drawable.img_silideshow_3));
        list.add(new Photo(R.drawable.img_silideshow_4));
        list.add(new Photo(R.drawable.img_silideshow_5));
        return list;
    }
}