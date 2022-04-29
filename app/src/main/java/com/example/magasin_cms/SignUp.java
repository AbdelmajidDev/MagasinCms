package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignUp extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener {
EditText name,mail,phone,pass,repass,pos;
RadioButton male,female;
RadioGroup rad;
Button sign;
String gender;
String csID;
String userID;
FirebaseAuth fAuth;
FirebaseFirestore fStore;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rad=findViewById(R.id.rad);
        pos=findViewById(R.id.pos);
        name=findViewById(R.id.Name);
        mail=findViewById(R.id.Smail);
        phone=findViewById(R.id.phone);
        pass=findViewById(R.id.Spsd);
        repass=findViewById(R.id.repsd);
        sign=findViewById(R.id.done);
        male=findViewById(R.id.Male);
        female=findViewById(R.id.Female);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos.setText(null);
                DialogFragment singleChoiceDialog=new SingleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(),"Single Choice Dialog");
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName=name.getText().toString().trim();
                String email=mail.getText().toString().trim();
                String Phone=phone.getText().toString().trim();
                String Password=pass.getText().toString().trim();
                String ConfPassword=repass.getText().toString().trim();
                String Position=pos.getText().toString().trim();
                  if(male.isChecked())
                {
                    gender="Male";

                }else if(female.isChecked()){
                    gender="Female";
                }

                if (FullName.isEmpty())
                {
                name.setError("Name is Required");
                return;
                }
                if (email.isEmpty())
                {
                    mail.setError("E-Mail is Required");
                    return;
                }
                if (Phone.isEmpty())
                {
                    phone.setError("Phone Number is Required");
                    return;
                }

                if (rad.getCheckedRadioButtonId() == -1)
                {
                    male.setError("!!!!");
                    female.setError("!!!!");
                    return;

                }
                if (Position.isEmpty())
                {
                    pos.setError("Position is Required");
                    return;
                }
                if (Password.isEmpty())
                {
                    pass.setError("Password is Required");
                    return;

                }
                if (ConfPassword.isEmpty())
                {
                    repass.setError("Password confirmation is Required");
                    return;

                }
                if (!Password.equals(ConfPassword))
                {
                    repass.setError("Password confirmation Doesn't match password");
                    return;
                }
                int i=FullName.indexOf(" ")+1;
                int y=i+7;
                csID=FullName.substring(0,1)+FullName.substring(i,y);

                fAuth.createUserWithEmailAndPassword(email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        userID=fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference=fStore.collection("users").document(userID);
                        Map<String,Object> user= new HashMap<>();
                        user.put("csID",csID);
                        user.put("Fname",FullName);
                        user.put("Gender",gender);
                        user.put("Email",email);
                        user.put("Phone",Phone);
                        user.put("Position",Position);

                        Toast.makeText(getApplicationContext(),"You're good to go!",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            Log.d(TAG,"C bon!!!!"+userID);
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public void onPositiveButtonClicked(String[] options, int def) {
        pos.setText(options[def]);
    }

    @Override
    public void onNegativeButtonClicked() {
    return;
    }
}