package com.example.toshiba.mymovie.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.view.fragment.FavFragment;
import com.example.toshiba.mymovie.view.fragment.HomeFragment;
import com.example.toshiba.mymovie.view.reminder.AlarmReceiver;
import com.example.toshiba.mymovie.view.reminder.SchedulerService;
import com.example.toshiba.mymovie.view.reminder.SchedulerTask;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new HomeFragment();
        switchFragment(fragment);
        getSupportActionBar().setTitle(getString(R.string.home));

        SchedulerTask schedulerTask = new SchedulerTask(this);
        schedulerTask.createTask();

        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.setRepeatingAlarm(this, "07:00", getString(R.string.message_daily_reminder));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cari) {
            startActivity(new Intent(this, CariActivity.class));
        } else if (id == R.id.nav_ubah) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            switchFragment(fragment);
            getSupportActionBar().setTitle(getString(R.string.home));
        } else if (id == R.id.nav_fav) {
            fragment = new FavFragment();
            switchFragment(fragment);
            getSupportActionBar().setTitle(getString(R.string.favorit));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

}
