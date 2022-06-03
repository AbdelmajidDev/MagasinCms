

package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.Token;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.internal.cache.DiskLruCache;

import static android.content.ContentValues.TAG;

public class AddTask extends AppCompatActivity implements SingleChoiceDialogFragment2.SingleChoiceListener {
    EditText AddTaskTitle, AddTaskDescription, AddTaskDate, AddTaskscID, AddTaskShiftReceiver;
    int mYear, mMonth, mDay;
    Switch aswitch;
    int mHour, mMinute;
    Button AddNewTask, Back_btn;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    FirebaseAuth fAuth;
    CheckBox NcheckBox;
    String xdep,mix;
    String yy;
    String userID;
    FirebaseFirestore fStore;
    String token;
    DocumentReference documentReference,documentReference2, documentReference3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        AddTaskTitle = findViewById(R.id.addTaskTitle);
        AddTaskDescription = findViewById(R.id.addTaskDescription);
        AddTaskDate = findViewById(R.id.taskDate);
        AddTaskscID = findViewById(R.id.taskcsID);
        AddNewTask = findViewById(R.id.addNewTask);
        Back_btn = findViewById(R.id.back_button);
        NcheckBox=findViewById(R.id.NCheckBox);
        AddTaskShiftReceiver = findViewById(R.id.taskShiftReciever);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("users")
                .document((fAuth.getCurrentUser().getEmail().replace("@visteon.com","")).toLowerCase())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    xdep = task.getResult().getString("Department").trim();
                }
            }
        });


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

       /* AddTaskTime.setOnTouchListener((view, motionEvent) -> {
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
        });*/

        AddTaskShiftReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskShiftReceiver.setText(null);
                DialogFragment singleChoiceDialog2=new SingleChoiceDialogFragment2();
                singleChoiceDialog2.setCancelable(false);
                singleChoiceDialog2.show(getSupportFragmentManager(),"Single Choice Dialog");
            }
        });

        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { SendData(view);


            }
        });

    }



    public void SendData(View view) {
        String Title = AddTaskTitle.getText().toString().trim();
        String Description = AddTaskDescription.getText().toString().trim();
        String Date = AddTaskDate.getText().toString().trim();
        String csID = AddTaskscID.getText().toString().trim().toLowerCase().trim();
        String ReceivingShift = AddTaskShiftReceiver.getText().toString().toLowerCase().trim();

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

        if (csID.isEmpty()) {
            AddTaskscID.setError("choose a receiver");
            return;
        }






        //userID = fAuth.getCurrentUser().getUid();

        if(csID.equals((fAuth.getCurrentUser().getEmail().replace("@visteon.com","")))){
            AddTaskscID.setError("Choose another user");
            return;

        }else{

            /* documentReference = fStore.collection("tasks").document(csID)
                    .collection("Received").document();*/
             documentReference2=fStore.collection("tasks")
                     .document((fAuth.getCurrentUser().getEmail().replace("@visteon.com","")).toLowerCase())
                     .collection("Sent").document();
        }

                Map<String, Object> task = new HashMap<>();

                 task.put("title", Title);
                 task.put("description", Description);
                 task.put("date", Date);
                 task.put("csID", csID);
                 task.put("receiver", ReceivingShift);
                 task.put("status","uncompleted");
                 task.put("Assigned_by",(fAuth.getCurrentUser().getEmail().replace("@visteon.com","")).toLowerCase());


        //String Topic=ReceivingShift+xdep;
                if(NcheckBox.isChecked()){
                    documentReference = fStore.collection("tasks").document(csID)
                            .collection("Received").document();
                        AddTaskShiftReceiver.setText(null);
                    FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/"+csID,Title,Description
                            ,getApplicationContext(),AddTask.this);
                    notificationsSender.SendNotifications(); }
                else{
                    documentReference = fStore.collection(xdep).document();
                            //.document(AddTaskShiftReceiver.getText().toString().trim())
                            //.collection("Received").document();
                    mix=(AddTaskShiftReceiver.getText()+xdep);
                    FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/"+mix,Title,Description
                            ,getApplicationContext(),AddTask.this);
                    System.out.println("send to : "+mix);
                    notificationsSender.SendNotifications();}



                //String xShift=String.valueOf(Integer.valueOf(Receiver)+1);


                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();


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

    @Override
    public void onPositiveButtonClicked2(String[] options, int def) {
        AddTaskShiftReceiver.setText(options[def]);
    }

    @Override
    public void onNegativeButtonClicked2() {

    }
}



