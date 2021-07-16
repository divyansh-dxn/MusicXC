package com.dxn.musicxc.utils

object Utilities {
    fun getTimeFromMillis(milliseconds: Long) : String {
        val minutes: Long = milliseconds / 1000 / 60
        val seconds: Long = milliseconds / 1000 % 60
        return if (seconds < 10) {
            "$minutes:0$seconds"
        } else "$minutes:$seconds"
    }
}