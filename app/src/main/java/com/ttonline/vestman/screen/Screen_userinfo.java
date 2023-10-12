package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ClientModel;
import com.ttonline.vestman.models.ClientUpdateModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_userinfo extends AppCompatActivity {
    private ImageButton btn_back;
    private Button btn_info_update;
    private EditText ed_info_name,ed_info_email,ed_info_phone,ed_info_address;
    private ImageView img_avatar;
    Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_userinfo);
        btn_back = findViewById(R.id.btn_userinfo_back);
        img_avatar = findViewById(R.id.img_user);
        ed_info_name = findViewById(R.id.ed_info_username);
        ed_info_email = findViewById(R.id.ed_info_email);
        ed_info_phone = findViewById(R.id.ed_info_phone);
        ed_info_address = findViewById(R.id.ed_info_address);
        btn_info_update = findViewById(R.id.btn_update_info);

        ClientModel demoClient = new ClientModel("A12345","12345","Nguyen Van A","a123@gmail.com","ABC BHD","https://png.pngtree.com/png-vector/20190822/ourmid/pngtree-avatar-client-face-happy-man-person-user-business-flat-li-png-image_1695892.jpg",021323123,null);

        ed_info_name.setText(demoClient.getFull_name());
        ed_info_email.setText(demoClient.getEmail());
        ed_info_phone.setText(demoClient.getPhone_number().toString());
        ed_info_address.setText(demoClient.getAddress());

        //ảnh
        Glide.with(this).load(demoClient.getAvatar()).into(img_avatar);

        String id = "651d2f76d4fd68889763d190";
        btn_info_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientUpdateModel modelUpdate = new ClientUpdateModel(ed_info_name.getText().toString(),ed_info_email.getText().toString(),ed_info_address.getText().toString(),Integer.parseInt(ed_info_phone.getText().toString()));
                ApiService.apiservice.updateClient(id,modelUpdate).enqueue(new Callback<ClientUpdateModel>() {
                    @Override
                    public void onResponse(Call<ClientUpdateModel> call, Response<ClientUpdateModel> response) {
                        if(response.isSuccessful()){
                            Log.d("zzzzz", "onResponse: cap nhat thanh cong");
                            Toast.makeText(Screen_userinfo.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ClientUpdateModel> call, Throwable t) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Screen_userinfo.this, Screen_navigation.class));

            }
        });
    }
}