package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ClientModel;
import com.ttonline.vestman.models.ResetPassRequest;
import com.ttonline.vestman.models.SignupResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_resetpassword extends AppCompatActivity {
    private ImageButton btn_back;

    private TextInputLayout til_oldPass,til_newPass,til_confirmPass;
    private TextInputEditText ed_oldPass,ed_newPass,ed_confirmPass;
    private Button btn_resetPass;
    private String id = "",oldPass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_resetpassword);
        btn_back = findViewById(R.id.btn_resetpass_back);

        til_oldPass = findViewById(R.id.til_oldpass);
        til_newPass = findViewById(R.id.til_newpass);
        til_confirmPass = findViewById(R.id.til_confirmpass);

        ed_oldPass = findViewById(R.id.ed_oldpass);
        ed_newPass = findViewById(R.id.ed_newpass);
        ed_confirmPass = findViewById(R.id.ed_confirmpass);

        btn_resetPass = findViewById(R.id.btn_resetpass);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            id = intent.getStringExtra("userId");
//                Log.d("reset pass", "resetPassword: "+id);
        }
        getUser(id);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Screen_resetpassword.this, Screen_navigation.class));

            }
        });

        btn_resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = createDialog();
                dialog.show();

            }

            AlertDialog createDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Screen_resetpassword.this);
                builder.setMessage("Confirm Update");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearError();
                        resetPassword();
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
        });


    }
    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    void clearError(){
        til_oldPass.setError("");
        til_newPass.setError("");
        til_confirmPass.setError("");
    }
    void clearText(){
        ed_oldPass.setText("");
        ed_newPass.setText("");
        ed_confirmPass.setText("");

    }


    private void getUser(String id){
        ApiService.apiservice.getUserDetail(id).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    SignupResponse signupResponse = response.body();
                    if (signupResponse.isSuccess()){
                        ClientModel client = signupResponse.getData();
                        oldPass = client.getPassword();
                        Log.d("zzzz", "old pass: "+client.getPassword());


                    }else{
                        Log.d("zzzz", "userinfo: "+signupResponse.getMessage());
                    }
                }else{
                    Toast.makeText(Screen_resetpassword.this, "Error in response", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(Screen_resetpassword.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("tttt", t.getMessage());
            }
        });
    }

    private void resetPassword(){
        boolean isValid = true;



        if(!oldPass.equals(ed_oldPass.getText().toString())){
            til_oldPass.setError("Old Password is not correct!!!");
            isValid = false;
        } else if (isEmpty(ed_oldPass)){
            til_oldPass.setError("Old Password must be enter!!!");
            isValid = false;
        }
        if (isEmpty(ed_newPass)){
            til_newPass.setError("New Password must be enter!!!");
            isValid = false;
        }
        if (isEmpty(ed_confirmPass)){
            til_confirmPass.setError("Confirm Password must be enter!!!");
            isValid = false;
        }

        if(!ed_newPass.getText().toString().equals(ed_confirmPass.getText().toString())){
            til_confirmPass.setError("Confirm Password is not correct!!!");
            isValid = false;
        }






        if (isValid){


            ResetPassRequest resetPassRequest = new ResetPassRequest(ed_newPass.getText().toString());


            ApiService.apiservice.updatePassword(id,resetPassRequest).enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    if (response.isSuccessful() && response.body() != null){
                        SignupResponse signupResponse = response.body();
                        if (signupResponse.isSuccess()){
                            Log.d("zzzzz", "onResponse: "+signupResponse.getMessage());
                            Toast.makeText(Screen_resetpassword.this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            clearText();
                        }else {
                            Log.d("zzzzz", "onResponse: "+signupResponse.getMessage());

                        }
                    } else {
                        Toast.makeText(Screen_resetpassword.this,response.message() , Toast.LENGTH_SHORT).show();
                        Log.d("zzzzz", "onResponse: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable t) {
                    Log.d("zzzzz", "onResponse: " + t.getMessage());
                }
            });
        }
    }
}