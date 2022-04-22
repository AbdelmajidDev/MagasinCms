package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;

public class Password_Reset extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton , CancelButton;
   // private ProgressBar progressBar;
    int count=0;
    Timer timer;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__reset);

        emailEditText =findViewById(R.id.Rmail);
        resetPasswordButton=findViewById(R.id.RSend);
        CancelButton=findViewById(R.id.Cancel);
       // progressBar= findViewById(R.id.progressBar);
        //timer= new Timer();
        /*TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {

                if (count==4){
                    progressBar.setProgress(count);

                    timer.cancel();

                }

            }
        }
        timer.schedule(timerTask,0,500);*/
        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
                finish();
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });
    }

    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()){
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide a valid Email!");
            emailEditText.requestFocus();
            return;
        }

      //  progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Password_Reset.this,"check your mail to reset your password!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Password_Reset.this,"Try again! Something wrong happened!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}