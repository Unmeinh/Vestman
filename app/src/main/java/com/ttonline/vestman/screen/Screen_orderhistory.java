package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ttonline.vestman.Adapter.BillAdapter;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillItemModel;
import com.ttonline.vestman.models.BillModel;
import com.ttonline.vestman.models.RootBill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_orderhistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BillAdapter billAdapter;
    private List<BillModel> mListBill = new ArrayList<>();
    private ArrayList<BillItemModel> mListBillItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CallApiGetBillHL();

        setContentView(R.layout.activity_sreen_orderhistory);

        mListBillItem.add(new BillItemModel("1","XL","10"));
        mListBillItem.add(new BillItemModel("2","XL","10"));
        mListBillItem.add(new BillItemModel("3","XL","10"));

        recyclerView = findViewById(R.id.rcv_order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mListBill.add(new BillModel("1",mListBillItem,3,null));
        mListBill.add(new BillModel("2",mListBillItem,3,null));
        mListBill.add(new BillModel("3",mListBillItem,3,null));

        billAdapter = new BillAdapter(mListBill);
        recyclerView.setAdapter(billAdapter);

    }

    public void CallApiGetBillHL(){
        Toast.makeText(Screen_orderhistory.this, "load bill", Toast.LENGTH_SHORT).show();

//        Call<List<BillModel>> call = ApiService.apiservice.getBillsHL();
//        call.enqueue(new Callback<List<BillModel>>() {
//            @Override
//            public void onResponse(Call<List<BillModel>> call, Response<List<BillModel>> response) {
//                if(response.isSuccessful()){
//                    mListBill = response.body();
//                    Log.d("zzzzz", "onResponse: "+response.body());
//
//                    billAdapter = new BillAdapter(mListBill);
//                    recyclerView.setAdapter(billAdapter);
//                    Toast.makeText(Screen_orderhistory.this, "bill loaded", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(Screen_orderhistory.this, "error bill", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<BillModel>> call, Throwable t) {
//                Toast.makeText(Screen_orderhistory.this, "error bill 2", Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        ApiService.apiservice.getBillsHL().enqueue(new Callback<RootBill>() {
//            @Override
//            public void onResponse(Call<RootBill> call, Response<RootBill> response) {
//                if(response.isSuccessful() && response.body() != null){
//                    RootBill rootBill = response.body();
//                    if (rootBill.isSuccess()){
//                        mListBill = rootBill.getData();
//                        billAdapter = new BillAdapter(mListBill);
//                        recyclerView.setAdapter(billAdapter);
//
//                    }else {
//                        Toast.makeText(Screen_orderhistory.this, "Error in API response: " + rootBill.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(Screen_orderhistory.this, "Error in response", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RootBill> call, Throwable t) {
//                Toast.makeText(Screen_orderhistory.this, "onFailure", Toast.LENGTH_SHORT).show();
//                Log.d("tttt", t.getMessage());
//            }
//        });





    }
}