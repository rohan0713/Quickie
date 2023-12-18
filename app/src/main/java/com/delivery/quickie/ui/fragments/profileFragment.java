package com.delivery.quickie.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.delivery.quickie.R;
import com.delivery.quickie.network.ProfileResponse;
import com.delivery.quickie.network.RetrofitClient;
import com.delivery.quickie.ui.activities.LoginActivity;
import com.delivery.quickie.ui.adapters.fragmentAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class profileFragment extends Fragment {

    View view;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    ShapeableImageView profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        profile = view.findViewById(R.id.profilePhoto);
        MaterialButton logOut = view.findViewById(R.id.btnLogOut);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.posts));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.reels));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tagged));

        fragmentAdapter adapter = new fragmentAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                Intent i = new Intent(view.getContext(), LoginActivity.class);
                startActivity(i);
                requireActivity().finish();
            }
        });

        return view;


    }
}