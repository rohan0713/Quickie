package com.delivery.quickie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.delivery.quickie.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
    homeFragment homeFragment = new homeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();
        }

        binding.navMenu.setBackground(null);
        binding.navMenu.getMenu().getItem(2).setEnabled(false);

        binding.navMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if(item.getItemId() == R.id.home){
                    fragment = new homeFragment();
                }else if(item.getItemId() == R.id.explore){
                    fragment = new exploreFragment();
                }else if (item.getItemId() == R.id.feed){
                    fragment = new feedFragment();
                } else if (item.getItemId() == R.id.profile) {
                    fragment = new profileFragment();
                }

                assert fragment!=null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });
    }
}