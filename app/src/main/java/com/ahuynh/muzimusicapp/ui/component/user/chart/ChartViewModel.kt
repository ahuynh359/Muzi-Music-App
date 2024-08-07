package com.ahuynh.muzimusicapp.ui.component.user.chart

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.SongListen
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val songRepository: SongRepository,
) : BaseViewModel() {

    val chartList = MutableLiveData<List<Song>>()
    val top3List = MutableLiveData<List<SongListen>>()
    val songDrawables = MutableLiveData<List<Drawable?>>()

    fun getTopSongDrawable(context: Context) {
        viewModelScope.launch {
            val loader = ImageLoader(context)
            val list = ArrayList<Drawable?>()

            for (i in 0 until (chartList.value?.size ?: 0)) {
                val request = ImageRequest.Builder(context)
                    .data(chartList.value?.get(i)?.avatar)
                    .allowHardware(false)
                    .build()
                try {
                    val result =
                        (loader.execute(request) as SuccessResult)
                            .drawable
                    val resizedDrawable = resizeDrawable(context, result, Utils.convertDpToPixel(48f,context),  Utils.convertDpToPixel(48f,context))
                    list.add(resizedDrawable)
                } catch (e: Exception) {
                    list.add(null)
                }
            }
            songDrawables.postValue(list)
        }
    }
    private fun resizeDrawable(context: Context, drawable: Drawable, width: Int, height: Int): Drawable {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        return BitmapDrawable(context.resources, resizedBitmap)
    }

    fun getChartList() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            chartList.postValue(songRepository.getTop10())
            top3List.postValue(songRepository.getTop3())
        }
        registerEventParentJobFinish()
    }
}


