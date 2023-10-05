package com.ttonline.vestman.Adapter;//package com.ttonline.vestman.Arapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ttonline.vestman.R;
//import com.ttonline.vestman.models.ProductModel;
//
//import java.util.List;
//
//public class ProductArapter extends RecyclerView.Adapter<ProductArapter.ProductViewHolder> {
//    private List<ProductModel> mListProduct;
//    private RecyclerView rcvProduct;
//    Context context;
//
//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
//
//    public ProductArapter(List<ProductModel> mListProduct, Context context) {
//        this.mListProduct = mListProduct;
//        this.context = context;
//    }
//
//
//
//    @NonNull
//    @Override
//    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
//        return new ProductViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//        ProductModel productModel=mListProduct.get(position);
//        if (productModel==null){
//            return;
//        }
//        holder.tv_name.setText(productModel.getName_product());
//        holder.tv_gia.setText(String.valueOf(productModel.getPrice()));
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mListProduct != null){
//            return mListProduct.size();
//        }
//        return 0;
//    }
//
//    public class ProductViewHolder extends RecyclerView.ViewHolder{
//        private  TextView tv_name,tv_gia;
//        private ImageView ing_product;
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tv_name=itemView.findViewById(R.id.tv_name);
//            tv_gia=itemView.findViewById(R.id.tv_gia);
//
//        }
//    }
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ProductModel;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<ProductModel> mListProduct;
    private Context context;

    public ProductAdapter(List<ProductModel> mListProduct, Context context) {
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductModel productModel = (ProductModel) getItem(position);
        if (productModel != null) {
            holder.tv_name.setText(productModel.getName_product());
            holder.tv_gia.setText(String.valueOf(productModel.getPrice()));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name, tv_gia;
    }
}
