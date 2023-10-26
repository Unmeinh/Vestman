package com.ttonline.vestman.Arapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.IdProduct;
import com.ttonline.vestman.models.ResMessage;
import com.ttonline.vestman.screen.Screen_cart;

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
        Picasso.get().load(idProduct.getImages().get(0)).into(holder.imageView);
        holder.name.setText(idProduct.getName_product());
        holder.gia.setText("Price: " + String.valueOf(idProduct.getPrice()) + " VNĐ");
        holder.color.setText("Color: " + idProduct.getColor());
        holder.size.setText("Size: "+datum.getSize());
        holder.quantity.setText("Quantity : "+datum.getQuantity());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa mục này khỏi giỏ hàng?");

                // Thêm nút "Xác nhận"
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa mục khi người dùng xác nhận
                        callApiDeleteCartItem(datum._id);
                        // Cập nhật danh sách giỏ hàng và thông báo
                        list_cart.remove(datum);
                        notifyDataSetChanged();
                    }
                });

                // Thêm nút "Hủy bỏ"
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại nếu người dùng hủy bỏ
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị hộp thoại
                AlertDialog dialog = builder.create();
                dialog.show();
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
        private TextView name,gia,color,size,quantity;
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
            quantity=itemView.findViewById(R.id.quntity);

        }
    }
    private void callApiDeleteCartItem(String idCart) {
        ApiService.apiservice.deleteToCart(idCart).enqueue(new Callback<ResMessage>() {
            @Override
            public void onResponse(Call<ResMessage> call, Response<ResMessage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xóa thành công
                    // Thực hiện các hành động cần thiết sau khi xóa
                    CartAdapter cartAdapter = new CartAdapter(list_cart, context);
                    cartAdapter.notifyDataSetChanged();

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
