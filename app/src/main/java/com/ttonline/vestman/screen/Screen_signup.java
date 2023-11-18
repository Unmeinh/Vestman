package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.SignupRequest;
import com.ttonline.vestman.models.SignupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_signup extends AppCompatActivity {

    private TextView txt_login;
    private TextInputLayout til_username,til_email,til_password,til_repassword,til_phone;
    private TextInputEditText ed_username,ed_email,ed_password,ed_repassword,ed_phone;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_signup);
        btn_signup =findViewById(R.id.signup_btn_signup);
        txt_login = findViewById(R.id.backlogin);

        til_username = findViewById(R.id.signup_til_username);
        til_email = findViewById(R.id.signup_til_email);
        til_password = findViewById(R.id.signup_til_password);
        til_repassword = findViewById(R.id.signup_til_repassword);
        til_phone = findViewById(R.id.signup_til_phone);

        ed_username =findViewById(R.id.signup_ed_username);
        ed_email = findViewById(R.id.signup_ed_email);
        ed_password = findViewById(R.id.signup_ed_password);
        ed_repassword = findViewById(R.id.signup_ed_repassword);
        ed_phone = findViewById(R.id.signup_ed_phone);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearError();
                Signup();
            }
        });
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Screen_signup.this, Screen_login.class));

            }
        });
    }

    boolean isEmpty(TextInputEditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    void clearError(){
        til_username.setError(null);
        til_email.setError(null);
        til_password.setError(null);
        til_repassword.setError(null);
        til_phone.setError(null);
    }

    void Signup(){
        boolean isValid = true;
        if (isEmpty(ed_username)){
            til_username.setError("Username must be enter!!!");
            isValid = false;
        }
        if (isEmpty(ed_email)){
            til_email.setError("Email must be enter!!!");
            isValid = false;
        }
        if (isEmpty(ed_phone)){
            til_phone.setError("Phone number must be enter!!!");
            isValid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(ed_email.getText().toString()).matches()){
            til_email.setError("Invalid email!!!");
            isValid = false;
        }
        if (isEmpty(ed_password)){
            til_password.setError("Password must be enter!!!");
            isValid = false;
        }
        if (isEmpty(ed_repassword)){
            til_repassword.setError("Repeat Password must be enter!!!");
            isValid = false;
        }

        if(!ed_repassword.getText().toString().equals(ed_password.getText().toString())){
            til_repassword.setError("Repeat Password is not correct!!!");
            isValid = false;
        }

        if (isValid){
            //su ly dang ky
            SignupRequest signupRequest = new SignupRequest();
            signupRequest.setUsername(ed_username.getText().toString());
            signupRequest.setEmail(ed_email.getText().toString());
            signupRequest.setPassword(ed_password.getText().toString());
            signupRequest.setPhone_number(Integer.parseInt(ed_phone.getText().toString()));

            ApiService.apiservice.Signup(signupRequest).enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    if (response.isSuccessful()){
                        SignupResponse signupResponse = response.body();
                        if (signupResponse.isSuccess()){


                            Log.d("zzzzz", "onResponse: Signup Successful");
                            Toast.makeText(Screen_signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Screen_signup.this, Screen_login.class));
                        } else {
                            Toast.makeText(Screen_signup.this, "Error in API response: " + signupResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(Screen_signup.this, "Signup Fail "+response.message(), Toast.LENGTH_SHORT).show();
                        Log.d("zzzzz", "onResponse: Signup Failed "+response.message());
                    }
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable t) {
                    Log.d("zzzzz", "onResponse: Call signup Failed");

                }
            });

        }

    }

}