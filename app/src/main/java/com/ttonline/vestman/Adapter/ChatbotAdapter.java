package com.ttonline.vestman.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ChatModel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatbotAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatModel> chatModelArrayList;
    private Context context;


    public ChatbotAdapter(ArrayList<ChatModel> chatModelArrayList, Context context) {
        this.chatModelArrayList = chatModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_user,parent,false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_bot,parent,false);
                return new BotViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = chatModelArrayList.get(position);
        switch (chatModel.getSender()){
            case "user":
                ((UserViewHolder)holder).tv_user.setText(chatModel.getMessage());
                break;
            case "bot":
                ((BotViewHolder) holder).img_botMsg.setVisibility(View.GONE);

                String[] rep = chatModel.getMessage().toString().split("http");
                String repWithOutImg = rep[0];
                String imgUrl = extractUrls(chatModel.getMessage().toString());
                if (imgUrl != null){
                    ((BotViewHolder)holder).tv_bot.setText(repWithOutImg);
                    Glide.with(context).load(imgUrl).into(((BotViewHolder)holder).img_botMsg);
                    ((BotViewHolder) holder).img_botMsg.setVisibility(View.VISIBLE);

                }else {
                    ((BotViewHolder)holder).tv_bot.setText(chatModel.getMessage().toString());

                }
                break;
        }
    }

    public static String extractUrls(String text) {
        String urlRegex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            String url = text.substring(urlMatcher.start(0), urlMatcher.end(0));
            System.out.println(url);
            return url;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatModelArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatModelArrayList.size();

    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tv_user;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user = itemView.findViewById(R.id.tv_usermsg);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView tv_bot;
        ImageView img_botMsg;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bot = itemView.findViewById(R.id.tv_botmsg);
            img_botMsg = itemView.findViewById(R.id.iv_botmsg);

        }
    }

}
