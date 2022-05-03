package com.example.magasin_cms.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dialog.plus.ui.DialogPlus;
import com.example.magasin_cms.Model.TaskModel;
import com.example.magasin_cms.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddTaskAdapter extends FirebaseRecyclerAdapter<TaskModel,AddTaskAdapter.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AddTaskAdapter(@NonNull FirebaseRecyclerOptions<TaskModel> options) {
        super(options);
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        TextView title, description , date, time, receiver;
        Button edit, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.title);
            description= itemView.findViewById(R.id.details);
            date= itemView.findViewById(R.id.date);
            time= itemView.findViewById(R.id.day);
            receiver= itemView.findViewById(R.id.status);
        }
    }
    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);
        return new AddTaskAdapter.MyViewHolder(v);

    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull TaskModel model) {
            holder.title.setText(model.getTitle());
            holder.description.setText(model.getDescription());
            holder.date.setText(model.getDate());
            holder.time.setText(model.getTime());
            holder.receiver.setText(model.getReceiver());


        /*holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Map<String,Object> map = new HashMap<>();
                map.put("title",title.getText().toString());
                map.put("description",AddTaskDescription.getText().toString());
                map.put("time",AddTaskTime.getText().toString());
                map.put("date",AddTaskDate.getText().toString());
                map.put("receiver",AddTaskReciever.getText().toString());

            }
        });*/


            /*holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("tasks")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });*/
    }


}
