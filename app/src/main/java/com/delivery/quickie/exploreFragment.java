package com.delivery.quickie;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.delivery.quickie.databinding.FragmentExploreBinding;
import com.delivery.quickie.room.cResponse;
import com.delivery.quickie.room.cuisine;
import com.delivery.quickie.room.cuisineAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class exploreFragment extends Fragment {

    FragmentExploreBinding binding;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false);
        view = binding.getRoot();

        binding.cuisineRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        getCuisine();
        return view;
    }

    private void getCuisine() {

        Call<cResponse> call = Retrofit.getServices().cuisine();
        call.enqueue(new Callback<cResponse>() {
            @Override
            public void onResponse(Call<cResponse> call, Response<cResponse> response) {
                List<cuisine> list = response.body().getMeals();
                binding.cuisineRecyclerview.setAdapter(new cuisineAdapter(list));
                binding.more.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<cResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), "failed", Toast.LENGTH_LONG).show();
            }
        });

    }
}