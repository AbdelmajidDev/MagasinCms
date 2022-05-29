package com.example.magasin_cms;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class CategoriesActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    ImageView OpenNotif;
    // FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    SharedPreferences sharedPreferences;

   // public static final String fileName = "login";
    //public static final String Username = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        drawerLayout = findViewById(R.id.drawer_layout);
        OpenNotif=findViewById(R.id.OpenNotif);









       /* sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Username)) {
            Toast.makeText(this, "You are welcome", Toast.LENGTH_SHORT).show();
        }*/



    }

    public void OpenNotif(View view){
        redirectActivity(this, NotificationActivity.class);

    }

    @Override
    public void onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
             backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
             backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    public void ClickMenu(View view) {
        openDrawer(drawerLayout);

    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickHome(View view) {
        recreate();

    }

    public void ClickProfile(View view) {
        redirectActivity(this, HomeActivity.class);

    }

    public void ClickRate(View view) {
        redirectActivity(this, RateUsActivity.class);
    }

    public void ClickTemperature(View view) {
        redirectActivity(this, TemperatureActivity.class);
    }

    public void ClickTaskManagment(View view) {
        redirectActivity(this, SentOrReceived.class);
    }

    public void ClickCalendar(View view) {
        redirectActivity(this, CalendarActivity.class);
    }

    public void ClickWorkers(View view) {
        redirectActivity(this, Registred_Workers.class);
    }

    /*public void ClickAbout(View view){

        redirectActivity(this,);
    }*/
    public void ClickLogout(View view) {

        Logout(this);
    }

    private static void Logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                activity.finishAffinity();
                //System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    private static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    public void ClickToDo(View view) {
        redirectActivity(this, ToDo.class);
    }


    // 5 - Show fragment according an Identifier


}


