package com.ttonline.vestman.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.Api.ApiService;
import com.ttonline.vestman.R;
import com.ttonline.vestman.databinding.ActivityScreenCartBinding;
import com.ttonline.vestman.databinding.ActivityScreenDetailProductBinding;
import com.ttonline.vestman.models.ProductModel;
import com.ttonline.vestman.models.ResMessage;
import com.ttonline.vestman.models.YourRequestClass;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen_detailProduct extends AppCompatActivity {
    ActivityScreenDetailProductBinding binding;

    private List<ProductModel> mListProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        binding= ActivityScreenDetailProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProductModel productModel = getIntent().getParcelableExtra("product");
        if (productModel != null) {
            List listsize=new ArrayList();
            listsize=productModel.getSizes();
            Log.d("id product : ", productModel.get_id());
            Log.d("size product : ", String.valueOf(listsize));
            Log.d("name product : ", productModel.getName_product());
            Log.d("quantity product : ", String.valueOf(productModel.getQuantity()));
            Log.d("quantitySold product : ", String.valueOf(productModel.getQuantitySold()));
            Log.d("price product : ", String.valueOf(productModel.getPrice()));
            Log.d("discount : ", String.valueOf(productModel.getId_discount()));
            binding.btnboxchat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Screen_detailProduct.this,Screen_chatbot.class);
                    intent.putExtra("id_Product", productModel.get_id());
                    startActivity(intent);
                }
            });
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                numbers.add(i);
            }
            // Tạo một ArrayAdapter để kết nối dữ liệu với Spinner
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbers);
            // Đặt kiểu giao diện cho Spinner khi nó được bung ra
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Đặt Adapter cho Spinner
            binding.spinnerQuantity.setAdapter(adapter);
            ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listsize);
            sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerSize.setAdapter(sizeAdapter);

            binding.color.setText("Mầu sắc: " + productModel.getColor());
            Picasso.get().load(productModel.getImages().get(0)).into(binding.imageView);
            binding.name.setText(productModel.getName_product());
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String total = format.format(productModel.getPrice());
            binding.price.setText(total);
            binding.detailProduct.setText(productModel.getDetail_product());
            binding.soluongdaban.setText("Số lượng dã bán: "+productModel.getQuantitySold());
            binding.soluong.setText("Số lượng còn lại: "+productModel.getQuantity());

            binding.btnCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            binding.btnAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        YourRequestClass request = new YourRequestClass();
                        request.id_product=productModel.get_id();
                        request.quantity=(Integer) binding.spinnerQuantity.getSelectedItem();
                        request.size= (String) binding.spinnerSize.getSelectedItem();
                        // Cấu hình dữ liệu sản phẩm cần thêm vào giỏ hàng trong request

                        ApiService.apiservice.insertToCart(getUserId(), request).enqueue(new Callback<ResMessage>() {
                            @Override
                            public void onResponse(Call<ResMessage> call, Response<ResMessage> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    // Xử lý phản hồi thành công, response.body() chứa dữ liệu giỏ hàng mới
                                    Intent intent = new Intent(Screen_detailProduct.this,Screen_cart.class);
                                    startActivity(intent);

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
                        Toast.makeText(Screen_detailProduct.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    /////////////////////////////
    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("user_id", "");
        Log.d("zzzz", "user id from product: "+userId);
        return userId;
    }
///////////////////////////////
}
