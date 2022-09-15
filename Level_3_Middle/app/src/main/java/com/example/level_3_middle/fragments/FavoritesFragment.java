package com.example.level_3_middle.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.level_3_middle.classes.DataAdapterFavorites;
import com.example.level_3_middle.classes.GetData;
import com.example.level_3_middle.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    RecyclerView recView;
    ArrayList<GetData> dataModelFavorites = new ArrayList<>();
    DataAdapterFavorites dataAdapter;
    DatabaseReference reff;


    public FavoritesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_favorites, container, false);

        reff = FirebaseDatabase.getInstance().getReference().child("Photo");
        recView = myView.findViewById(R.id.recyclerViewFavorites);
        dataAdapter = new DataAdapterFavorites(getActivity(), dataModelFavorites);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }

        recView.setHasFixedSize(true);

        recView.setAdapter(dataAdapter);


        reff.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataModelFavorites.clear();
                int value = (int) dataSnapshot.getChildrenCount();
                for (int i = 1; i <= value; i++) {
                    String childBD = "id_" + i;
                    boolean likeCheck = dataSnapshot.child(childBD).child("like").getValue(Boolean.class);

                    String urlImg = dataSnapshot.child(childBD).child("imageUrl").getValue(String.class);
                    if (likeCheck) {
                        dataModelFavorites.add(new GetData(urlImg, likeCheck));
                    }
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return myView;
    }

}