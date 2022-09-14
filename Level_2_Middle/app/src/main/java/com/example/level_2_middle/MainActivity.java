package com.example.level_2_middle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.level_2_middle.classes.DataAdapter;
import com.example.level_2_middle.classes.GetData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reff;
    TextView name, age;
    private ArrayList<GetData> userList;
    private long value;
    public DataAdapter adapter;
    private boolean isVisible = false;
    RecyclerView recyclerView;
    FragmentContainerView fragmentScreen;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        reff = FirebaseDatabase.getInstance().getReference().child("User");
        reff.keepSynced(true);
        recyclerView.setHasFixedSize(true);
        userList = new ArrayList<>();
        adapter = new DataAdapter(userList, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        name = findViewById(R.id.textNameSingle);
        age = findViewById(R.id.textAgeSingle);
        fragmentScreen = findViewById(R.id.fragmentScreen);

        getDataFirebase();

    }

    public void getDataFirebase() {
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                value = (int) dataSnapshot.getChildrenCount();
                for (int i = 1; i <= value; i++) {
                    String childBD = "id_" + i;
                    String userName = dataSnapshot.child(childBD).child("name").getValue(String.class);
                    int userAge = dataSnapshot.child(childBD).child("age").getValue(Integer.class);
                    String userDate = dataSnapshot.child(childBD).child("date").getValue(String.class);
                    boolean checkSwitch = dataSnapshot.child(childBD).child("student").getValue(Boolean.class);
                    userList.add(new GetData(userName, userAge, userDate, checkSwitch));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("visible", isVisible);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean("visible", false)) {
            makeVisible();
        }
    }

    public void makeVisible() {
        recyclerView.setVisibility(View.GONE);
        fragmentScreen.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isVisible = true;
    }

    public void firstScreen() {
        recyclerView.setVisibility(View.VISIBLE);
        isVisible = false;
        fragmentScreen.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                firstScreen();
                return true;
            case R.id.action_add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

