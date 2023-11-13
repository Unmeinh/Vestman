package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.IdProduct;
import com.ttonline.vestman.models.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Screen_orderdetailitem extends AppCompatActivity {

    private TextView tv_name, tv_price,tv_color,tv_description,tv_size,tv_qt;
    private ImageButton btn_back;
    private ImageView img_item;
    private String itemSize ="";
    private int itemQt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_orderdetailitem);
        tv_name = findViewById(R.id.detail_item_name);
        tv_price = findViewById(R.id.detail_item_price);
        tv_color = findViewById(R.id.detail_item_color);
        tv_description = findViewById(R.id.detail_item_description);
        tv_size = findViewById(R.id.detail_item_size);
        tv_qt = findViewById(R.id.detail_item_quantity);


        btn_back = findViewById(R.id.detail_item_btn_cancle);

        img_item = findViewById(R.id.detail_item_imageView);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        IdProduct idProduct = getIntent().getParcelableExtra("orderdetailitem");
        itemSize = getIntent().getStringExtra("orderdetailsize");
        itemQt = getIntent().getIntExtra("orderdetailqt",0);

        tv_name.setText(idProduct.getName_product());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String total = format.format(idProduct.getPrice());
        tv_price.setText(total);
        tv_color.setText("Color: "+idProduct.getColor());
        tv_description.setText("Description: "+idProduct.getDetail_product());
        tv_size.setText(itemSize);
        tv_qt.setText(Integer.toString(itemQt));

        ArrayList listImg = idProduct.getImages();
        if (listImg != null){
            Log.d("zzzz", "list img : "+listImg);
            try{
                Glide.with(this).load(listImg.get(0)).into(img_item);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}