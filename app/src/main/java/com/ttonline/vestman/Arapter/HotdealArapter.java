package com.ttonline.vestman.Arapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.ResMessage;
import com.ttonline.vestman.models.YourRequestClass;
import com.ttonline.vestman.screen.Screen_cart;
import com.ttonline.vestman.screen.Screen_detailProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotdealArapter extends BaseAdapter {
    private List<ProductModel> mListProduct;
    private Context context;

    public HotdealArapter(List<ProductModel> mListProduct, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hotdeal_product, parent, false);
            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_gia = convertView.findViewById(R.id.tv_gia);
            holder.img_product = convertView.findViewById(R.id.img_product);
            holder.cardView = convertView.findViewById(R.id.cardv_hotdeal);
            holder.btn_cart = convertView.findViewById(R.id.btn_cart);
            holder.tv_gia_cu=convertView.findViewById(R.id.tv_gia_cu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductModel productModel = (ProductModel) getItem(position);
        if (productModel != null) {
            int gia= Integer.parseInt(String.valueOf(productModel.getPrice()));
            holder.tv_name.setText(productModel.getName_product());
            holder.tv_gia_cu.setText(String.valueOf(productModel.getPrice()));
            holder.tv_gia.setText(String.valueOf(gia-100000));
            Picasso.get().load(productModel.getImages().get(0)).into(holder.img_product);



            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, Screen_detailProduct.class);
                    intent.putExtra("product", productModel);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }


    static class ViewHolder {
        TextView tv_name, tv_gia, tv_gia_cu;
        ImageView img_product;
        ImageButton btn_cart;
        CardView cardView;
    }
}
