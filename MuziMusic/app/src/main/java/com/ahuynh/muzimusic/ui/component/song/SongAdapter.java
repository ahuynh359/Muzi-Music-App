package com.ahuynh.muzimusic.ui.component.song;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahuynh.muzimusic.databinding.ItemSongBinding;
import com.ahuynh.muzimusic.data.model.Song;
import com.ahuynh.muzimusic.utils.MusicLibraryHelper;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    public static List<Song> songList = new ArrayList<>();


    public SongAdapter(List<Song> songList) {
        SongAdapter.songList = songList;

    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSongBinding binding = ItemSongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        private ItemSongBinding binding;

        public ViewHolder(@NonNull ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(binding.getRoot().getContext(), "Clicj item " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void bind(Song song) {

            binding.tvNameSong.setText(song.getDisplayName());
            binding.tvSinger.setText(song.getArtist());
            Bitmap imv = MusicLibraryHelper.getThumbnail(binding.getRoot().getContext(), song.getAlbumArt());
            if (imv != null)
                binding.imvSong.setImageBitmap(imv);

        }

    }

}

