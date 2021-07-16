package com.dxn.musicxc.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dxn.musicxc.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(
    viewModel: MainViewModel,
) {
    val allMusic by remember { viewModel.musicList }
    val mBinder by remember { viewModel.mBinder }
    val musicService = mBinder?.currentService()

    Column {
        LazyColumn {
            itemsIndexed(allMusic) { position, music ->
                ListItem(
                    icon = {
                        Image(
                            painter = rememberImagePainter(music.albumArtUri),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    },
                    secondaryText = {
                        Text(text = music.album, maxLines = 1)
                    },
                    trailing = {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Rounded.MoreVert, "Options")
                        }
                    },
                    modifier = Modifier.clickable {
                        musicService?.playMusic(music)
                        Log.d("TAG", "Home: $musicService")
                        viewModel.isPlaying.value = true
                        viewModel.setCurrentPlaying(position)
                        viewModel.maxDuration.value = musicService?.getMaxDuration()?:0L
                        viewModel.startUpdatingProgress()
                    }
                ) {
                    Text(text = music.title, maxLines = 1)
                }
                Divider()
            }
        }
    }
}