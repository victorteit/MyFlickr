package ca.teitandroid.myflickr.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.teitandroid.myflickr.R;
import ca.teitandroid.myflickr.flickrService.FlickrAPI;
import ca.teitandroid.myflickr.model.Photo;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.MyViewHolder>{
    Photo photo;
    Context context;
    ArrayList<Photo> photoList;
    OnPhotoListener onPhotoListener;


    public PhotoRecyclerAdapter(Context context, ArrayList<Photo> photoList, OnPhotoListener onPhotoListener){
        this.context = context;
        this.photoList = photoList;
        this.onPhotoListener = onPhotoListener;
    }

    @NonNull
    @Override
    public PhotoRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_grid, parent, false);
        return new PhotoRecyclerAdapter.MyViewHolder(view, onPhotoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoRecyclerAdapter.MyViewHolder holder, int position) {
        photo = photoList.get(position);
        String url = photo.getUrlPhoto();
        if (holder.imageView != null && url != null) {
            Picasso.get().load(url).into(holder.imageView);
        } else {
            Log.d("PhotoRecyclerAdapter", "ImageView or url is null");
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        OnPhotoListener onPhotoListener;
        public MyViewHolder(@NonNull View itemView, OnPhotoListener onPhotoListener) {
            super(itemView);
            this.onPhotoListener = onPhotoListener;
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           // RecyclerView recyclerView = (RecyclerView) v.getParent();
            onPhotoListener.onPhotoClick(getAdapterPosition());
        }
    }
    public interface OnPhotoListener {
        void onPhotoClick(int position);
    }
}
