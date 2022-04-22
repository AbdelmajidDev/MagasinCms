package com.example.magasin_cms;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

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
    public static ProfileFragment newInstance() {
        // Required empty public constructor
        return (new ProfileFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        userImage = (CircleImageView)getActivity().findViewById(R.id.user_image);
        changeProfile = getActivity().findViewById(R.id.floatingActionButton);
        userName = getActivity().findViewById(R.id.username);
        PID = getActivity().findViewById(R.id.pid);
        phoneTextView = getActivity().findViewById(R.id.inc_mobile);
        mailTextView = getActivity().findViewById(R.id.inc_email);
        genderTextView = getActivity().findViewById(R.id.inc_gender);
        positionTextView = getActivity().findViewById(R.id.inc_position);
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
                            Intent i=new Intent(getContext(),SignUp.class);
                            startActivity(i);
                        }
                    }
                });

        /*changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ProfileFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(20);
            }
        });*/
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}