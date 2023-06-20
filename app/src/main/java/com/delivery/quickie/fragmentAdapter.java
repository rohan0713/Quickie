package com.delivery.quickie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class fragmentAdapter extends FragmentStateAdapter {

    public fragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 1){
            return new reelFragment();
        }else if(position == 2){
            return new tagFragment();
        }
        return new postFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
