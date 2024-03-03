package com.demo.muzimusicapp.ui.component.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.muzimusicapp.databinding.FragmentHomeBinding;
import com.demo.muzimusicapp.ui.base.BaseFragment;


public class HomeFragment extends BaseFragment<FragmentHomeBinding>{

    public HomeFragment(BaseFragment.InflateMethod<FragmentHomeBinding> inflateMethod) {
        super(inflateMethod);
    }

    @Override
    protected void initialize(FragmentHomeBinding binding) {
        binding.tv.setText("ABC");
    }
}