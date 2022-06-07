package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magasin_cms.Model.TaskModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class SentTasks extends AppCompatActivity {
    TextView addTask;


    ObservableSnapshotArray<TaskModel> id;
    private DocumentReference mDataBase;
    private FirebaseAuth fAuth;
    String work,CurrentCsId;
    private FirestoreRecyclerAdapter adapter;

    //Recycler
    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_tasks);
        addTask = findViewById(R.id.addTask);
        fAuth = FirebaseAuth.getInstance();
         work=FirebaseAuth.getInstance().getCurrentUser().getEmail();

         CurrentCsId=work.replace("@visteon.com","");

        firebaseFirestore=FirebaseFirestore.getInstance();


        recyclerView = findViewById(R.id.taskRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);

        Query query = FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(CurrentCsId).collection("Sent");


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
        adapter= new FirestoreRecyclerAdapter<TaskModel, SentTasks.TaskViewHolder>(options) {
            @NonNull
            @Override
            public SentTasks.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);
                return new SentTasks.TaskViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull SentTasks.TaskViewHolder holder, int position, @NonNull TaskModel model) {
                holder.task_title.setText(model.getTitle());
                holder.task_details.setText(model.getDescription());
                holder.task_ReceiverCs.setText(model.getCsID());
                holder.task_status.setText((model.getStatus()));
                holder.Task_Card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getApplicationContext(),specificActivity.class);
                        intent.putExtra("a",model.getDate());
                        intent.putExtra("b",model.getTitle());
                        intent.putExtra("c",model.getDescription());
                        intent.putExtra("d",model.getCsID());
                        intent.putExtra("e",model.getItem_id());
                        intent.putExtra("PreviousAct","SentTasks");
                        startActivity(intent);
                    }
                });

                holder.Task_Card.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SentTasks.this);
                        builder.setTitle("Logout");
                        builder.setMessage("Are you sure you want to delete this task?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firebaseFirestore.collection("tasks").document(CurrentCsId)
                                        .collection("Sent").document(model.getItem_id()).delete();

                                //System.exit(0);
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                        return false;
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
        TextView task_title , task_details, task_receiver , task_ReceiverCs,task_status;
        CardView Task_Card;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            Task_Card=itemView.findViewById(R.id.Task_Card);
            task_title = itemView.findViewById(R.id.title);
            task_details = itemView.findViewById(R.id.details);
            task_ReceiverCs = itemView.findViewById(R.id.ReceiverCs);
            task_status=itemView.findViewById(R.id.status);
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
    /*ItemTouchHelper.SimpleCallback ItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(SentTasks.this, "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            Toast.makeText(SentTasks.this, "on Swiped ", Toast.LENGTH_SHORT).show();
            //Remove swiped item from list and notify the RecyclerView
            //int position = viewHolder.getAdapterPosition();
            Toast.makeText(SentTasks.this, "wanna delete", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();

        }
    };*/

    public void Delete(String position){
     firebaseFirestore.collection("users").document(CurrentCsId).collection("Sent").document(position).delete();

    }



}