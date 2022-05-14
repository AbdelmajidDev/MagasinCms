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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CategoriesActivity extends AppCompatActivity {

    // FOR DESIGN
   private Toolbar toolbar;
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;

    //ActionBarDrawerToggle toggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        drawerLayout=findViewById(R.id.drawer_layout);
        /*// 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
       // this.configureNavigationView();


       /* navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setCheckedItem(R.id.nav_profile);
        navigationView.setNavigationItemSelectedListener(this);*/

       /* FragmentManager fragmentManager = getSupportFragmentManager();
        Home_fragment fragment= new Home_fragment();
        fragmentManager.beginTransaction().replace(R.id.Frame1,fragment).commit();*/

/*line.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ClickMenu(drawerLayout);
    }
});*/
    }



  /*  @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    3 - Configure NavigationView
    private void configureNavigationView() {
        this.navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }

    2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

     1 - Configure Toolbar
    private void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }*/

public void ClickMenu(View view){
        openDrawer(drawerLayout);

}

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

public void ClickHome(View view){
        recreate();

}
public void ClickProfile(View view){
    redirectActivity(this,HomeActivity.class);

}
public void ClickRate(View view){
    redirectActivity(this,RateUsActivity.class);
}

public void ClickTemperature(View view){
    redirectActivity(this,TemperatureActivity.class);
}

public void ClickTaskManagment(View view){
    redirectActivity(this,SentOrReceived.class);
}

public void ClickCalendar(View view){
    redirectActivity(this,CalendarActivity.class);
}
/*public void ClickAbout(View view){

    redirectActivity(this,);
}*/
public void ClickLogout(View view){

        Logout(this);
}

    private static void Logout(Activity activity) {
AlertDialog.Builder builder=new AlertDialog.Builder(activity);
builder.setTitle("Logout");
builder.setMessage("Are you sure you want to logout?");
builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
   activity.finishAffinity();
   System.exit(0);
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

    private static void redirectActivity(Activity activity,Class aclass) {
        Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    public void ClickToDo(View view) {
    redirectActivity(this,ToDo.class);
    }

    // 5 - Show fragment according an Identifier


    }


