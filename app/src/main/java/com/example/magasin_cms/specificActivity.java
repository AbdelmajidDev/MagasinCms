package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Transaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.magasin_cms.AddNewTodoTask.TAG;

public class specificActivity extends AppCompatActivity {
TextView stitle,sdesc,sdate;
Button ButtonComplete;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    DocumentReference reference;
    SwipeRefreshLayout swipeLayout;
FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        stitle=findViewById(R.id.stitle);
        sdate=findViewById(R.id.sdate);
        sdesc=findViewById(R.id.sdesc);
        ButtonComplete=findViewById(R.id.buttonComplet);
        String work=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String CurrentCsId=work.replace("@visteon.com","");
        fab=findViewById(R.id.edit_task);
        swipeLayout=findViewById(R.id.swipeLayout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        Intent j=getIntent();
        String n=j.getStringExtra("a");
        String m=j.getStringExtra("b");
        String p=j.getStringExtra("c");
        String q=j.getStringExtra("d");
        String t=j.getStringExtra("f");
       String id=j.getStringExtra("e");
       String x1=j.getStringExtra("x1");
       String PreviousAct=j.getStringExtra("PreviousAct");
        stitle.setText("Task Title : "+m);
        sdesc.setText("Task Description : "+p);
        sdate.setText("Task Date : "+n);
        System.out.println("departemtn delete;"+x1);
        System.out.println("id delete : "+id);
        System.out.println("assigned by: "+t);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fStore.collection("tasks")
                        .document(fAuth.getCurrentUser().getEmail().replace("@visteon.com",""))
                        .collection("Sent").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            BottomSheet bottomSheet = new BottomSheet();
                            Bundle bundle = new Bundle();
                            bundle.putString("date", n);
                            bundle.putString("title", m);
                            bundle.putString("Description", p);
                            bundle.putString("Receiver", q);
                            bundle.putString("id",id);
                            bottomSheet.setArguments(bundle);

                            bottomSheet.show(getSupportFragmentManager(), "TAG");
                        }else{
                            Toast.makeText(getApplicationContext(),"you cannot edit this Task",Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }
                //if(!(CurrentCsId.equals(q))) {

               // }else

           // }
        });

        ButtonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreviousAct.equals("ReceivedTasks")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(specificActivity.this);
                    builder.setTitle("Task completed");
                    builder.setMessage(t);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            fStore.collection("tasks").document(t)
                                    .collection("Sent").whereEqualTo("title", m).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            fStore.collection("tasks").document(CurrentCsId)
                                                    .collection("Received").document(id).delete();
                                            fStore.collection("tasks")
                                                    .document(document.get("Assigned_by").toString()).collection("Sent")
                                                    .document(document.getId()).update("status", "completed");

                                        }
                                    }

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();


                }else if(PreviousAct.equals("AllReceivedTasksActivity"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(specificActivity.this);
                    builder.setTitle("Task completed");
                    builder.setMessage(t);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            fStore.collection("tasks").document(t)
                                    .collection("Sent").whereEqualTo("title", m).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            fStore.collection(x1).document(id).delete();
                                            fStore.collection("tasks")
                                                    .document(document.get("Assigned_by").toString()).collection("Sent")
                                                    .document(document.getId()).update("status", "completed");

                                        }
                                    }

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                     }else{Toast.makeText(getApplicationContext(),"You cannot perform this action",Toast.LENGTH_SHORT).show();}
            }
        });
}
    }

