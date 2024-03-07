package com.ahuynh.muzimusic.ui.component.song;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahuynh.muzimusic.MainViewModel;
import com.ahuynh.muzimusic.databinding.FragmentSongBinding;
import com.ahuynh.muzimusic.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment {

    private FragmentSongBinding binding;
    private MainViewModel viewModel;
    private SongAdapter adapter;
    private List<Song> songList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        songList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSongBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpSongList();


    }

    private void setUpSongList() {
        viewModel.loadSongsFromLocal(getActivity());
        viewModel.getSongList().observe(getViewLifecycleOwner(), songs -> {
            adapter =  new SongAdapter(songList);
            binding.rcySongs.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rcySongs.setAdapter(adapter);
        });

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}