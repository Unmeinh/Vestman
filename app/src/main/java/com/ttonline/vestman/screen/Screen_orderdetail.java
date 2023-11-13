package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ttonline.vestman.Adapter.BillItemAdapter;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillModel;
import com.ttonline.vestman.models.ClientModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Screen_orderdetail extends AppCompatActivity {

    private ImageButton btn_back;
    private RecyclerView rcv_orderdetail_item;
    private TextView tv_date,tv_status,tv_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_orderdetail);

        Intent intent = getIntent();

        String billName = "";
        if (intent != null && intent.hasExtra("billDetail")) {
            billName = intent.getStringExtra("billDetail");

        }
        Gson gson = new Gson();
        BillModel bill = gson.fromJson(billName, BillModel.class);
        Log.d("zzzz--detail", "onCreate: "+bill.get_id());

        btn_back = findViewById(R.id.btn_order_detail_back);
        rcv_orderdetail_item = findViewById(R.id.rcv_order_detail_item);
        tv_date = findViewById(R.id.tv_order_detail_date);
        tv_status = findViewById(R.id.tv_order_detail_status);
        tv_total = findViewById(R.id.tv_order_detail_price);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rcv_orderdetail_item.setLayoutManager(new LinearLayoutManager(this));
        rcv_orderdetail_item.setAdapter(new BillItemAdapter(this, bill.getArr_product()));

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            TimeZone timeZone = TimeZone.getTimeZone("ICT");
            inputFormat.setTimeZone(timeZone);
            outputFormat.setTimeZone(timeZone);

            Date date = inputFormat.parse(bill.getCreated_at());
            String formattedDate = outputFormat.format(date);

            tv_date.setText("Date: "+formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String status = "";
        if (bill.getStatus() == -1){
            status = "Unconfimred";
        } else if (bill.getStatus() == 0){
            status = "Delivering";
        } else if (bill.getStatus() == 1){
            status = "Delivered";
        } else if (bill.getStatus() == 2){
            status = "Received";
        }

        tv_status.setText("Status: "+status);

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String total = format.format(bill.getTotal());
        tv_total.setText(total);



    }
}