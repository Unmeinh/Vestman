package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ClientModel;
import com.ttonline.vestman.models.LoginRequest;
import com.ttonline.vestman.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_login extends AppCompatActivity {

    private TextInputLayout til_username,til_password;
    private TextInputEditText ed_username,ed_password;
    private Button btn_login,btn_register;
    private CheckBox ckb_rememberme;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_login);
        til_username = findViewById(R.id.login_til_username);
        til_password = findViewById(R.id.login_til_password);
        ed_username = findViewById(R.id.login_ed_username);
        ed_password = findViewById(R.id.login_ed_password);
        btn_login = findViewById(R.id.login_btn_login);
        btn_register = findViewById(R.id.login_btn_register);
        ckb_rememberme = findViewById(R.id.ckb_rememberme);

        checkLoginRemember();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearError();
                Login();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Screen_login.this, Screen_signup.class));

            }
        });
    }

    public int checkLoginRemember(){
        String strUsername = "";
        String strPassword = "";

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        boolean chk = sharedPreferences.getBoolean("save",false);
        if (chk){
            strUsername = sharedPreferences.getString("username","");
            strPassword = sharedPreferences.getString("password","");
            ed_username.setText(strUsername);
            ed_password.setText(strPassword);
            ckb_rememberme.setChecked(chk);
            return 1;
        }
        return -1;
    }

    boolean isEmpty(TextInputEditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    void clearError(){
        til_password.setError(null);
        til_username.setError(null);
    }

    public void rememberUser(String username, String password,boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!status){
            editor.clear();
        }else{
            //add data
            editor.putString("username",username);
            editor.putString("password",password);
            editor.putBoolean("save",status);
        }
        //save
        editor.commit();
    }

    public void Login(){
        boolean isValid = true;

        String username = ed_username.getText().toString();
        String password = ed_password.getText().toString();

        if (isEmpty(ed_username)){
            til_username.setError("Username must be enter!!!");
            isValid = false;
        }
        if (isEmpty(ed_password)){
            til_password.setError("Password must be enter!!!");
            isValid = false;
        }

        if(isValid){
            LoginRequest loginRequest = new LoginRequest();

            loginRequest.setUsername(username);
            loginRequest.setPassword(password);




            ApiService.apiservice.Login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        LoginResponse loginResponse = response.body();
                        Log.d("zzzzzzzz", loginResponse.isSuccess()+"");

                        if (loginResponse.isSuccess()){
                            ClientModel client  = loginResponse.getData();
                            
                            Log.d("zzzzz", loginResponse.getMessage());
                            Log.d("zzzz", "onResponse: "+client.getUsername());
                            rememberUser(username,password,ckb_rememberme.isChecked());
                            Toast.makeText(Screen_login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            Gson gson = new Gson();
                            String json = gson.toJson(client);

                            Intent intent = new Intent(Screen_login.this, Screen_navigation.class);
                            intent.putExtra("loginUser",json);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Screen_login.this, "Error in API response: " + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            til_username.setError("Invalid Username");
                            til_password.setError("Invalid Password");
                        }
                    } else {
                        Toast.makeText(Screen_login.this, "Login Fail", Toast.LENGTH_SHORT).show();
                        Log.d("zzzzz", "onResponse: Login Failed");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("zzzzz", "onResponse: "+t.getMessage());

                }
            });
        }
    }
}