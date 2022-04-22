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

public class CategoriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // FOR DESIGN
   private Toolbar toolbar;
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;

    //ActionBarDrawerToggle toggle;

    //FOR FRAGMENTS
    // 1 - Declare fragment handled by Navigation Drawer
    private Fragment fragmentProfile;
    private Fragment fragmentHome;
    //FOR DATAS
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_PROFILE = 1;
    private static final int FRAGMENT_HOME = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.showFirstFragment();

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

    private void showFirstFragment() {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showFragment(FRAGMENT_HOME);
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(2).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    // 3 - Configure NavigationView
    private void configureNavigationView() {
        this.navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 1 - Configure Toolbar
    private void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation  Item clicks
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                this.showFragment(FRAGMENT_HOME);
            break;

            case R.id.nav_profile:
                this.showFragment(FRAGMENT_PROFILE);
                break;

            case R.id.nav_workers:
                break;

            case R.id.nav_Calendar:
                break;

            case R.id.nav_logout:
                break;

            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // 5 - Show fragment according an Identifier

    private void showFragment(int fragmentIdentifier) {
        switch (fragmentIdentifier){
            case FRAGMENT_HOME:
                this.showHomeFragment();
            case FRAGMENT_PROFILE:
                this.showProfileFragment();
                break;
            default:
                break;
        }
    }

    private void showHomeFragment() {
        if (this.fragmentHome == null)
            this.fragmentHome = HomeFragment.newInstance();
        this.startHomeFragment(this.fragmentHome);
    }

    private void showProfileFragment() {
        if (this.fragmentProfile == null)
            this.fragmentProfile = ProfileFragment.newInstance();
        this.startProfileFragment(this.fragmentProfile);
    }


    // 3 - Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startProfileFragment(Fragment fragmentProfile) {
        if (!fragmentProfile.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout,fragmentProfile).commit();
        }
    }
        private void startHomeFragment(Fragment fragmentHome){
        if(!fragmentHome.isVisible())
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout,fragmentHome).commit();

        }
    }

}
