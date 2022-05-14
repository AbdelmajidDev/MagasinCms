package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Transaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.magasin_cms.AddNewTodoTask.TAG;

public class specificActivity extends AppCompatActivity {
TextView stitle,sdesc,sdate;
    FirebaseAuth fAuth;
    String userID;
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
        String work=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String CurrentCsId=work.replace("@visteon.com","");
        fab=findViewById(R.id.edit_task);
        swipeLayout=findViewById(R.id.swipeLayout);
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        Intent j=getIntent();
        String n=j.getStringExtra("a");
        String m=j.getStringExtra("b");
        String p=j.getStringExtra("c");
        String q=j.getStringExtra("d");
       String id=j.getStringExtra("e");
        stitle.setText(m);
        sdesc.setText(p);
        sdate.setText(n);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(CurrentCsId.equals(q))) {
                    BottomSheet bottomSheet = new BottomSheet();
                    Bundle bundle = new Bundle();
                    bundle.putString("date", n);
                    bundle.putString("title", m);
                    bundle.putString("Description", p);
                    bundle.putString("Receiver", q);
                    bundle.putString("id",id);
                    bottomSheet.setArguments(bundle);

                    bottomSheet.show(getSupportFragmentManager(), "TAG");
                }else
                Toast.makeText(getApplicationContext(),"you cannot edit this Task",Toast.LENGTH_SHORT).show();

            }
        });



    }
}