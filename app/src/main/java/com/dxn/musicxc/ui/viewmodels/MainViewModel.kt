package com.dxn.musicxc.ui.viewmodels

import android.app.Application
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.musicxc.models.Music
import com.dxn.musicxc.repository.MusicRepository
import com.dxn.musicxc.services.MusicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MusicRepository()

    val musicList: MutableState<List<Music>> = mutableStateOf(ArrayList())
    val totalMusic: MutableState<Int> = mutableStateOf(0)
//    val isBottomSheetExpanded : MutableState<Boolean> = mutableStateOf(false)

    val currentPlaying: MutableState<Music?> = mutableStateOf(null)
    val currentPlayingIndex: MutableState<Int> = mutableStateOf(0)
    val isPlaying: MutableState<Boolean> = mutableStateOf(false)
    val isRepeatOn: MutableState<Boolean> = mutableStateOf(false)
    val mBinder: MutableState<MusicService.MyBinder?> = mutableStateOf(null)
    val currentProgress = mutableStateOf(0L)
    val maxDuration = mutableStateOf(0L)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("MainViewModel", "onServiceConnected: Service Connected")
            val binder: MusicService.MyBinder = service as MusicService.MyBinder
            mBinder.value = binder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBinder.value = null
        }

    }

    fun getServiceConnection(): ServiceConnection {
        return serviceConnection
    }

    fun fetchAllSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            musicList.value = repository.getAllMusic(getApplication())
            totalMusic.value = musicList.value.size
        }
    }

    fun setCurrentPlaying(index: Int) {
        currentPlaying.value = musicList.value[index]
        currentPlayingIndex.value = index
    }

    fun startUpdatingProgress() {
        val musicService = mBinder.value?.currentService()
        viewModelScope.launch(Dispatchers.IO) {
            if (musicService != null) {
                while (musicService.getCurrentProgress() < musicService.getMaxDuration()) {
                    currentProgress.value = musicService.getCurrentProgress().toLong()
                    delay(1000)
                }
            }
        }
    }
}