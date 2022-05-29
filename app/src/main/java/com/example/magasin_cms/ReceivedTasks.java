package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.magasin_cms.Model.TaskModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReceivedTasks extends AppCompatActivity {

    TextView addTask;
    int mHour, mMinute;
String xdep;
String xShift;
Button AllTasks;
    FirebaseUser work;

    //Firebase

    private FirestoreRecyclerAdapter adapter;

    //Recycler
     RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_management);
        addTask = findViewById(R.id.addTask);
        String work=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String CurrentCsId=work.replace("@visteon.com","");
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.taskRecycler);
        AllTasks=findViewById(R.id.AllTasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);

        FirebaseFirestore.getInstance().collection("users")
                .document(CurrentCsId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    xdep = task.getResult().getString("Department");
                    xShift=task.getResult().getString("Current Shift");

                }
            }
        });
        AllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(getApplicationContext(),AllReceivedTasksActivity.class);
                intent.putExtra("xdep",xdep);
                intent.putExtra("xShift",xShift);
                startActivity(intent);
            }
        });



        Query query = FirebaseFirestore.getInstance()
                .collection("tasks").document(CurrentCsId).collection("Received");

        FirestoreRecyclerOptions<TaskModel> options = new FirestoreRecyclerOptions.Builder<TaskModel>()
                .setQuery(query, new SnapshotParser<TaskModel>() {
                    @NonNull
                    @Override
                    public TaskModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        TaskModel taskModel=snapshot.toObject(TaskModel.class);
                        String item_id=snapshot.getId();
                        taskModel.setItem_id(item_id);
                        return taskModel;
                    }
                })
                .build();
        //id=options.getSnapshots();
         adapter= new FirestoreRecyclerAdapter<TaskModel, TaskViewHolder>(options) {
            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);
                return new TaskViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull TaskModel model) {
                holder.task_title.setText(model.getTitle());
                holder.task_details.setText(model.getDescription());
                holder.task_receiver.setText(model.getReceiver());
                holder.task_date.setText(model.getDate());


                holder.Task_Card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent=new Intent(getApplicationContext(),specificActivity.class);
                        intent.putExtra("a",model.getDate());
                        intent.putExtra("b",model.getTitle());
                        intent.putExtra("c",model.getDescription());
                        intent.putExtra("d",model.getReceiver());
                        intent.putExtra("e",model.getItem_id());
                        startActivity(intent);
                    }
                });
            }
        };



        recyclerView.setAdapter(adapter);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                startActivity(intent);
            }
        });

    }





    private class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView task_title , task_details, task_receiver , task_date;
            CardView Task_Card;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            Task_Card=itemView.findViewById(R.id.Task_Card);
            task_title = itemView.findViewById(R.id.title);
            task_details = itemView.findViewById(R.id.details);
            task_receiver = itemView.findViewById(R.id.status);
            task_date = itemView.findViewById(R.id.date);

        }
    }

        @Override
        protected void onStop() {
            super.onStop();
            adapter.stopListening();
        }

        @Override
        protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


}