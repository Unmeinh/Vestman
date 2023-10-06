package com.ttonline.vestman.Arapter;//package com.ttonline.vestman.Arapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ProductModel;

import java.util.List;

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
            holder.img_product= convertView.findViewById(R.id.img_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductModel productModel = (ProductModel) getItem(position);
        if (productModel != null) {
            holder.tv_name.setText(productModel.getName_product());
            holder.tv_gia.setText(String.valueOf(productModel.getPrice()));
            Picasso.get().load(productModel.getImages().get(0)).into(holder.img_product);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name, tv_gia;
        ImageView img_product;
    }
}
