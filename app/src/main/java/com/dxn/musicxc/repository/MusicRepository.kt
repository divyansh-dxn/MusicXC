package com.dxn.musicxc.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.dxn.musicxc.models.Music

class MusicRepository {

    fun getAllMusic(applicationContext: Context): ArrayList<Music> {
        val musicList = ArrayList<Music>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DATE_ADDED
        )
        val query = applicationContext.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )
        query?.use { cursor ->
            val idC = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleC = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val albumC = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val artistC = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val durationC = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val albumArtC = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idC)
                val title = cursor.getString(titleC)
                val album = cursor.getString(albumC)
                val artist = cursor.getString(artistC)
                val duration = cursor.getInt(durationC)
                val albumArt = cursor.getLong(albumArtC)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                val albumArtUri: Uri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),albumArt)
                musicList += Music(id, title, album, artist, duration, albumArtUri, contentUri)
            }
        }
        return musicList
    }
}