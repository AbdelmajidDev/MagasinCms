package com.example.magasin_cms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class descfragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String Fname , Email , Department;
    public descfragment() {

    }

    public descfragment(String Fname, String Email , String Department) {
        this.Fname= Fname;
        this.Email= Email;
        this.Department= Department;
    }

    public static descfragment newInstance(String param1, String param2) {
        descfragment fragment = new descfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_descfragment, container, false);

        ImageView imageholder=view.findViewById(R.id.image_holder);
        TextView nameholder=view.findViewById(R.id.nameholder);
        TextView department_holder=view.findViewById(R.id.department_holder);
        TextView emailholder=view.findViewById(R.id.emailholder);

        nameholder.setText(Fname);
        department_holder.setText(Department);
        emailholder.setText(Email);
        // This Glide is used to hold the ImageView
        //Glide.with(getContext()).load(purl).into(imageholder);

        return view;
    }
    

    }