package com.ttonline.vestman.Arapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.IdProduct;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.Root_cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductArapter extends BaseAdapter {
    private List<ProductModel> mListProduct;
    private Context context;

    public ProductArapter(List<ProductModel> mListProduct, Context context) {
        this.mListProduct = mListProduct;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mListProduct != null && position >= 0 && position < mListProduct.size()) {
            return mListProduct.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_gia = convertView.findViewById(R.id.tv_gia);
            holder.img_product = convertView.findViewById(R.id.img_product);
            holder.cardView = convertView.findViewById(R.id.cardview);
            holder.btn_cart = convertView.findViewById(R.id.btn_cart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductModel productModel = (ProductModel) getItem(position);
        if (productModel != null) {
            holder.tv_name.setText(productModel.getName_product());
            holder.tv_gia.setText(String.valueOf(productModel.getPrice()));
            Picasso.get().load(productModel.getImages().get(0)).into(holder.img_product);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_detail_product, null);
                    builder.setView(view);
                    TextView name = view.findViewById(R.id.name);
                    TextView price = view.findViewById(R.id.price);
                    TextView description = view.findViewById(R.id.detail_product);
                    TextView color = view.findViewById(R.id.color);
                    color.setText("Mầu sắc: " + productModel.getColor());
                    ImageView imgview = view.findViewById(R.id.imageView);
                    Picasso.get().load(productModel.getImages().get(0)).into(imgview);
                    name.setText(productModel.getName_product());
                    price.setText(String.valueOf(productModel.getPrice()) + " VNĐ");
                    description.setText(productModel.getDetail_product());
                    Dialog dialog1 = builder.create();
                    Window window = dialog1.getWindow();
                    dialog1.show();
                }
            });

            holder.btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        IdProduct idProduct = new IdProduct();
                        idProduct.set_id(productModel.get_id());

                        Log.d("TAG", productModel.get_id());
                        callApiAddToCart("650c27f6cbe42ee7d05816d8", idProduct, "3", "xl");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return convertView;
    }

    private void callApiAddToCart(String idClient, IdProduct idProduct, String quantity, String size) {
        try {
            Datum datum = new Datum();
            datum.setId_client(idClient);
            datum.setId_product(idProduct);
            datum.setQuantity(quantity);
            datum.setSize(size);

            ApiService.apiservice.addToCart(idClient, datum).enqueue(new Callback<Root_cart>() {
                @Override
                public void onResponse(Call<Root_cart> call, Response<Root_cart> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Root_cart rootCart = response.body();
                        if (rootCart.isSuccess()) {
                            // Xử lý thành công khi thêm vào giỏ hàng
                            Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Lỗi trong phản hồi API: " + rootCart.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Lỗi trong phản hồi", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Root_cart> call, Throwable t) {
                    Toast.makeText(context, "Lỗi kết nối hoặc lỗi khác: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("zzzzzzzz", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    static class ViewHolder {
        TextView tv_name, tv_gia;
        ImageView img_product;
        ImageButton btn_cart;
        CardView cardView;
    }
}
