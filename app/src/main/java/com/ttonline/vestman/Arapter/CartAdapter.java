package com.ttonline.vestman.Arapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.IdProduct;
import com.ttonline.vestman.models.ResMessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Datum> list_cart;
    private Context context;

    public CartAdapter(List<Datum> list_cart) {
        this.list_cart = list_cart;
    }

    public CartAdapter(List<Datum> list_cart, Context context) {
        this.list_cart = list_cart;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Datum datum=list_cart.get(position);
        if (datum == null || datum.getId_product() == null) {
            return;
        }
        IdProduct idProduct = datum.getId_product();

        holder.name.setText(idProduct.getName_product());
        holder.gia.setText("Price: " + String.valueOf(idProduct.getPrice()) + " VNĐ");
        holder.color.setText("Color: " + idProduct.getColor());
        holder.size.setText("Size: "+"56");

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "xóa dữ liệu", Toast.LENGTH_SHORT).show();
                //xử lý xóa dữ liệu
                callApiDeleteCartItem(datum._id);
                Log.d("TAGzzzzz", "onClick: "+datum.get_id());
                Log.d("TAGtttt", "onClick: "+list_cart.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list_cart != null){
            return list_cart.size();
        }
        return 0;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageButton btn_delete;
        private TextView name,gia,color,size;
        private CardView cardView;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_product);
            name=itemView.findViewById(R.id.name);
            gia=itemView.findViewById(R.id.gia);
            color=itemView.findViewById(R.id.color);
            size=itemView.findViewById(R.id.size);
            cardView=itemView.findViewById(R.id.cardv);
            btn_delete=itemView.findViewById(R.id.btn_delete);

        }
    }
    private void callApiDeleteCartItem(String idCart) {
        ApiService.apiservice.deleteToCart(idCart).enqueue(new Callback<ResMessage>() {
            @Override
            public void onResponse(Call<ResMessage> call, Response<ResMessage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xóa thành công
                    // Thực hiện các hành động cần thiết sau khi xóa
                    notifyDataSetChanged();
                    Log.d("zzzzzz", "Xóa thành công. Message: " + response.message() + " Code: " + response.code());
                } else {
                    // Xóa không thành công hoặc có lỗi
                    // Xử lý lỗi hoặc thông báo lỗi cho người dùng
                    Log.d("zzzzzz", "Lỗi xóa. Message: " + response.message() + " Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResMessage> call, Throwable t) {
                // Xử lý lỗi trong quá trình gọi API (ví dụ: mất kết nối)
                Log.d("zzzzzz", "Lỗi trong quá trình gọi API: " + t.getMessage());
            }
        });
    }
}
