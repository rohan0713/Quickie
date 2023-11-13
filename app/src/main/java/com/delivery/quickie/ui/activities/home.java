package com.delivery.quickie.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.delivery.quickie.R;
import com.delivery.quickie.data.PostsResponse;
import com.delivery.quickie.databinding.ActivityHomeBinding;
import com.delivery.quickie.network.RetrofitClient;
import com.delivery.quickie.ui.fragments.exploreFragment;
import com.delivery.quickie.ui.fragments.feedFragment;
import com.delivery.quickie.ui.fragments.homeFragment;
import com.delivery.quickie.ui.fragments.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Response;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
    com.delivery.quickie.ui.fragments.homeFragment homeFragment = new homeFragment();
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

        binding.navMenu.setOnNavigationItemSelectedListener(item -> {
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
            return true;
        });
    }
}