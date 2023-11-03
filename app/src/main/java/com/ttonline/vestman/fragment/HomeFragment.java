package com.ttonline.vestman.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.Arapter.PhotoArapter;
import com.ttonline.vestman.Arapter.ProductArapter;
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.FragmentHomeBinding;
import com.ttonline.vestman.models.ModelSlideShow;
import com.ttonline.vestman.models.Photo;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.Root;
import com.ttonline.vestman.models.RootSlideShow;
import com.ttonline.vestman.screen.Screen_cart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
        private List<ProductModel> mListProduct=new ArrayList<>();
    private List<ModelSlideShow> mListSlideShows=new ArrayList<>();
    private List<ModelSlideShow>mlistPhoto;


    private Handler handler= new Handler(Looper.getMainLooper());
    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            if (binding != null && binding.viewPager2 != null) {
                int currenPosition= binding.viewPager2.getCurrentItem();
                if (currenPosition==mlistPhoto.size()-1){
                    binding.viewPager2.setCurrentItem(0);

                }else {
                    binding.viewPager2.setCurrentItem(currenPosition+1);
                }
            }

        }
    };
    private FragmentHomeBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context=getContext();
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Screen_cart.class);
                startActivity(intent);
            }
        });
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
        callApiGetProduct();
        callApiSlideShow();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
                    ProductArapter productArapter = new ProductArapter(mListProduct, getContext());
                    binding.rcvProduct.setAdapter(productArapter);
                } else {
                    Toast.makeText(getContext(), "Error in API response: " + root.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Error in response", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Root> call, Throwable t) {
            Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
            Log.d("tttt", t.getMessage());
        }
    });
}

    private void callApiSlideShow() {
        ApiService.apiservice.getSideShow().enqueue(new Callback<RootSlideShow>() {
            @Override
            public void onResponse(Call<RootSlideShow> call, Response<RootSlideShow> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RootSlideShow rootSlideShow = response.body();
                    if (rootSlideShow.isSuccess()) {
                        ArrayList<ModelSlideShow> slideShows = rootSlideShow.getData();
                                 mlistPhoto=slideShows;
                                 PhotoArapter photoArapter=new PhotoArapter(mlistPhoto,getContext());
                                 binding.viewPager2.setAdapter(photoArapter);
                                 binding.CircleIndicator3.setViewPager(binding.viewPager2);
                                    binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2500);
            }
        });

                    } else {
                        Toast.makeText(getContext(), "Error in API response: " + rootSlideShow.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error in response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RootSlideShow> call, Throwable t) {
                Toast.makeText(getContext(),  "Slide show onFailure", Toast.LENGTH_SHORT).show();
                Log.d("tttt", t.getMessage());
            }
        });
    }

}