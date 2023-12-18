package com.delivery.quickie.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.delivery.quickie.ui.adapters.postAdapter;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class postFragment extends Fragment {

    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));

        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        Log.d("email", email.toString());

            try {
                Call<ProfileResponse> res = RetrofitClient.Companion.getDbApi().getProfile(email.toString());
                res.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            recyclerView.setAdapter(new postAdapter(response.body().getPosts()));
                        } else {
                            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                Log.e("error", e.getMessage());
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

        return view;

    }
}