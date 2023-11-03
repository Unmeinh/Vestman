package com.ttonline.vestman.screen;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ClientModel;
import com.ttonline.vestman.models.ClientUpdateModel;
import com.ttonline.vestman.models.LoginResponse;
import com.ttonline.vestman.models.SignupResponse;
import com.ttonline.vestman.util.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_userinfo extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private ImageButton btn_back;
    private Button btn_info_update,btn_update_avt;
    private EditText ed_info_name,ed_info_email,ed_info_phone,ed_info_address;
    private ImageView img_avatar;
    Handler mainHandler = new Handler();
    private String id = "";
    private Uri mUri;
    Context context;


    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e("zzzzz", "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            img_avatar.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                }
            }
    );


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
        btn_update_avt = findViewById(R.id.btn_update_avt);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            id = intent.getStringExtra("userId");
        }
        callApiUserInfo();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

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

                callApiUpdateUserInfo();
            }
        });

        btn_update_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check avt
                if (mUri != null){
                    callApiUpdateUserAvt();

                } else {
                    Log.d("zzzzz", "ko cập nhật ảnh");
                    Toast.makeText(Screen_userinfo.this, "Chưa chọn ảnh!!!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();

            }
        });
    }

    private void callApiUserInfo() {
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
                Log.d("zzzz", t.getMessage());

            }
        });
    }

    private void callApiUpdateUserAvt() {
        String strRealPath = RealPathUtil.getRealPath(this,mUri);
        Log.e("zzzzzimg", strRealPath );
        File file = new File(strRealPath);
        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part multiparkBodyAvt = MultipartBody.Part.createFormData("imageUrl",file.getName(),requestBodyAvt);

        ApiService.apiservice.updateAvt(id,multiparkBodyAvt).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    LoginResponse responseimg = response.body();

                    if (responseimg.isSuccess()){
                        Log.d("zzzzzzzz", responseimg.getMessage()+"");
                        Log.d("zzzzzzzz", responseimg.getData().getAvatar()+"");
                        Toast.makeText(Screen_userinfo.this, responseimg.getMessage(), Toast.LENGTH_SHORT).show();



                    }

                } else {
                    Toast.makeText(Screen_userinfo.this, "Fail "+response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("zzzzz", "onResponse: Failed "+response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Screen_userinfo.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("zzzzz", t.getMessage());
            }
        });

    }

    private void callApiUpdateUserInfo() {
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

    private void onClickRequestPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));

    }


}