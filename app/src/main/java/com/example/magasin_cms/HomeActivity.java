package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class HomeActivity extends AppCompatActivity {
    private Button logout;
    private CircleImageView userImage;
    private TextView userName , PID ;
    private TextView mailTextView , phoneTextView , genderTextView , positionTextView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private String mail ;
    private String userid;
    FloatingActionButton changeProfile;
    TextView myTextView;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private Map<String, String> userMap;
    private static final String USERS = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.logout);
        userImage = findViewById(R.id.user_image);
        changeProfile = findViewById(R.id.floatingActionButton);
        userName = findViewById(R.id.username);
        PID = findViewById(R.id.pid);
        phoneTextView = findViewById(R.id.inc_mobile);
        mailTextView = findViewById(R.id.inc_email);
        genderTextView = findViewById(R.id.inc_gender);
        positionTextView = findViewById(R.id.inc_position);
        FirebaseUser work=FirebaseAuth.getInstance().getCurrentUser();
        String CurrentId=work.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        reference=firestore.collection("users").document(CurrentId);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            String fullName=task.getResult().getString("Fname");
                            String Email=task.getResult().getString("Email");
                            String phone=task.getResult().getString("Phone");
                            String gender=task.getResult().getString("Gender");
                            String position=task.getResult().getString("Position");
                            mailTextView.setText(Email);
                            phoneTextView.setText(phone);
                            genderTextView.setText(gender);
                            positionTextView.setText(position);
                            userName.setText(fullName);
                            PID.setText(CurrentId);
                        }else{
                            Intent i=new Intent(getApplicationContext(),SignUp.class);
                            startActivity(i);
                        }
                    }
                });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(HomeActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(20);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finishAffinity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 ){
            Uri uri = data.getData();
            userImage.setImageURI(uri);
        }

    }
}