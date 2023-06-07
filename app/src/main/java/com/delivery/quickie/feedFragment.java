package com.delivery.quickie;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delivery.quickie.databinding.FragmentFeedBinding;

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
        binding.feedRecyclerview.setAdapter(new feedAdapter());
        return view;
    }
}