package com.example.level_3_middle.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.level_3_middle.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    final Context context;
    DatabaseReference reff;
    ArrayList<GetData> modelList;

    public DataAdapter(Context context, ArrayList<GetData> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetData data = modelList.get(position);
        Glide.with(context).load(data.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton likeBtn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById((R.id.imgCard));
            likeBtn = itemView.findViewById(R.id.likeBtn);

            likeBtn.setOnClickListener(view -> {

                int positionIndex = getAdapterPosition();
                String childDB = "id_" + (positionIndex + 1);
                reff = FirebaseDatabase.getInstance().getReference().child("Photo").child(childDB).child("like");

                if (modelList.get(positionIndex).getLike()) {
                    likeBtn.setBackgroundResource(R.drawable.ic_like_grey__24);
                    modelList.get(positionIndex).setLikeCheck(false);
                    reff.setValue(false);
                } else {
                    likeBtn.setBackgroundResource(R.drawable.ic_like_red_24);
                    modelList.get(positionIndex).setLikeCheck(true);
                    reff.setValue(true);
                }
            });
        }
    }

}
