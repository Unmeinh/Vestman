package com.ttonline.vestman.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.fragment.IncompletedBillFragment;
import com.ttonline.vestman.models.BillModel;
import com.ttonline.vestman.models.RootBill;
import com.ttonline.vestman.screen.Screen_orderdetail;
import com.ttonline.vestman.screen.Screen_userinfo;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    Context context;
    private final List<BillModel> mListBill;
    private AdapterView.OnItemClickListener listener;

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
            status = "Unconfimred";
            holder.btn_order_confirm.setVisibility(View.GONE);
        } else if (bill.getStatus() == 0){
            status = "Delivering";
            holder.btn_order_confirm.setVisibility(View.GONE);
        } else if (bill.getStatus() == 1){
            ///
            status = "Delivered";
            holder.btn_order_confirm.setVisibility(View.VISIBLE);
        } else if (bill.getStatus() == 2){
            status = "Received";
            holder.btn_order_confirm.setVisibility(View.GONE);
        }

        holder.tv_order_status.setText("Status: "+status);

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String total = format.format(bill.getTotal());
        holder.tv_orderPrice.setText(total);

        //list
        holder.rcv_order_item.setLayoutManager(new LinearLayoutManager(context));
        holder.rcv_order_item.setAdapter(new BillItemAdapter(context,bill.getArr_product()));

        holder.btn_order_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //thay đổi trạng thái đơn hàng
//                Log.d("zzzzz_order detail", "onClick: "+bill.get_id());

                AlertDialog dialog = createDialog();
                dialog.show();

            }

            AlertDialog createDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm Order");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callApiConfirmOrder(bill.get_id());

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
        holder.cv_order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"order clicked: "+ bill.get_id(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Screen_orderdetail.class);
                Gson gson = new Gson();
                String json = gson.toJson(bill);
                intent.putExtra("billDetail", json);
                context.startActivity(intent);

            }
        });



    }

    private void callApiConfirmOrder(String idOrder) {
        ApiService.apiservice.confirmRecive(idOrder).enqueue(new Callback<RootBill>() {
            @Override
            public void onResponse(Call<RootBill> call, Response<RootBill> response) {
                if (response.isSuccessful()){
                    RootBill rootBill = response.body();
                    if (rootBill.isSuccess()){
                        Log.d("zzzz_bill", "onResponse: "+rootBill.getMessage());
                        notifyDataSetChanged();
                    }
                }else {
                    Log.d("zzzz_bill", "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<RootBill> call, Throwable t) {
                Log.d("zzzzzzz_bill", "onResponse: "+t.getMessage());

            }
        });
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
        Button btn_order_confirm;
        CardView cv_order_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderDate = itemView.findViewById(R.id.tv_order_date);
            tv_orderPrice = itemView.findViewById(R.id.tv_order_price);
            rcv_order_item = itemView.findViewById(R.id.rcv_order_item);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);
            btn_order_confirm = itemView.findViewById(R.id.btn_order_comfirm);
            cv_order_item = itemView.findViewById(R.id.cv_order_item);
        }
    }
}
