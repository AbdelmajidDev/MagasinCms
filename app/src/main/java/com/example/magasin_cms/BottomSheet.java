package com.example.magasin_cms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BottomSheet extends BottomSheetDialogFragment {
    FirebaseFirestore fStore;
    Activity specificActivity;
    Context context;
    public BottomSheet() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.row_edit_item,container,false);
        EditText editTaskTitle,editTaskDescription,edittaskDate,edittaskreciever;
        Button editTask,edit_back_button;
        editTaskTitle=view.findViewById(R.id.editTaskTitle);
        editTaskDescription=view.findViewById(R.id.editTaskDescription);
        edittaskDate=view.findViewById(R.id.edittaskDate);
        edittaskreciever=view.findViewById(R.id.edittaskReciever);
        editTask=view.findViewById(R.id.editTask);
        edit_back_button=view.findViewById(R.id.edit_back_button);
        Bundle bundle=getArguments();
       String n=bundle.getString("date");
       String m=bundle.getString("title");
       String p=bundle.getString("Description");
       String q=bundle.getString("Receiver");
       String r=bundle.getString("id");
        edittaskDate.setText(n);
        editTaskTitle.setText(m);
        editTaskDescription.setText(p);
        edittaskreciever.setText(q);

        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore = FirebaseFirestore.getInstance();
                DocumentReference documentReference = fStore.collection("tasks").document(r);
                documentReference.update("title",editTaskTitle.getText().toString(),"date",edittaskDate.getText().toString()
                        ,"description",editTaskDescription.getText().toString(),"receiver",edittaskreciever.getText().toString());
                dismiss();

            }
        });
        edit_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }
}
