package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.magasin_cms.Model.TaskModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;

public class TaskManagementActivity extends AppCompatActivity {
    @BindView(R.id.addTask)
    TextView addTask;

    //Firebase
    private DocumentReference mDataBase;
    private FirebaseAuth mAuth;

    //Recycler
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_management);
        addTask = findViewById(R.id.addTask);
        FirebaseUser work=FirebaseAuth.getInstance().getCurrentUser();
        String CurrentId=work.getUid();
       // DocumentReference reference;
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        mDataBase=firestore.collection("tasks").document(CurrentId);
        mDataBase.get();

        //mDataBase = FirebaseDatabase.getInstance().getReference().child("tasks").child(uId);
        //Recycler

        recyclerView = findViewById(R.id.taskRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                startActivity(intent);
            }
        });

    }

    /*@Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<TaskModel, MyViewHolder> adapter = new FirebaseRecyclerAdapter<TaskModel, MyViewHolder>(TaskModel.class,R.layout.item_task,MyViewHolder.class,mDataBase) {

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull TaskModel model) {
                holder.setTitle(model.getTitle());
                holder.setDetails(model.getDescription());
                holder.setDate(model.getDate());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };


        recyclerView.setAdapter(adapter);


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final View myview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setTitle(String title) {
            TextView mTitle = myview.findViewById(R.id.title);
            mTitle.setText(title);
        }

        public void setDetails(String details) {
            TextView mDetails = myview.findViewById(R.id.details);
            mDetails.setText(details);
        }

        public void setDate(String date) {
            TextView mDate = myview.findViewById(R.id.date);
            mDate.setText(date);
        }
    }*/
}