package com.ahuynh.muzimusic.ui.component.song;

import com.ahuynh.muzimusic.data.model.Song;

import java.util.List;

public interface ISongSelectListener {
    void playQueue(List<Song> songList, boolean shuffle);

    void addToQueue(List<Song> songList);

    void refreshMediaLibrary();
}
