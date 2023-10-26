package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.ttonline.vestman.models.SignupResponse;

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
    private String id = "";


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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            id = intent.getStringExtra("userId");
        }
        ApiService.apiservice.getUserDetail(id).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    SignupResponse signupResponse = response.body();
                    if (signupResponse.isSuccess()){
                        ClientModel client = signupResponse.getData();
                        Log.d("zzzz", "userinfo: "+client);
                        ed_info_name.setText(client.getFull_name());
                        ed_info_email.setText(client.getEmail());
                        ed_info_phone.setText(client.getPhone_number().toString());
                        ed_info_address.setText(client.getAddress());

                        //ảnh
                        Glide.with(Screen_userinfo.this).load(client.getAvatar()).into(img_avatar);
                    }else{
                        Log.d("zzzz", "userinfo: "+signupResponse.getMessage());
                    }
                }else{
                    Toast.makeText(Screen_userinfo.this, "Error in response", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(Screen_userinfo.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("tttt", t.getMessage());
            }
        });

//        ClientModel demoClient = new ClientModel("A12345","12345","Nguyen Van A","a123@gmail.com","ABC BHD","https://png.pngtree.com/png-vector/20190822/ourmid/pngtree-avatar-client-face-happy-man-person-user-business-flat-li-png-image_1695892.jpg",021323123,null);



        btn_info_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = createDialog();
                dialog.show();
            }
            AlertDialog createDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Screen_userinfo.this);
                builder.setMessage("Confirm Update");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateUserInfo();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createDialog().dismiss();
                    }
                });
                return builder.create();
            }

            private void updateUserInfo(){
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


        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAvatar();
            }

            private void updateAvatar(){


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