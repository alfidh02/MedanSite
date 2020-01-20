package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
//    Initiate fragment
    UserFragment userFragment = new UserFragment();
    HomeFragment homeFragment = new HomeFragment();
//    SearchFragment searchFragment = new SearchFragment();
    ExploreFragment exploreFragment = new ExploreFragment();
    GlobeFragment globeFragment = new GlobeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.miHome);

    }

    private void openFragment(final Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.miHome:
                openFragment(homeFragment);
                return true;
            case R.id.miCompass:
                openFragment(exploreFragment);
                return true;
            case R.id.miProfile:
                openFragment(userFragment);
                return true;
            case R.id.miGlobe:
                openFragment(globeFragment);
                return true;
        }
        return false;
    }
}
