package com.techmania.onebankafrica.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.techmania.onebankafrica.Fragments.Inbound;
import com.techmania.onebankafrica.Fragments.outbound;
import com.techmania.onebankafrica.Models.Transactions;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = Inbound.newInstance();
                break;
            case 1:
                fragment = outbound.newInstance();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
