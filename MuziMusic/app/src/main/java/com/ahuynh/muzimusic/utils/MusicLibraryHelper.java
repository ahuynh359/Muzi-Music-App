package com.ahuynh.muzimusic.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.ahuynh.muzimusic.R;
import com.ahuynh.muzimusic.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicLibraryHelper {


    public  List<Song> fetchMusicLibrary(Context context) {
        String collection;
        List<Song> songList = new ArrayList<>();

        if (VersionHelper.isVersionQ())
            collection = MediaStore.Audio.Media.BUCKET_DISPLAY_NAME;
        else
            collection = MediaStore.Audio.Media.DATA;

        String[] projection = new String[]{
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,  // error from android side, it works < 29
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                collection,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATE_MODIFIED,
                MediaStore.Audio.Media.DATA
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        Cursor musicCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, sortOrder);

        int artistInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
        int yearInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR);
        int trackInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK);
        int titleInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int displayNameInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
        int durationInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
        int albumIdInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
        int albumInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
        int relativePathInd = musicCursor.getColumnIndexOrThrow(collection);
        int idInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int dateModifiedInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED);
        int contentUriInd = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

        while (musicCursor.moveToNext()) {
            String artist = musicCursor.getString(artistInd);
            String title = musicCursor.getString(titleInd);
            String displayName = musicCursor.getString(displayNameInd);
            String album = musicCursor.getString(albumInd);
            String relativePath = musicCursor.getString(relativePathInd);
            String absolutePath = musicCursor.getString(contentUriInd);

            if (VersionHelper.isVersionQ())
                relativePath += "/";
            else if (relativePath != null) {
                File check = new File(relativePath).getParentFile();
                if (check != null) {
                    relativePath = check.getName() + "/";
                }
            } else {
                relativePath = "/";
            }

            int year = musicCursor.getInt(yearInd);
            int track = musicCursor.getInt(trackInd);
            int startFrom = 0;
            int dateAdded = musicCursor.getInt(dateModifiedInd);

            long id = musicCursor.getLong(idInd);
            long duration = musicCursor.getLong(durationInd);
            long albumId = musicCursor.getLong(albumIdInd);



            Uri albumArt = Uri.parse("");
            if (!relativePath.contains(album)) {
                albumArt = ContentUris.withAppendedId(Uri.parse(context.getResources().getString(R.string.album_art_dir)), albumId);
            }

            songList.add(new Song(
                    artist, title, displayName, album, relativePath, absolutePath,
                    year, track, startFrom, dateAdded,
                    id, duration, albumId, albumArt
            ));
        }

        if (!musicCursor.isClosed())
            musicCursor.close();

        return songList;
    }
}
