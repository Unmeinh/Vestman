package com.ttonline.vestman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillModel;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    Context context;
    private final List<BillModel> mListBill;

    public BillAdapter(List<BillModel> mListBill) {
        this.mListBill = mListBill;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillModel bill = mListBill.get(position);
        holder.tv_orderName.setText("Order: "+bill.getId_client());
        holder.tv_orderDate.setText("Date: "+bill.getCreate_at());
        holder.tv_orderPrice.setText("Total: "+bill.getTotal());

        //list
        holder.rcv_order_item.setLayoutManager(new LinearLayoutManager(context));
        holder.rcv_order_item.setAdapter(new BillItemAdapter(context,bill.getArr_product()));

    }

    @Override
    public int getItemCount() {
        if(mListBill != null){
            return mListBill.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_orderName,tv_orderDate,tv_orderPrice;
        RecyclerView rcv_order_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderName = itemView.findViewById(R.id.tv_order_name);
            tv_orderDate = itemView.findViewById(R.id.tv_order_date);
            tv_orderPrice = itemView.findViewById(R.id.tv_order_price);
            rcv_order_item = itemView.findViewById(R.id.rcv_order_item);
        }
    }
}
