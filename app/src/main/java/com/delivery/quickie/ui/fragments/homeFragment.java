package com.delivery.quickie.ui.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.delivery.quickie.utils.DotsIndicatorDecoration;
import com.delivery.quickie.R;
import com.delivery.quickie.network.Retrofit;
import com.delivery.quickie.databinding.FragmentHomeBinding;
import com.delivery.quickie.room.Repository;
import com.delivery.quickie.room.foodViewModel;
import com.delivery.quickie.data.food_items;
import com.delivery.quickie.data.response;
import com.delivery.quickie.ui.adapters.foodAdapter;
import com.delivery.quickie.ui.adapters.offerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class homeFragment extends Fragment {


    FragmentHomeBinding binding;

    private static Repository repository;
    private static foodViewModel viewModel; 
    private com.delivery.quickie.ui.adapters.foodAdapter foodAdapter;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home ,container,
                false);
        view = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerview.setLayoutManager(layoutManager);
        offerAdapter adapter = new offerAdapter();
        binding.recyclerview.setAdapter(adapter);

        //for dots indicator
        final int radius = getResources().getDimensionPixelSize(R.dimen.radius);
        final int dotsHeight = getResources().getDimensionPixelSize(R.dimen.dots_height);
        final int color = ContextCompat.getColor(view.getContext(), R.color.black);
        binding.recyclerview.addItemDecoration(new DotsIndicatorDecoration(radius, radius * 4, dotsHeight, color, color));
        new PagerSnapHelper().attachToRecyclerView(binding.recyclerview);

        //for auto scrolling
        binding.recyclerview.setOnFlingListener(null);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerview);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (layoutManager.findLastVisibleItemPosition() < (adapter.getItemCount() - 1)){
                    layoutManager.smoothScrollToPosition(binding.recyclerview, new RecyclerView.State(), layoutManager.findLastCompletelyVisibleItemPosition() + 1);
                }else{
                    layoutManager.smoothScrollToPosition(binding.recyclerview, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);

        List<food_items> list = new ArrayList<>();
        foodAdapter = new foodAdapter(list);
        binding.foodRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        repository = new Repository(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this).get(com.delivery.quickie.room.foodViewModel.class);

        getMeals();
        viewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<food_items>>() {
            @Override
            public void onChanged(List<food_items> list) {
                binding.foodRecyclerview.setAdapter(foodAdapter);
                foodAdapter.getList(list);
            }
        });
        return view;
    }

    private void getMeals() {

        Call<response> call = Retrofit.getServices().meals("Indian");
        call.enqueue(new Callback<response>() {
            @Override
            public void onResponse(Call<response> call, Response<response> response) {

                List<food_items> list = response.body().getMeals();
                repository.insert(list);
            }

            @Override
            public void onFailure(Call<response> call, Throwable t) {
                Toast.makeText(view.getContext(), "No Internet", Toast.LENGTH_LONG).show();
            }
        });

    }
}