package com.example.level_1_senior;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class FragmentCounting extends Fragment implements View.OnClickListener {

    Button addBtn;
    TextView resultView;
    int i;
    Communicator communicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            i = 0;
        } else {
            i = savedInstanceState.getInt("i", 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_counting, container, false);

        addBtn = myView.findViewById(R.id.fragmentBtn);
        communicator = (Communicator) getActivity();
        resultView = myView.findViewById(R.id.fragmentResult);
        addBtn.setOnClickListener(this);
        resultView.setText(String.valueOf(i));

        return myView;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragmentBtn) {
            i++;
            resultView.setText(String.valueOf(i));
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("i", i);
    }

    protected void sendData() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        communicator.getData(i);
    }

}