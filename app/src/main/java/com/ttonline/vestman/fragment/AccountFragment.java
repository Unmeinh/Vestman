package com.ttonline.vestman.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttonline.vestman.R;
import com.ttonline.vestman.screen.Screen_chatbot;
import com.ttonline.vestman.screen.Screen_resetpassword;
import com.ttonline.vestman.screen.Screen_userinfo;
import com.ttonline.vestman.screen.Screen_orderhistory;


public class AccountFragment extends Fragment {


    private TextView txtUserInfo,txtResetPass,txtLogout,txtChatbot,tv_OrderHistory;


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
        // Inflate the layout for this fragment
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

        txtUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Screen_userinfo.class));
            }
        });
        txtResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Screen_resetpassword.class));
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
                startActivity(new Intent(getContext(), Screen_orderhistory.class));
            }
        });

//        txtChatbot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog dialog = createDialog();
//                dialog.show();
//
//            }
//
//            AlertDialog createDialog(){
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("Confirm Logout");
//                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //log out here
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        createDialog().dismiss();
//                    }
//                });
//                return builder.create();
//            }
//        });


    }

//    private void callApiGetClient(){
//        ApiService.apiservice.getCilent().enqueue(new Callback<Root>() {
//            @Override
//            public void onResponse(Call<Root> call, Response<Root> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                } else {
//                    Toast.makeText(getContext(), "Error in response", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Root> call, Throwable t) {
//                Toast.makeText(getContext(), "lỗi account", Toast.LENGTH_SHORT).show();
//                Log.d("tttt", t.getMessage());
//            }
//        });
//    }
}