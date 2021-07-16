package com.dxn.musicxc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dxn.musicxc.R

@Composable
fun MusicControls(
    onNextClicked: () -> Unit,
    onPrevClicked: () -> Unit,
    onShuffleClicked: () -> Unit,
    onRepeatClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    isRepeatOn: MutableState<Boolean>,
    isPlaying: MutableState<Boolean>
) {

    val mIsPlaying by remember { isPlaying }
    val mIsRepeatOn by remember { isRepeatOn }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                onRepeatClicked()
            }, modifier = Modifier
                .weight(1f)
                .size(24.dp)
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.repeat_black_24dp),
                contentDescription = "Repeat button",
                tint = if (mIsRepeatOn) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            )
        }
        IconButton(
            onClick = {
                onPrevClicked()
            }, modifier = Modifier
                .weight(1f)
                .size(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.skip_previous_black_24dp),
                "Previous button"
            )
        }
        IconButton(
            onClick = {
                onPlayPauseClicked()
            }, modifier = Modifier
                .weight(1f)
                .size(32.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
        ) {
            if (mIsPlaying) {
                Icon(
                    painter = painterResource(id = R.drawable.pause_black_24dp),
                    contentDescription = "Play Pause button",
                    tint = MaterialTheme.colors.onPrimary
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.play_arrow_black_24dp),
                    contentDescription = "Play Pause button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }

        }
        IconButton(
            onClick = {
                onNextClicked()
            }, modifier = Modifier
                .weight(1f)
                .size(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.skip_next_black_24dp),
                "Next button"
            )
        }
        IconButton(
            onClick = {
                onShuffleClicked()
            }, modifier = Modifier
                .weight(1f)
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.shuffle_black_24dp),
                "Shuffle button"
            )
        }
    }
}