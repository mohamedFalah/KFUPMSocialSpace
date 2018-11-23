package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Since we removed the defult action bar we have to tell the app to use the toolbar we created and select the 7th Toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //Here is for the fragments after creating them to listen to the button clicks
        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationview = findViewById(R.id.nav_view);
        //https://www.youtube.com/watch?v=bjYstsO1PgI 5:00 we implements the interface
        navigationview.setNavigationItemSelectedListener(this);

        /*
        This is to show the action bar sliding. before that we add 2 strings in strings.xml
        the navigation_drawer_open And navigation_drawer_close for Blind people so it can be read by the device.
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);


        //This is the first things you see when you open the app before clicking on any activity from the toolbar
        // https://www.youtube.com/watch?v=bjYstsO1PgI 9:20 when device rotates we don't call the method again
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        navigationview.setCheckedItem(R.id.nav_profile);

        //just rotates the three line hamburger symbol with the device
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                //getSupportFragmentManager shows the fragment in the framelayout
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_utilities:
                //getSupportFragmentManager shows the fragment in the framelayout
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UtilitiesFragment()).commit();
                break;

            case R.id.nav_files:
                //getSupportFragmentManager shows the fragment in the framelayout
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FilesFragment()).commit();
                break;

            case R.id.nav_logout:
                Toast.makeText(this, "Replace with Log out action", Toast.LENGTH_SHORT).show();
                break;
        }

        //After clicking an item we need to close the drawer so we can get to the page and see it
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    //if back is pressed it closes the toolbar first
    //START is for the left side
    //END is for the right side toolbars
    @Override
    public void onBackPressed() {
        //if the toolbar is open close it
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //closes the activity
            super.onBackPressed();
        }
    }
}
