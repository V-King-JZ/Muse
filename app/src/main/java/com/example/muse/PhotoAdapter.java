package com.example.muse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context mContext;
    private List<Photo> mPhotoList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView photoView;
        TextView photoName;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView) view;
            photoView=view.findViewById(R.id.photo_view);
            photoName=view.findViewById(R.id.photo_name);
        }
    }
    public PhotoAdapter(List<Photo> photoList){
        mPhotoList=photoList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.photo_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Photo photo=mPhotoList.get(position);
                Intent intent=new Intent(mContext,PhotoActivity.class);
                intent.putExtra(PhotoActivity.PHOTO_ID,photo.getUri());
                intent.putExtra(PhotoActivity.PHOTO_Name,photo.getName());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo=mPhotoList.get(position);
        holder.photoName.setText(photo.getName());
        Glide.with(mContext).load(photo.getUri()).centerCrop().into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }
}
