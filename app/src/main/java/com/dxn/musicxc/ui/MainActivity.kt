package com.dxn.musicxc.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dxn.musicxc.ui.theme.MusicXCTheme


import com.dxn.musicxc.services.MusicService
import com.dxn.musicxc.ui.viewmodels.MainViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var musicIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRuntimeService()
        musicIntent = Intent(this, MusicService::class.java)
        startService(musicIntent)
        bindService(musicIntent, viewModel.getServiceConnection(), Context.BIND_AUTO_CREATE)
        setContent {
            MusicXCTheme {
                App()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(musicIntent)
    }

    private fun getRuntimeService() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    viewModel.fetchAllSongs()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    p0?.requestedPermission
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }


}


