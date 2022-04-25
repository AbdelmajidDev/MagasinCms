package com.example.magasin_cms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTask extends AppCompatActivity {
    EditText AddTaskTitle, AddTaskDescription, AddTaskDate, AddTaskTime, AddTaskReciever;
    int mYear, mMonth, mDay;
    int mHour, mMinute;
    Button AddNewTask;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        AddTaskTitle = findViewById(R.id.addTaskTitle);
        AddTaskDescription = findViewById(R.id.addTaskDescription);
        AddTaskDate = findViewById(R.id.taskDate);
        AddTaskTime = findViewById(R.id.taskTime);
        AddNewTask=findViewById(R.id.addNewTask);
        AddTaskReciever = findViewById(R.id.taskReciever);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        AddTaskDate.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                    String  specificDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            AddTaskDate.setText(specificDate);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        AddTaskTime.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        (view12, hourOfDay, minute) -> {
                            AddTaskTime.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

        AddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fStore.collection("Tasks").document(userID);
                Map<String,Object> user= new HashMap<>();

                user.put("Ttime",AddTaskTime);
                user.put("Tdate",AddTaskDate);
       /* user.put("Email",email);
        user.put("Phone",Phone);
        user.put("Position",Position);*/

        /*Toast.makeText(getApplicationContext(),"You're good to go!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));*/
            }
        });
    }
    /*public void SendTask() {
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Tasks").document(userID);
        Map<String, Object> user = new HashMap<>();

        user.put("Ttime", AddTaskTime);
        user.put("Tdate", AddTaskDate);
        user.put("Email", email);
        user.put("Phone", Phone);
        user.put("Position", Position);*/

        /*Toast.makeText(getApplicationContext(),"You're good to go!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));*/


    }


