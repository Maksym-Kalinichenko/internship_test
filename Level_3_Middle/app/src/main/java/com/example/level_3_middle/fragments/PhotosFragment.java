package com.example.level_3_middle.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.level_3_middle.classes.DataAdapter;
import com.example.level_3_middle.classes.GetData;
import com.example.level_3_middle.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotosFragment extends Fragment {

    RecyclerView recView;
    ArrayList<GetData> dataModelArrayList = new ArrayList<>();
    DataAdapter dataAdapter;
    String url = "https://zoo-animal-api.herokuapp.com/animals/rand/10";
    DatabaseReference reff;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 0, limit = 1, sum = 1;

    public PhotosFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_photos, container, false);

        reff = FirebaseDatabase.getInstance().getReference().child("Photo");
        recView = myView.findViewById(R.id.recyclerViewPhotos);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }

        recView.setHasFixedSize(true);

        nestedScrollView = myView.findViewById(R.id.nestedScroll);
        progressBar = myView.findViewById(R.id.progress_bar);
        getDataFromAPI(page, limit);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                page++;
                progressBar.setVisibility(View.VISIBLE);
                sum += 10;
                getDataFromAPI(page, limit);
            }
        });

        return myView;
    }

    public int plusRes() {
        return sum;
    }

    private void getDataFromAPI(int page, int limit) {
        if (page > limit) {
            Toast.makeText(getActivity(), "That's all the data..", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            int sum = plusRes();
            int k = response.length();
            for (int i = 0; i < k; i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    String urlImg = obj.getString("image_link");
                    dataModelArrayList.add(new GetData(urlImg, false));
                    String newChild = "id_" + (i + sum);
                    reff.child(newChild).setValue(new GetData(urlImg, false));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dataAdapter = new DataAdapter(getActivity(), dataModelArrayList);
                recView.setAdapter(dataAdapter);

            }
        }, error -> Toast.makeText(getActivity(), "Fail to get data..", Toast.LENGTH_SHORT).show());
        queue.add(jsonArrayRequest);

    }

}