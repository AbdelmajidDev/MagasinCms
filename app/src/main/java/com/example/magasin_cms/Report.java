package com.example.magasin_cms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Report extends AppCompatActivity {
EditText report;
    String userID;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
Button sendReport,cancelReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        report=findViewById(R.id.report);
        sendReport=findViewById(R.id.sendReport);
        cancelReport=findViewById(R.id.cancelReport);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        cancelReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Report=report.getText().toString().trim();
                if (Report.isEmpty())
                {
                    report.setError("This field is Required");


                }
                userID=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fStore.collection("Reports").document(userID);
                Map<String,Object> user= new HashMap<>();
                user.put("report",Report);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"C bon!!!!"+userID);
                    }
                });
                Toast.makeText(getApplicationContext(),"You're good to go!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}