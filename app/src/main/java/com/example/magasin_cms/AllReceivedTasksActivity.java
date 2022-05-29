package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.Calendar;

import static com.example.magasin_cms.AddNewTodoTask.TAG;

public class AllReceivedTasksActivity extends AppCompatActivity {

    TextView addTask;
    int mHour, mMinute;
    public String xdep;
    String xShift,mix;
    Button AllTasks;
    String n,m,x;
    FirebaseUser work;

    ObservableSnapshotArray<TaskModel> id;
    //Firebase
    private DocumentReference mDataBase;
    private FirebaseAuth mAuth;

    private FirestoreRecyclerAdapter adapter;

    //Recycler
    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_received_tasks);
        addTask = findViewById(R.id.addTask);

        String work=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String CurrentCsId=work.replace("@visteon.com","");
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.taskRecycler);
        AllTasks=findViewById(R.id.AllTasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        Intent depShift=getIntent();
         n=depShift.getStringExtra("xdep");
         m=depShift.getStringExtra("xShift");

        firebaseFirestore.collection(n)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task ) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                x=document.get("receiver").toString();
                                //if((mHour==01 && mMinute==22)||(mHour==14 && mMinute==00)||(mHour==22 && mMinute==00))
                                if((mHour==15 && mMinute==57))

                                {   mix="2"+n;
                                    FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/"+mix,"New task","Tap to Open"
                                        ,getApplicationContext(),AllReceivedTasksActivity.this);
                                    notificationsSender.SendNotifications();
                                    System.out.println("time :"+mix);
                                    document.getReference().update("receiver","2");
                                }
                                if((mHour==16 && mMinute==05)){
                                    mix="3"+n;
                                    System.out.println("time :"+mix);
                                    FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/"+mix,"New task","Tap to Open"
                                            ,getApplicationContext(),AllReceivedTasksActivity.this);
                                    notificationsSender.SendNotifications();
                                    document.getReference().update("receiver","3");
                                }
                                if((mHour==6 && mMinute==00)){firebaseFirestore.collection(n).document(document.getId()).delete();}
                                }
                            }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Query query2=FirebaseFirestore.getInstance()
                .collection(n).whereEqualTo("receiver",m);
                //.whereEqualTo("receiver","2");
                        //.document(m).collection("Received");

        FirestoreRecyclerOptions<TaskModel> options = new FirestoreRecyclerOptions.Builder<TaskModel>()
                .setQuery(query2, new SnapshotParser<TaskModel>() {
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
        //if((mHour==01 && mMinute==22)||(mHour==14 && mMinute==00)||(mHour==22 && mMinute==00)){}
           // DocumentReference documentReference = firebaseFirestore.collection(n).whereEqualTo("receiver","3").get();
           // documentReference.update("csID","azertyy");


        System.out.println("xdepartement :"+n);
        adapter.startListening();
    }


}