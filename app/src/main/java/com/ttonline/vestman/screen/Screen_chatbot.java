package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ttonline.vestman.Adapter.ChatbotAdapter;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ChatModel;
import com.ttonline.vestman.models.ChatbotResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_chatbot extends AppCompatActivity {
    private ImageButton btn_back;
    private RecyclerView rcv_chatbot;
    private Button btn_q1,btn_q2,btn_q3;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatModel> chatModelArrayList;
    private ChatbotAdapter chatbotAdapter;
    private String id_product = "650c2b36f17c8322b62e50de";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_chatbot);
        btn_back = findViewById(R.id.btn_chatbot_back);
        rcv_chatbot = findViewById(R.id.rv_chatbot);
        btn_q1 = findViewById(R.id.btn_quest_1);
        btn_q2 = findViewById(R.id.btn_quest_2);
        btn_q3 = findViewById(R.id.btn_quest_3);

        chatModelArrayList = new ArrayList<>();
        chatbotAdapter = new ChatbotAdapter(chatModelArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcv_chatbot.setLayoutManager(manager);
        rcv_chatbot.setAdapter(chatbotAdapter);

        callApiChatbotQuest();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(btn_q1.getText().toString());
            }
        });
        btn_q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(btn_q2.getText().toString());
            }
        });
        btn_q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(btn_q3.getText().toString());
            }
        });

    }
    private void getResponse(String message){
        chatModelArrayList.add(new ChatModel(message,USER_KEY));
        chatbotAdapter.notifyDataSetChanged();

        callApiChatbotRep(message);

    }

    private void callApiChatbotRep(String msg) {
        ApiService.apiservice.getChatbot(id_product).enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    ChatbotResponse chatbotResponse = response.body();
                    if (chatbotResponse.isSuccess()){
                        Log.d("zzzz", "chatbot: "+ chatbotResponse.getMessage());

                        ArrayList listQuest = chatbotResponse.getData().getQuestions();
                        ArrayList listRep = chatbotResponse.getData().getReplies();

                        if (msg.equals(listQuest.get(0).toString())){
                            chatModelArrayList.add(new ChatModel(listRep.get(0).toString(),BOT_KEY));
                        } else if (msg.equals(listQuest.get(1).toString())){
                            chatModelArrayList.add(new ChatModel(listRep.get(1).toString(),BOT_KEY));
                        } else if (msg.equals(listQuest.get(2).toString())){
                            chatModelArrayList.add(new ChatModel(listRep.get(2).toString(),BOT_KEY));
                        }

                        chatbotAdapter.notifyDataSetChanged();

                    }else {
                        Log.d("zzzz", "chatbot fail: "+ chatbotResponse.getMessage());
                    }
                } else {
                    Log.d("zzzz", "chatbot fail: "+ response.message());

                }
            }

            @Override
            public void onFailure(Call<ChatbotResponse> call, Throwable t) {
                Log.d("zzzz", "chatbot call fail: "+ t.getMessage());

            }
        });

    }

    private void callApiChatbotQuest() {
        ApiService.apiservice.getChatbot(id_product).enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    ChatbotResponse chatbotResponse = response.body();
                    if (chatbotResponse.isSuccess()){
                        ArrayList listQuest = chatbotResponse.getData().getQuestions();
                        ArrayList listRep = chatbotResponse.getData().getReplies();
                        Log.d("zzzz", "chatbot questions: "+ listQuest);
                        btn_q1.setText(listQuest.get(0).toString());
                        btn_q2.setText(listQuest.get(1).toString());
                        btn_q3.setText(listQuest.get(2).toString());


                        Log.d("zzzz", "chatbot get question: "+ chatbotResponse.getMessage());

                    }else {
                        Log.d("zzzz", "chatbot get question fail: "+ chatbotResponse.getMessage());
                    }
                } else {
                    Log.d("zzzz", "chatbot get question fail: "+ response.message());

                }
            }

            @Override
            public void onFailure(Call<ChatbotResponse> call, Throwable t) {
                Log.d("zzzz", "chatbot get question call fail: "+ t.getMessage());

            }
        });

    }
}