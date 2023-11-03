package com.ttonline.vestman.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttonline.vestman.R;
import com.ttonline.vestman.models.BillModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    Context context;
    private final List<BillModel> mListBill;

    public BillAdapter(Context context, List<BillModel> mListBill) {
        this.context = context;
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

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            TimeZone timeZone = TimeZone.getTimeZone("ICT");
            inputFormat.setTimeZone(timeZone);
            outputFormat.setTimeZone(timeZone);

            Date date = inputFormat.parse(bill.getCreated_at());
            String formattedDate = outputFormat.format(date);

            holder.tv_orderDate.setText("Date: "+formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String status = "";
        if (bill.getStatus() == -1){
            status = "Chưa xác nhận đơn hàng";
        } else if (bill.getStatus() == 0){
            status = "Đang giao hàng";
        } else if (bill.getStatus() == 1){
            status = "Đã giao hàng";
        } else if (bill.getStatus() == 2){
            status = "Đã nhận hàng";
        }

        holder.tv_order_status.setText("Status: "+status);
        holder.tv_orderPrice.setText(""+bill.getTotal());

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
        TextView tv_orderDate,tv_orderPrice,tv_order_status;
        RecyclerView rcv_order_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderDate = itemView.findViewById(R.id.tv_order_date);
            tv_orderPrice = itemView.findViewById(R.id.tv_order_price);
            rcv_order_item = itemView.findViewById(R.id.rcv_order_item);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);
        }
    }
}
