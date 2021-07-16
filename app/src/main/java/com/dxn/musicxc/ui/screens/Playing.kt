package com.dxn.musicxc.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dxn.musicxc.ui.components.MusicControls
import com.dxn.musicxc.ui.components.MusicProgressBar
import com.dxn.musicxc.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Playing(
    viewModel: MainViewModel,
    isExpanded: MutableState<Boolean> = mutableStateOf(false),
) {
    val scope = rememberCoroutineScope()
    val mIsPlaying by remember { viewModel.isPlaying }
    val mIsRepeatOn by remember { viewModel.isRepeatOn }
    val mCurrentlyPlaying by remember { viewModel.currentPlaying }
    val mIsExpanded by remember { isExpanded }

    val mCurrentProgress = remember { viewModel.currentProgress }
    val mMaxDuration = remember { viewModel.maxDuration }
    val isRepeatOn = remember { viewModel.isRepeatOn }
    val isPlaying = remember { viewModel.isPlaying }

    val mBinder by remember { viewModel.mBinder }
    val musicService = mBinder?.currentService()

    musicService?.onComplete {
        if (mIsRepeatOn) {
            mCurrentlyPlaying?.let { musicService.playMusic(it) }
        } else {
            if (viewModel.currentPlayingIndex.value == viewModel.totalMusic.value - 1) {
                viewModel.setCurrentPlaying(0)
            } else {
                viewModel.setCurrentPlaying(++viewModel.currentPlayingIndex.value)
            }
            viewModel.currentPlaying.value?.let { musicService.playMusic(it) }
            viewModel.maxDuration.value = musicService.getMaxDuration()
        }
        scope.launch(Dispatchers.IO) {
            while (musicService.getCurrentProgress() < musicService.getMaxDuration()) {
                viewModel.currentProgress.value = musicService.getCurrentProgress().toLong()
                delay(1000)
            }
        }
    }

    Column(
        modifier = Modifier.padding(32.dp, 8.dp, 32.dp, 32.dp)
    ) {

        // todo : set !isExpanded
        if (mIsExpanded) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = mCurrentlyPlaying?.title ?: "No song played!")
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = rememberImagePainter(mCurrentlyPlaying?.albumArtUri),
                    contentDescription = "Currently playing art",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }

        Text(
            text = mCurrentlyPlaying?.title ?: "No song playing currently",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(
            text = mCurrentlyPlaying?.album ?: "",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
        Image(
            painter = rememberImagePainter(mCurrentlyPlaying?.albumArtUri),
            contentDescription = "Album art",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
        MusicControls(
            onNextClicked = {
                if (viewModel.currentPlayingIndex.value == viewModel.totalMusic.value - 1) {
                    viewModel.setCurrentPlaying(0)
                } else {
                    viewModel.setCurrentPlaying(++viewModel.currentPlayingIndex.value)
                }
                viewModel.currentPlaying.value?.let { musicService?.playMusic(it) }
                viewModel.maxDuration.value = musicService?.getMaxDuration() ?: 0L
            },
            onPrevClicked = {
                if (viewModel.currentPlayingIndex.value == 0) {
                    viewModel.setCurrentPlaying(viewModel.totalMusic.value - 1)
                } else {
                    viewModel.setCurrentPlaying(--viewModel.currentPlayingIndex.value)
                }
                viewModel.maxDuration.value = musicService?.getMaxDuration() ?: 0L
                viewModel.currentPlaying.value?.let { musicService?.playMusic(it) }
            },
            onShuffleClicked = {

            },
            onRepeatClicked = {
                viewModel.isRepeatOn.value = !viewModel.isRepeatOn.value
            },
            onPlayPauseClicked = {
                if (mIsPlaying) {
                    musicService?.onPause()
                } else {
                    musicService?.onPlay()
                }
                viewModel.isPlaying.value = !viewModel.isPlaying.value
            },
            isRepeatOn = isRepeatOn,
            isPlaying = isPlaying
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        MusicProgressBar(
            currentProgress = mCurrentProgress,
            maxDuration = mMaxDuration,
            onValueChange = {
                viewModel.currentProgress.value = it.toLong()
                musicService?.seekTo(it.toLong())
            }
        )
    }
}



