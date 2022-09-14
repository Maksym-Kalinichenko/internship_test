package com.example.level_2_middle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.level_2_middle.classes.Member;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button saveBtn;
    DatabaseReference reff;
    Member member;
    EditText editAge, editName, editDate;
    DatePickerDialog datePick;
    boolean result = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reff = FirebaseDatabase.getInstance().getReference().child("User");
        editAge = findViewById(R.id.editAge);
        editName = findViewById(R.id.editName);

        editDate = findViewById(R.id.editDate);
        editDate.setInputType(InputType.TYPE_NULL);
        editDate.setOnClickListener(this);
        member = new Member();
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(view -> {

            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int value = (int) dataSnapshot.getChildrenCount();

                    setData(value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(AddActivity.this, R.string.user_add, Toast.LENGTH_SHORT).show();
        });

    }

    public void setData(int value) {
        if (result) {
            String newChild = "id_" + (value + 1);
            String name = editName.getText().toString();
            int age = Integer.parseInt(editAge.getText().toString());
            String date = editDate.getText().toString();
            member.setName(name);
            member.setAge(age);
            member.setDate(date);
            member.setStudent(false);
            reff.child(newChild).setValue(member);
            result = false;
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.editDate) {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            datePick = new DatePickerDialog(AddActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            editDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, year, month, day);
            datePick.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}