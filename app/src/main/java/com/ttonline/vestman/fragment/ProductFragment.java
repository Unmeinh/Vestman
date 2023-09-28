package com.ttonline.vestman.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.Arapter.ProductArapter;
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.FragmentHomeBinding;
import com.ttonline.vestman.databinding.FragmentProductBinding;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private List<ProductModel> mListProduct=new ArrayList<>();
    private FragmentProductBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        callApiGetProduct();
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
}