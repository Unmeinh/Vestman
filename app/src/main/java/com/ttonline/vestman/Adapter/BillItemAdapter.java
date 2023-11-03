package com.ttonline.vestman.Adapter;

import android.app.Activity;
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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillItemModel;
import com.ttonline.vestman.models.BillModel;
import com.ttonline.vestman.models.IdProduct;
import com.ttonline.vestman.models.ProductModel;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttonline.vestman.screen.Screen_userinfo;

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder>{

    Context context;
    private List<BillItemModel> mListBillItem;

    public BillItemAdapter(Context context, List<BillItemModel> mListBillItem) {
        this.context = context;
        this.mListBillItem = mListBillItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillItemModel billItem = mListBillItem.get(position);
        holder.tv_order_item_size.setText("Size: "+billItem.getSize());
        holder.tv_order_item_qty.setText("Qty: "+billItem.getQuantity());

        IdProduct id_product = billItem.getId_product();

        holder.tv_order_item_name.setText(id_product.name_product);
        holder.tv_order_item_color.setText("Color: "+id_product.color);

        holder.tv_order_item_price.setText("Price: "+id_product.price);

        ArrayList listImg = id_product.images;
        if (listImg != null){
            Log.d("zzzz", "list img : "+listImg);
            try{
                Glide.with(context).load(listImg.get(0)).into(holder.img_avt);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }

    @Override
    public int getItemCount() {
        if(mListBillItem != null){
            return mListBillItem.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_order_item_name,tv_order_item_size,tv_order_item_qty,tv_order_item_color,tv_order_item_price;
        ImageView img_avt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_order_item_name = itemView.findViewById(R.id.tv_order_item_name);
            tv_order_item_size = itemView.findViewById(R.id.tv_order_item_size);
            tv_order_item_qty = itemView.findViewById(R.id.tv_order_item_qty);
            tv_order_item_color = itemView.findViewById(R.id.tv_order_item_color);
            tv_order_item_price = itemView.findViewById(R.id.tv_order_item_price);
            img_avt = itemView.findViewById(R.id.img_order_item_product);
        }
    }
}
