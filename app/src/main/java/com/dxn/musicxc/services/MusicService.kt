package com.dxn.musicxc.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.dxn.musicxc.models.Music

class MusicService : Service() {

    private val mBinder : Binder = MyBinder()
    private var mMediaPlayer: MediaPlayer? = null


    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    inner class MyBinder : Binder() {
        fun currentService() :MusicService {
            return this@MusicService
        }
    }

    fun playMusic(music: Music) {
        if(mMediaPlayer!=null) {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer=null
        }
        mMediaPlayer = MediaPlayer.create(baseContext,music.musicUri)
        mMediaPlayer?.start()
    }

    fun onPause() {
        mMediaPlayer?.pause()
    }

    fun onPlay() {
        mMediaPlayer?.start()
    }

    fun getCurrentProgress() : Int {
        return mMediaPlayer?.currentPosition?:0
    }

    fun seekTo(progress:Long) {
        mMediaPlayer?.seekTo(progress.toInt())
    }

    fun getMaxDuration() : Long {
        return mMediaPlayer?.duration?.toLong()?:0L
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
        mMediaPlayer=null
        stopSelf()
    }

    fun onComplete(
        onCompleteListener : () -> Unit
    ) {
        mMediaPlayer?.setOnCompletionListener {
            onCompleteListener()
        }
    }
}