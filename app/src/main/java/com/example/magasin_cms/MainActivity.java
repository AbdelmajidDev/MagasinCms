package com.example.magasin_cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText mail,psd;

    Button LogIn,SignUp,Forget;
    RelativeLayout rellay1, rellay2;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);
        LogIn=findViewById(R.id.LogIn);
        SignUp=findViewById(R.id.SignUp);
        Forget=findViewById(R.id.Forget);
        mail=findViewById(R.id.mail);
        psd=findViewById(R.id.psd);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 1000); //2000 is the timeout for the splash

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=mail.getText().toString().trim();
                String Pass=psd.getText().toString().trim();

                if(TextUtils.isEmpty(Email))
                {
                    mail.setError("Required Field..");
                    return;
                }

                if(TextUtils.isEmpty(Pass))
                {
                    psd.setError("Required Field..");
                    return;
                }
                mDialog.setMessage("Processing..");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mail.setText(null);
                            psd.setText(null);
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            Intent k=new Intent(getApplicationContext(),CategoriesActivity.class);
                            startActivity(k);
                            mDialog.show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Problem occured!",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent (getApplicationContext(),SignUp.class);
                startActivity(i);
            }
        });
        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j=new Intent(getApplicationContext(),Password_Reset.class);
                startActivity(j);
            }
        });

    }
}
