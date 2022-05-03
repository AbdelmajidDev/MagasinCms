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
                            String specificDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
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
                            AddTaskTime.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TaskManagementActivity.class));
                finish();
            }
        });

        AddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });

    }

    private void processinsert() {

        Map<String,Object> map = new HashMap<>();
        map.put("title",AddTaskTitle.getText().toString());
        map.put("description",AddTaskDescription.getText().toString());
        map.put("time",AddTaskTime.getText().toString());
        map.put("date",AddTaskDate.getText().toString());
        map.put("receiver",AddTaskReciever.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("tasks").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AddTaskTitle.setText("");
                        AddTaskDescription.setText("");
                        AddTaskTime.setText("");
                        AddTaskDate.setText("");
                        AddTaskReciever.setText("");
                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
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
        if (Time .isEmpty()) {
            AddTaskTime.setError("Time Number is Required");
            return;
        }
        if (Receiver.isEmpty()) {
            AddTaskReciever.setError("choose a receiver");
            return;
        }




                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("tasks").document(userID);
                Map<String, Object> task = new HashMap<>();

                 task.put("Ttitle", Title);
                 task.put("Tdescription", Description);
                 task.put("Tdate", Date);
                 task.put("Ttime", Time);
                 task.put("Treceiver", Receiver);

                Toast.makeText(getApplicationContext(), "You're good to go!", Toast.LENGTH_SHORT).show();


                documentReference.set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "C bon!!!!" + userID);
                    }
                });


            }

}



