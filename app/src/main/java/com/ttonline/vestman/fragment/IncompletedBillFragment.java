package com.ttonline.vestman.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ttonline.vestman.Adapter.BillAdapter;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillModel;
import com.ttonline.vestman.models.RootBill;
import com.ttonline.vestman.screen.Screen_orderhistory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IncompletedBillFragment extends Fragment {
    private RecyclerView recyclerView;
    private BillAdapter billAdapter;
    private List<BillModel> mListBill = new ArrayList<>();
    private String idUser = "";

    public IncompletedBillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incompleted_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            idUser = intent.getStringExtra("userId");
        }

        recyclerView = view.findViewById(R.id.rcv_incomplete_bill);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        CallApiGetIncompleteBill();
    }
    public void CallApiGetIncompleteBill(){
        ApiService.apiservice.getIncompleteBill(idUser).enqueue(new Callback<RootBill>() {
            @Override
            public void onResponse(Call<RootBill> call, Response<RootBill> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.d("zzz-z", response.message());
                    RootBill rootBill = response.body();
                    if (rootBill.isSuccess()){
                        Log.d("zzz-z", rootBill.getMessage());
                        mListBill = rootBill.getData();
//                        Log.d("zzz-z", mListBill.get(0).getArr_product().get(0).getId_product().getName_product().toString());

                        billAdapter = new BillAdapter(getContext(),mListBill);
                        recyclerView.setAdapter(billAdapter);
                    }
                } else {
                    Log.d("zzz--z", response.message());
                }
            }

            @Override
            public void onFailure(Call<RootBill> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("zzz---z", t.getMessage());
            }
        });
    }
}