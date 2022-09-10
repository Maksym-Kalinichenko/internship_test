package com.example.level_1_senior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Communicator, View.OnClickListener {
    Button btnFragment;
    TextView viewRes;
    ConstraintLayout mainContent;
    FragmentContainerView fragmentView;
    private boolean isVisible = false;
    FragmentCounting fragment;
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment = (FragmentCounting) getSupportFragmentManager().findFragmentById(R.id.fragmentScreen);
        setContentView(R.layout.activity_main);
        viewRes = findViewById(R.id.resultView);
        btnFragment = findViewById(R.id.btnMain);
        mainContent = findViewById(R.id.mainContent);
        btnFragment.setOnClickListener(this);
        fragmentView = findViewById(R.id.fragmentScreen);

    }


    @Override
    public void getData(int data) {
        mainContent.setVisibility(View.VISIBLE);
        isVisible = false;
        k = data;
        fragmentView.setVisibility(View.GONE);
        viewRes.setText(String.valueOf(data));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnMain) {
            mainContent.setVisibility(View.GONE);
            isVisible = true;
            fragmentView.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void makeVisible() {
        mainContent.setVisibility(View.GONE);
        fragmentView.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isVisible = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("visible", isVisible);
        outState.putInt("number", k);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean("visible", false)) {
            makeVisible();
        } else {
            k = savedInstanceState.getInt("number", 0);
            viewRes.setText(String.valueOf(k));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            fragment.sendData();
        }
        return super.onOptionsItemSelected(item);
    }

}