package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

public class TemperatureActivity extends AppCompatActivity {
  TextView CurrentTemp,CurrentHum;
    private DatabaseReference mDatabase;
    private DatabaseReference mb;
    int mSecond,i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        CurrentTemp=findViewById(R.id.CurrentTemp);
        CurrentHum=findViewById(R.id.CurrentHum);
        mDatabase = FirebaseDatabase.getInstance().getReference("humidite");
        mb= FirebaseDatabase.getInstance().getReference("Tempurature");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String  data=snapshot.getValue().toString();
                    CurrentHum.setText(data+"%");
                        if(Float.valueOf(data)>90)
                        {
                    FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/Temp","Alert","High Humidity"
                            ,getApplicationContext(),TemperatureActivity.this);
                    notificationsSender.SendNotifications();
                        }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data1=snapshot.getValue().toString();
                    CurrentTemp.setText(data1);

                    if(Float.valueOf(data1)>50)
                    {
                        FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/Temp","Alert","High Temperature"
                                ,getApplicationContext(),TemperatureActivity.this);
                        notificationsSender.SendNotifications();
                    }
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}

