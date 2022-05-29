package com.example.magasin_cms.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magasin_cms.Model.UserModel;
import com.example.magasin_cms.R;
import com.example.magasin_cms.descfragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Workers_adapter extends FirestoreRecyclerAdapter<UserModel,Workers_adapter.myviewholder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Workers_adapter(@NonNull FirestoreRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull UserModel model) {
        holder.Fname_text.setText(model.getFname());
        holder.Department_text.setText(model.getDepartment());
        holder.Email_text.setText(model.getEmail());
        // This Glide is used to hold the ImageView
        //Glide.with(holder.img1.getContext()).load(model.getPurl()).into(holder.img1);

                holder.Fname_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity activity=(AppCompatActivity)view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new descfragment(model.getFname(),model.getDepartment(),model.getEmail())).addToBackStack(null).commit();
                    }
                });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView img1;
        TextView Fname_text , Email_text , Department_text;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1=itemView.findViewById(R.id.img1);
            Fname_text=itemView.findViewById(R.id.Fname_text);
            Department_text=itemView.findViewById(R.id.Department_text);
            Email_text=itemView.findViewById(R.id.Email_text);

        }
    }
}
