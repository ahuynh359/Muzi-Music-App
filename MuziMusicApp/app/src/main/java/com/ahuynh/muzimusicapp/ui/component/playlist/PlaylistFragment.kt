package com.ahuynh.muzimusicapp.ui.component.playlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util.startForegroundService
import androidx.navigation.fragment.navArgs
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistBinding
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.library.OnSongClicked
import com.ahuynh.muzimusicapp.ui.component.library.SongAdapter
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.DATA
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Constants.SONG_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistFragment : BaseFragment<FragmentPlaylistBinding>(FragmentPlaylistBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: PlaylistFragmentArgs by navArgs()
        super.onViewCreated(view, savedInstanceState)
        val songs: ArrayList<Song> = args.songs.toMutableList() as ArrayList<Song>
        val adapter = SongAdapter(object : OnSongClicked {
            override fun onSongClicked(song: Song) {
                startActivity(Intent(requireContext(), PlayerActivity::class.java))
                sendMusicAction(
                    Constants.ACTION_PLAY,
                    song,
                    songList = songs
                )
            }

        })

        binding.rcySong.adapter = adapter



        adapter.submitList(args.songs.toMutableList())
        Log.d("PlaylistFragment", args.songs.toMutableList().toString())


    }

    @OptIn(UnstableApi::class)
    private fun sendMusicAction(
        action: Int,
        song: Song? = null,
        songList: ArrayList<Song> = arrayListOf()
    ) {
        val intent = Intent(requireContext().applicationContext, MusicService::class.java)

        intent.putExtra("action", action)
        song?.let {
            val bundle = Bundle().apply {
                putParcelable(SONG, it)
                putParcelableArrayList(SONG_LIST, songList)
            }
            intent.putExtra(DATA, bundle)
        }

        startForegroundService(requireContext().applicationContext, intent)
    }

}
