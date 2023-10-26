package com.ttonline.vestman.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ClientModel;
import com.ttonline.vestman.models.SignupResponse;
import com.ttonline.vestman.screen.Screen_chatbot;
import com.ttonline.vestman.screen.Screen_login;
import com.ttonline.vestman.screen.Screen_navigation;
import com.ttonline.vestman.screen.Screen_resetpassword;
import com.ttonline.vestman.screen.Screen_userinfo;
import com.ttonline.vestman.screen.Screen_orderhistory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragment extends Fragment {


    private TextView txtUserInfo,txtResetPass,txtLogout,txtChatbot,tv_OrderHistory,tv_username;
    private ImageView img_user;
    public static final String SHARED_PREFS = "sharedPrefs";

    private String userId;


    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String user = "";

        Bundle args = getArguments();
        if (args!= null){
            user = args.getString("userInfo");
        }
        Gson gson = new Gson();
        ClientModel client = gson.fromJson(user, ClientModel.class);
        userId = client.get_id();
        Log.d("zzzczzz", "onCreateView: "+userId);
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtUserInfo = view.findViewById(R.id.txt_userinfo);
        txtLogout = view.findViewById(R.id.txt_logout);
        txtResetPass = view.findViewById(R.id.txt_resetpass);
        txtChatbot = view.findViewById(R.id.txt_chatbot);
        tv_OrderHistory = view.findViewById(R.id.txt_orderhistory);
        img_user = view.findViewById(R.id.img_account);
        tv_username = view.findViewById(R.id.tv_username);

        ApiService.apiservice.getUserDetail(userId).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    SignupResponse signupResponse = response.body();
                    if (signupResponse.isSuccess()){
                        ClientModel client = signupResponse.getData();
                        tv_username.setText(client.getFull_name());

                        //ảnh
                        Glide.with(getContext()).load(client.getAvatar()).into(img_user);

                    }else{
                        Log.d("zzzz", "userinfo: "+signupResponse.getMessage());
                    }
                }else{
                    Toast.makeText(getContext(), "Error in response", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("tttt", t.getMessage());
            }
        });

        txtUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Screen_userinfo.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        txtResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Screen_resetpassword.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        txtChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Screen_chatbot.class));
            }
        });
        tv_OrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Screen_orderhistory.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog dialog = createDialog();
                dialog.show();

            }

            AlertDialog createDialog(){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Confirm Logout");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getContext(), Screen_login.class));
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear().commit();
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
}