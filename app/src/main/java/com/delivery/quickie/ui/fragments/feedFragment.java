package com.delivery.quickie.ui.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.delivery.quickie.R;
import com.delivery.quickie.data.PostsResponse;
import com.delivery.quickie.databinding.FragmentFeedBinding;
import com.delivery.quickie.network.RetrofitClient;
import com.delivery.quickie.ui.adapters.feedAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class feedFragment extends Fragment {

    FragmentFeedBinding binding;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        view = binding.getRoot();
        binding.feedRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        getPosts();
        return view;
    }

    private void getPosts() {

        Call<PostsResponse> response = RetrofitClient.Companion.getApi().getPosts();
        response.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "fetching success", Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        binding.feedRecyclerview.setAdapter(new feedAdapter(response.body().getPosts()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), "fetching failed", Toast.LENGTH_SHORT).show();
            }
        });

//        Response<PostsResponse> response = RetrofitClient.Companion.getApi().getPosts();
//        if(response.isSuccessful()){
//            if(response.body() != null){
//                binding.feedRecyclerview.setAdapter(new feedAdapter(response.body().getPosts()));
//            }
//        }
    }
}