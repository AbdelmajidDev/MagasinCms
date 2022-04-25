package com.example.magasin_cms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.magasin_cms.Adapter.ToDoAdapter;
import com.example.magasin_cms.Model.ToDoModel;
import com.example.magasin_cms.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDo extends AppCompatActivity implements onDialogCloseListener {

    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        myDB = new DataBaseHelper(ToDo.this);
        mList= new ArrayList<>();
        adapter = new ToDoAdapter(myDB,ToDo.this);

        mList = myDB.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTodoTask.newInstance().show(getSupportFragmentManager(), AddNewTodoTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = myDB.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
    }
}