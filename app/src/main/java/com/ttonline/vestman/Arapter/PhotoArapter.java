package com.ttonline.vestman.Arapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ttonline.vestman.R;
import com.ttonline.vestman.models.Photo;

import java.util.List;

public class PhotoArapter extends RecyclerView.Adapter<PhotoArapter.PhotoViewHolder>{
    private List<Photo> mlistPhoto;

    public PhotoArapter(List<Photo> mlistPhoto) {
        this.mlistPhoto = mlistPhoto;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo=mlistPhoto.get(position);
        if (photo==null){
            return;
        }
        holder.img_photo.setImageResource(photo.getResourceId());
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
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            img_photo=itemView.findViewById(R.id.img_photo);
        }
    }
}
