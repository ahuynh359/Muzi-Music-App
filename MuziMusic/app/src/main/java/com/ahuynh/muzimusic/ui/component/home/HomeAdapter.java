package com.ahuynh.muzimusic.ui.component.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahuynh.muzimusic.databinding.FragmentHomeBinding;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private FragmentHomeBinding binding;
        public ViewHolder(@NonNull FragmentHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
