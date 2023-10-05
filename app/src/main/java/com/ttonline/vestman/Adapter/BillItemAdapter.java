package com.ttonline.vestman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillItemModel;

import java.util.List;

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
        holder.tv_order_item_name.setText("Product ID: "+billItem.getId_product());
        holder.tv_order_item_size.setText("Size: "+billItem.getSize());
        holder.tv_order_item_qty.setText("Qty: "+billItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        if(mListBillItem != null){
            return mListBillItem.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_order_item_name,tv_order_item_size,tv_order_item_qty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_order_item_name = itemView.findViewById(R.id.tv_order_item_name);
            tv_order_item_size = itemView.findViewById(R.id.tv_order_item_size);
            tv_order_item_qty = itemView.findViewById(R.id.tv_order_item_qty);
        }
    }
}
