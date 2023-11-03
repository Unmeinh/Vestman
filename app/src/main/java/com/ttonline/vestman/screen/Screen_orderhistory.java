package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttonline.vestman.Adapter.BillAdapter;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillItemModel;
import com.ttonline.vestman.models.BillModel;
import com.ttonline.vestman.models.RootBill;

import java.io.DataInput;
import java.io.IOException;
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
    private String idUser = "";
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            idUser = intent.getStringExtra("userId");
        }

        setContentView(R.layout.activity_sreen_orderhistory);
        recyclerView = findViewById(R.id.rcv_order);
        btn_back = findViewById(R.id.btn_orderhistory_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CallApiGetBillHL();

    }

    public void CallApiGetBillHL(){
        Toast.makeText(Screen_orderhistory.this, "load bill", Toast.LENGTH_SHORT).show();

        ApiService.apiservice.getBill(idUser).enqueue(new Callback<RootBill>() {
            @Override
            public void onResponse(Call<RootBill> call, Response<RootBill> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.d("zzz-z", response.message());
                    RootBill rootBill = response.body();
                    if (rootBill.isSuccess()){
                        Log.d("zzz-z", rootBill.getMessage());
                        mListBill = rootBill.getData();

                        billAdapter = new BillAdapter(Screen_orderhistory.this,mListBill);
                        recyclerView.setAdapter(billAdapter);

//                        try{
//                            ObjectMapper om = new ObjectMapper();
//                            RootBillHistory rootBillHistory = om.readValue((DataInput) rootBill.getData(),RootBillHistory.class);
//                        Log.d("zzz-z", rootBillHistory.data.toString());
//
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }


                    }

                } else {
                    Log.d("zzz--z", response.message());

                }

            }

            @Override
            public void onFailure(Call<RootBill> call, Throwable t) {
                Toast.makeText(Screen_orderhistory.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("zzz---z", t.getMessage());
            }
        });





    }
}