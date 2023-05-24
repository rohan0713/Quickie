package com.delivery.quickie;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delivery.quickie.databinding.FragmentHomeBinding;

import java.util.Timer;
import java.util.TimerTask;


public class homeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home ,container,
                false);
        View view = binding.getRoot();

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

        binding.foodRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.foodRecyclerview.setAdapter(new foodAdapter());
        return view;
    }
}