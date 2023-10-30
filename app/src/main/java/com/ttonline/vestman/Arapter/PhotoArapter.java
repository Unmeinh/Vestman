package com.ttonline.vestman.Arapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ttonline.vestman.R;
import com.ttonline.vestman.models.ModelSlideShow;
import com.ttonline.vestman.models.Photo;
import com.ttonline.vestman.screen.Screen_hot_deal;
import com.ttonline.vestman.screen.Screen_navigation;

import java.util.Calendar;
import java.util.List;

public class PhotoArapter extends RecyclerView.Adapter<PhotoArapter.PhotoViewHolder>{
    private List<ModelSlideShow> mlistPhoto;
    Context context;

    public PhotoArapter(Context context) {
        this.context = context;
    }

    public PhotoArapter(List<ModelSlideShow> mlistPhoto, Context context) {
        this.mlistPhoto = mlistPhoto;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        ModelSlideShow modelSlideShow=mlistPhoto.get(position);
        if (modelSlideShow==null){
            return;
        }
        //// gan du lieu vao item
        Picasso.get().load(modelSlideShow.getThumbnailImage()).into(holder.img_photo);
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Screen_hot_deal.class);
                intent.putExtra("modelSlideShow", modelSlideShow);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlistPhoto !=null){
            return mlistPhoto.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_photo;
        private CardView cardView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            img_photo=itemView.findViewById(R.id.img_photo);
            cardView=itemView.findViewById(R.id.Cardview);
        }
    }
}
