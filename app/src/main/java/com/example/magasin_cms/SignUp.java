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
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignUp extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener,SingleChoiceDialogFragment2.SingleChoiceListener {
EditText name,phone,pass,repass,dep,shift,mail;
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
        dep=findViewById(R.id.dep);
        name=findViewById(R.id.Name);
        shift=findViewById(R.id.CurrentShift);
        phone=findViewById(R.id.phone);
        pass=findViewById(R.id.Spsd);
        repass=findViewById(R.id.repsd);
        sign=findViewById(R.id.done);
        male=findViewById(R.id.Male);
        mail=findViewById(R.id.Email);
        female=findViewById(R.id.Female);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dep.setText(null);
                DialogFragment singleChoiceDialog=new SingleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(),"Single Choice Dialog");
            }
        });
        shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shift.setText(null);
                DialogFragment singleChoiceDialog2=new SingleChoiceDialogFragment2();
                singleChoiceDialog2.setCancelable(false);
                singleChoiceDialog2.show(getSupportFragmentManager(),"Single Choice Dialog");
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName=name.getText().toString().trim();
                String CurrentShift=shift.getText().toString().trim();
                String Phone=phone.getText().toString().trim();
                String Password=pass.getText().toString().trim();
                String ConfPassword=repass.getText().toString().trim();
                String Department=dep.getText().toString().trim();
                String Email=mail.getText().toString().trim();
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

                if (Email.isEmpty())
                {
                    mail.setError("Email is Required");
                    return;
                }
                if (!Email.contains("@visteon.com"))
                {
                    mail.setError("xxxx@visteon.com");
                    return;
                }

                if (Phone.isEmpty())
                {
                    //Add anther test(8 digits)
                    phone.setError("Phone Number is Required");
                    return;
                }

                if (rad.getCheckedRadioButtonId() == -1)
                {
                    male.setError("!!!!");
                    female.setError("!!!!");
                    return;

                }
                if (Department.isEmpty())
                {
                    dep.setError("Position is Required");
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

                        /*int i=FullName.indexOf(" ")+1;
                        int y=i+7;

                        String mail=(csID+"@visteon.com").toLowerCase();*/
                csID=(Email.replace("@visteon.com","")).toLowerCase();



                fAuth.createUserWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        userID=fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference=fStore.collection("users").document(csID);
                        Map<String,Object> user= new HashMap<>();
                        user.put("csID",csID);
                        user.put("Fname",FullName);
                        user.put("Gender",gender);
                        user.put("Email",Email);
                        user.put("Phone",Phone);
                        user.put("Department",Department);
                        user.put("Current Shift",CurrentShift);
                        FirebaseMessaging.getInstance().deleteToken();
                       /*FirebaseMessaging.getInstance().unsubscribeFromTopic("1");
                       FirebaseMessaging.getInstance().unsubscribeFromTopic("2");
                       FirebaseMessaging.getInstance().unsubscribeFromTopic("3");*/

                        //+Department
                        Toast.makeText(getApplicationContext(),"You're good to go!",Toast.LENGTH_LONG).show();
                        finish();

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseMessaging.getInstance().subscribeToTopic(csID);
                                String mix=CurrentShift+Department;
                                FirebaseMessaging.getInstance().subscribeToTopic(mix);
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
        dep.setText(options[def]);

    }

    @Override
    public void onNegativeButtonClicked() {
    return;
    }


    public void onPositiveButtonClicked2(String[] options, int def) {
        shift.setText(options[def]);

    }


    public void onNegativeButtonClicked2() {
        return;
    }

}
