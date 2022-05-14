package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AddTask extends AppCompatActivity {
    EditText AddTaskTitle, AddTaskDescription, AddTaskDate, AddTaskTime, AddTaskReciever;
    int mYear, mMonth, mDay;
    int mHour, mMinute;
    Button AddNewTask, Back_btn;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    DocumentReference documentReference,documentReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        AddTaskTitle = findViewById(R.id.addTaskTitle);
        AddTaskDescription = findViewById(R.id.addTaskDescription);
        AddTaskDate = findViewById(R.id.taskDate);
        AddTaskTime = findViewById(R.id.taskTime);
        AddNewTask = findViewById(R.id.addNewTask);
        Back_btn = findViewById(R.id.back_button);
        AddTaskReciever = findViewById(R.id.taskReciever);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        AddTaskDate.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            String specificDate = dayOfMonth + "-" + (monthOfYear + 1) +"-"+(mYear);
                            AddTaskDate.setText(specificDate);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        AddTaskTime.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        (view12, hourOfDay, minute) -> {
                            //AddTaskTime.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendData(view);
            }
        });

    }



    public void SendData(View view) {
        String Title = AddTaskTitle.getText().toString().trim();
        String Description = AddTaskDescription.getText().toString().trim();
        String Date = AddTaskDate.getText().toString().trim();
        String Time = AddTaskTime.getText().toString().trim();
        String Receiver = AddTaskReciever.getText().toString().trim();

        if (Title.isEmpty()) {
            AddTaskTitle.setError("Title is Required");
            return;
        }
        if (Description.isEmpty()) {
            AddTaskDescription.setError("Description is Required");
            return;
        }
        if (Date .isEmpty()) {
            AddTaskDate.setError("Date is Required");
            return;
        }

        if (Receiver.isEmpty()) {
            AddTaskReciever.setError("choose a receiver");
            return;
        }




                //userID = fAuth.getCurrentUser().getUid();

        if(Receiver.equals((fAuth.getCurrentUser().getEmail().replace("@visteon.com","")))){
            AddTaskReciever.setError("Choose another user");
            return;
        }else{
             documentReference = fStore.collection("tasks").document(Receiver)
                    .collection("Received").document();
             documentReference2=fStore.collection("tasks").document(fAuth.getCurrentUser().getEmail().replace("@visteon.com",""))
                     .collection("Sent").document();
        }
                //DocumentReference documentReference = fStore.collection("tasks")
        // .document(userID).collection("sent").document();
                Map<String, Object> task = new HashMap<>();

                 task.put("title", Title);
                 task.put("description", Description);
                 task.put("date", Date);
                 task.put("time", Time);
                 task.put("receiver", Receiver);

                Toast.makeText(getApplicationContext(), "You're good to go!", Toast.LENGTH_SHORT).show();


                documentReference.set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "C bon!!!!" + userID);
                    }
                });

        documentReference2.set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "C bon!!!" + userID);
            }
        });


            }

}



