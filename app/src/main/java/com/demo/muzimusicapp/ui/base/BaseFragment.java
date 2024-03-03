package com.demo.muzimusicapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    private final InflateMethod<T> inflateMethod;
    private T binding;

    public BaseFragment(InflateMethod<T> inflateMethod) {
        this.inflateMethod = inflateMethod;
    }

    protected T getBinding() {
        return binding;
    }

    abstract protected void initialize(T binding);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = inflateMethod.inflate(inflater, container, false);
        initialize(binding);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected interface InflateMethod<T extends ViewBinding> {
        T inflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot);
    }
}
