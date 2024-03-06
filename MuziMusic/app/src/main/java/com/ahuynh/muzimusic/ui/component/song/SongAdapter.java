package com.ahuynh.muzimusic.ui.component.song;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahuynh.muzimusic.databinding.ItemGeneralRoundBigVerticalBinding;
import com.ahuynh.muzimusic.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private final List<Song> songList;
    //public final ISongSelectListener listener;

    public SongAdapter(List<Song> songList) {
        this.songList = songList;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGeneralRoundBigVerticalBinding binding = ItemGeneralRoundBigVerticalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        holder.bind(songList.get(position));

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemGeneralRoundBigVerticalBinding binding;

        public ViewHolder(@NonNull ItemGeneralRoundBigVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Song song) {
            binding.tvName.setText(song.displayName);

        }

    }

}

