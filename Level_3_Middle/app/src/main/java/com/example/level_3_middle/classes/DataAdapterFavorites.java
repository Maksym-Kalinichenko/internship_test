package com.example.level_3_middle.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.level_3_middle.R;

import java.util.ArrayList;

public class DataAdapterFavorites extends RecyclerView.Adapter<DataAdapterFavorites.ViewHolder> {

    final Context context;
    ArrayList<GetData> newList;

    public DataAdapterFavorites(Context context, ArrayList<GetData> modelList) {
        this.context = context;
        this.newList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetData data = newList.get(position);
        Glide.with(context).load(data.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById((R.id.imgCardFavorite));

        }
    }

}
