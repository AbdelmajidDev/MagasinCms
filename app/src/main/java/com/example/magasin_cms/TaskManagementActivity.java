package com.example.magasin_cms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskManagementActivity extends BaseActivity implements  {
FloatingActionButton addTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_management);
        //addTask=findViewById(R.id.addTask);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent=new Intent(getApplicationContext(),AddTask.class);
                startActivity(intent);
            }
        });
    }
}