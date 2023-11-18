package com.ttonline.vestman.Arapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.ttonline.vestman.models.Datum;
import com.ttonline.vestman.models.IdProduct;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.ResMessage;
import com.ttonline.vestman.models.Root_cart;
import com.ttonline.vestman.models.YourRequestClass;
import com.ttonline.vestman.screen.Screen_cart;
import com.ttonline.vestman.screen.Screen_detailProduct;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String total = format.format(productModel.getPrice());
            holder.tv_gia.setText(total);
            Picasso.get().load(productModel.getImages().get(0)).into(holder.img_product);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, Screen_detailProduct.class);
                    intent.putExtra("product", productModel);
                    context.startActivity(intent);
                }
            });


            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_detail_product, null);
                    builder.setView(view);
                    TextView name = view.findViewById(R.id.name);
                    TextView price = view.findViewById(R.id.price);
                    TextView description = view.findViewById(R.id.detail_product);
                    TextView color = view.findViewById(R.id.color);
                    ImageButton btnadd=view.findViewById(R.id.btn_addCart);
                    ImageButton btnback=view.findViewById(R.id.btn_cancle);
                    // spinner size
                    Spinner spinner_size=view.findViewById(R.id.spinner_size);
                    Log.d("TAG spinner size", String.valueOf(productModel.getSizes()));
                    List listsize=new ArrayList();
                    listsize=productModel.getSizes();
                    ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,listsize);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_size.setAdapter(sizeAdapter);
                    // spinner quantity
                    Spinner spinner_quantity=view.findViewById(R.id.spinner_quantity);
                    List<Integer> numbers = new ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                        numbers.add(i);
                    }
                    // Tạo một ArrayAdapter để kết nối dữ liệu với Spinner
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, numbers);

                    // Đặt kiểu giao diện cho Spinner khi nó được bung ra
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Đặt Adapter cho Spinner
                    spinner_quantity.setAdapter(adapter);

                    color.setText("Mầu sắc: " + productModel.getColor());
                    ImageView imgview = view.findViewById(R.id.imageView);
                    Picasso.get().load(productModel.getImages().get(0)).into(imgview);
                    name.setText(productModel.getName_product());
                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    String total = format.format(productModel.getPrice());
                    price.setText(total);
                    description.setText(productModel.getDetail_product());
                    Dialog dialog1 = builder.create();
                    btnback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });
                    btnadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                YourRequestClass request = new YourRequestClass();
                                request.id_product=productModel.get_id();
                                request.quantity=(Integer) spinner_quantity.getSelectedItem();
                                request.size= (String) spinner_size.getSelectedItem();
                                // Cấu hình dữ liệu sản phẩm cần thêm vào giỏ hàng trong request

                                SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                String userId = preferences.getString("user_id", "");
                                Log.d("zzzz", "user id from product: "+userId);
                                ApiService.apiservice.insertToCart(userId, request).enqueue(new Callback<ResMessage>() {
                                    @Override
                                    public void onResponse(Call<ResMessage> call, Response<ResMessage> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            // Xử lý phản hồi thành công, response.body() chứa dữ liệu giỏ hàng mới
                                            Intent intent = new Intent(context,Screen_cart.class);
                                            context.startActivity(intent);
                                            dialog1.dismiss();

                                            // Thực hiện các thao tác sau khi thêm sản phẩm vào giỏ hàng thành công
                                        } else {
                                            // Xử lý lỗi hoặc hiển thị thông báo nếu không thành công
                                            Log.d("Lỗi Thêm vào giỏ hàng", response.message() + response.code());
                                            // Bạn cũng có thể hiển thị thông báo lỗi ở đây
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResMessage> call, Throwable t) {
                                        // Xử lý lỗi mạng hoặc lỗi khác
                                        Log.e("Lỗi Thêm vào giỏ hàng", t.getMessage());
                                        // Bạn cũng có thể hiển thị thông báo lỗi ở đây
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog1.show();
                    return false;
                }
            });


            holder.btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return convertView;
    }


    static class ViewHolder {
        TextView tv_name, tv_gia;
        ImageView img_product;
        ImageButton btn_cart;
        CardView cardView;
    }
}
