package com.ahuynh.muzimusicapp.ui.component.user.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter.AlbumViewType
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SingerAdapter.SingerViewType
import com.ahuynh.muzimusicapp.adapter.SliderAdapter
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.SongEntityAdapter
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.adapter.TypeAdapter.TypeViewType
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.SliderItem
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    AlbumAdapter.OnAlbumClicked, SongAdapter.OnSongClicked, SingerAdapter.OnSingerClicked,
    SongEntityAdapter.OnSongEntityClick, TypeAdapter.OnTypeClicked {

    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })

    companion object {
        const val TAG = "HomeFragment"
    }

    private val songEntityAdapter = SongEntityAdapter(this, SongEntityAdapter.TYPE_SONG_ENTITY_HOME)
    private var newSongAdapter = SongAdapter(this)
    private var recommendSongAdapter = SongAdapter(this)
    private val newAlbumAdapter = AlbumAdapter(this, AlbumViewType.HOME)
    private val newTypeAdapter = TypeAdapter(this, TypeViewType.HOME)
    private val newSingerAdapter = SingerAdapter(this, SingerViewType.HOME)
    private val singerYouFollowedAdapter = SingerAdapter(this, SingerViewType.HOME)
    private val slideAdapter = SliderAdapter()

    private var recentSongList: ArrayList<SongEntity> = arrayListOf()
    private var newSongList: ArrayList<Song> = arrayListOf()
    private var recommendSongList: ArrayList<Song> = arrayListOf()
    private var newTypeList: ArrayList<Type> = arrayListOf()
    private var newAlbumList: ArrayList<Album> = arrayListOf()
    private var newSingerList: ArrayList<Singer> = arrayListOf()
    private var singerYouFollowedList: ArrayList<Singer> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observe()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun handleUI() {

        binding.rcyNewSong.apply {
            adapter = newSongAdapter
            layoutManager = getGridLayoutHorizontal(3)
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
        binding.rcyRecommendationSong.apply {
            adapter = recommendSongAdapter
            layoutManager = getGridLayoutHorizontal(3)
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
        binding.rcyNewSinger.adapter = newSingerAdapter
        binding.rcySingerYouFollowed.adapter = singerYouFollowedAdapter
        binding.rcyNewAlbum.adapter = newAlbumAdapter
        binding.rcyRecentSongs.adapter = songEntityAdapter
        binding.rcyNewType.adapter = newTypeAdapter
        binding.imageSlider.apply {
            setSliderAdapter(slideAdapter)
            setIndicatorAnimation(IndicatorAnimationType.WORM);
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            startAutoCycle();
        }

        binding.topAppBar.setSubtitle(Utils.getGreetingMessage(requireContext()))
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ic_notification -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToNotificationFragment()
                    findNavController().navigate(action)
                    true
                }

                R.id.ic_setting -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
                    findNavController().navigate(action)
                    true
                }

                R.id.ic_history -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToRecentFragment()
                    findNavController().navigate(action)
                    true
                }


                else -> false
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            getData()
        }

    }


    private fun getData() {
        viewModel.getNewSongs()
        viewModel.getNewAlbums()
        viewModel.getNewSingers()
        viewModel.getRecentSongs()
        viewModel.getNewTypes()
        viewModel.getUnreadNotification()
        viewModel.getLoveSingers()
        viewModel.getRecommendations()
    }

    private fun observe() {
        handleRecentSong()
        handleNewSongList()
        handleRecommendationSongList()
        handleNewAlbumList()
        handleNewSingerList()
        handleNewTypeList()
        handleSliderList()
        handleLoveSingerList()

    }

    private fun handleSliderList() {
        val sliderItems = arrayListOf(
            SliderItem(
                image = R.drawable.slider_1,
                title = "Title 1",
                description = "Sing Along"
            ),
            SliderItem(
                image = R.drawable.slider_2,
                title = "Title 2",
                description = "Hits"
            ),
            SliderItem(
                image = R.drawable.slider_3,
                title = "Title 3",
                description = "Favorite"
            )
        )
        slideAdapter.submitData(sliderItems)
    }

    private fun handleNewTypeList() {
        viewModel.newTypeList.observe(viewLifecycleOwner) {
            binding.rcyNewType.visibility = View.VISIBLE
            if (it != null) {
                newTypeList = it as ArrayList<Type>
                newTypeAdapter.submitList(it)
            }
            binding.shimmerNewType.stopShimmer()
            binding.shimmerNewType.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun handleRecentSong() {

        viewModel.recentSong.observe(viewLifecycleOwner) {
            binding.rcyRecentSongs.visibility = View.VISIBLE
            songEntityAdapter.submitList(it)
            recentSongList = it as ArrayList<SongEntity>
            binding.shimmerRecentSongs.stopShimmer()
            binding.shimmerRecentSongs.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }


    private fun handleNewSongList() {

        viewModel.newSongList.observe(viewLifecycleOwner) {
            binding.rcyNewSong.visibility = View.VISIBLE
            newSongList = it as ArrayList<Song>
            newSongAdapter.submitList(it)

            binding.shimmerNewSong.stopShimmer()
            binding.shimmerNewSong.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun handleRecommendationSongList() {

        viewModel.recommendedSongList.observe(viewLifecycleOwner) {
            binding.rcyRecommendationSong.visibility = View.VISIBLE
            newSongList = it as ArrayList<Song>
            recommendSongAdapter.submitList(it)

            binding.shimmerRecommendation.stopShimmer()
            binding.shimmerRecommendation.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun getGridLayoutHorizontal(spanCount: Int) =
        GridLayoutManager(requireContext(), spanCount, GridLayoutManager.HORIZONTAL, false)


    private fun handleNewAlbumList() {
        viewModel.newAlbumList.observe(viewLifecycleOwner) {
            binding.rcyNewAlbum.visibility = View.VISIBLE
            if (it != null) {
                newAlbumList = it as ArrayList<Album>
                newAlbumAdapter.submitList(it)
            }
            binding.shimmerNewAlbum.stopShimmer()
            binding.shimmerNewAlbum.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun handleNewSingerList() {

        viewModel.newSingerList.observe(viewLifecycleOwner) {
            binding.rcyNewSinger.visibility = View.VISIBLE
            if (it != null) {
                newSingerList = it as ArrayList<Singer>
                newSingerAdapter.submitList(it)
            }
            binding.shimmerNewSinger.stopShimmer()
            binding.shimmerNewSinger.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun handleLoveSingerList() {

        viewModel.loveSingerList.observe(viewLifecycleOwner) {
            binding.rcySingerYouFollowed.visibility = View.VISIBLE
            if (it != null) {
                singerYouFollowedList = it as ArrayList<Singer>
                singerYouFollowedAdapter.submitList(it)
            }
            binding.shimmerSingleYouFollowed.stopShimmer()
            binding.shimmerSingleYouFollowed.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false

        }
    }


    override fun onSongClicked(song: Song) {
        Utils.sendMusic(
            requireContext(), MusicService.ACTION_PLAY, song, newSongList
        )
    }

    override fun openMenu(song: Song) {
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG, song)
        }
        fragment.show(requireActivity().supportFragmentManager, null)
    }


    override fun onAlbumClicked(album: Album) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailAlbumFragment(album)
        findNavController().navigate(action)
    }

    override fun onMoreItemAlbumClicked(album: Album) {

    }


    override fun onSingerClicked(singer: Singer) {
        val action = HomeFragmentDirections.actionHomeFragmentToSingerDetailFragment(singer)
        findNavController().navigate(action)
    }

    override fun onSongEntityClick(songEntity: SongEntity) {
        val songs = mutableListOf<Song>()
        for (song in recentSongList) {
            songs.add(song.toSong())
        }

        val song = songEntity.toSong()
        Utils.sendMusic(
            requireContext(), MusicService.ACTION_PLAY, song,songs as ArrayList)

    }

    override fun openMenu(songEntity: SongEntity) {

    }

    override fun onTypeClicked(type: Type) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailTypeFragment(type)
        findNavController().navigate(action)
    }

    override fun onMoreClicked(type: Type) {
    }


}



