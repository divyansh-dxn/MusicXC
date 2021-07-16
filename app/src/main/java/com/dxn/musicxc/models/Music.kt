package com.dxn.musicxc.models

import android.net.Uri

data class Music(
    val id:Long,
    val title:String,
    val album:String,
    val artist:String,
    val duration:Int,
    val albumArtUri:Uri,
    val musicUri: Uri
)