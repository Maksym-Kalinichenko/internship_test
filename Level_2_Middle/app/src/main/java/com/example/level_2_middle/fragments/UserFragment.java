package com.example.level_2_middle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.level_2_middle.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {
    TextView nameF, ageF, switchF, dateF;
    private static UserFragment instance;
    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            pos = 0;
        } else {
            pos = savedInstanceState.getInt("pos", 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_user, container, false);

        instance = this;
        nameF = myView.findViewById(R.id.textNameFragment);
        ageF = myView.findViewById(R.id.textAgeFragment);
        dateF = myView.findViewById(R.id.textDateFragment);
        switchF = myView.findViewById(R.id.textStudentFragment);


        String childNew = "id_" + (pos + 1);
        DatabaseReference rff = FirebaseDatabase.getInstance().getReference().child("User").child(childNew);
        rff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue(String.class);
                int age = dataSnapshot.child("age").getValue(Integer.class);
                boolean isSwitch = dataSnapshot.child("student").getValue(Boolean.class);
                String date = dataSnapshot.child("date").getValue(String.class);
                nameF.setText("Name: " + name);
                ageF.setText("Age: " + (String.valueOf(age)));
                dateF.setText("Date: " + date);
                switchF.setText("Student: " + (String.valueOf(isSwitch)));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return myView;
    }

    public static UserFragment getInstance() {
        return instance;
    }


    public void fragmentNew(int position) {
        pos = position;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", pos);
    }
}

