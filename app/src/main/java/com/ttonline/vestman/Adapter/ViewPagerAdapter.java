package com.ttonline.vestman.Adapter;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ttonline.vestman.fragment.CompleteFragment;
import com.ttonline.vestman.fragment.IncompletedBillFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new IncompletedBillFragment();
            case 1: return new CompleteFragment();
            default: return new IncompletedBillFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
