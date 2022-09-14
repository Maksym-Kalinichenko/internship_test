package com.example.level_2_middle.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.level_2_middle.MainActivity;
import com.example.level_2_middle.R;
import com.example.level_2_middle.fragments.UserFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    DatabaseReference rff;
    private ArrayList<GetData> userList;
    Context context;
    FragmentContainerView fragmentView;

    public DataAdapter(ArrayList<GetData> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, age, date;
        private Switch aSwitch;

        public ViewHolder(final View view) {
            super(view);
            name = view.findViewById(R.id.textNameSingle);
            age = view.findViewById(R.id.textAgeSingle);
            date = view.findViewById(R.id.textDateSingle);
            aSwitch = view.findViewById(R.id.switchSingle);

            fragmentView = view.findViewById(R.id.fragmentScreen);


            view.setOnClickListener(view1 -> {
                int positionIndex = getAdapterPosition();
                ((MainActivity) context).makeVisible();
                UserFragment.getInstance().fragmentNew(positionIndex);
            });

            aSwitch.setOnClickListener(view12 -> {
                int positionIndex = getAdapterPosition();
                String childDB = "id_" + (positionIndex + 1);
                boolean checkSwitch = userList.get(positionIndex).getSwitch();
                checkSwitch = !checkSwitch;
                rff = FirebaseDatabase.getInstance().getReference().child("User").child(childDB).child("student");
                rff.setValue(checkSwitch);
            });
        }
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {

        String name = userList.get(position).getName();
        int age = userList.get(position).getAge();
        String date = userList.get(position).getDate();
        boolean check = userList.get(position).getSwitch();

        holder.name.setText("Name: " + name);
        holder.age.setText("Age: " + (String.valueOf(age)));
        holder.date.setText("Date: " + date);
        holder.aSwitch.setChecked(check);

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }
}
