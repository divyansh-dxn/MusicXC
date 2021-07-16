package com.dxn.musicxc.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dxn.musicxc.utils.Utilities
import androidx.compose.runtime.getValue


@Composable
fun MusicProgressBar(
    currentProgress: MutableState<Long>,
    maxDuration: MutableState<Long>,
    onValueChange: (Float) -> Unit
) {
    val mCurrentProgress by remember { currentProgress }
    val mMaxDuration by remember { maxDuration }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = (Utilities.getTimeFromMillis(mCurrentProgress)))
        Spacer(modifier = Modifier.padding(8.dp))
        Slider(
            modifier = Modifier.width(200.dp),
            value = mCurrentProgress.toFloat(),
            onValueChange = {
                onValueChange(it)
            },
            valueRange = 0f..mMaxDuration.toFloat(),
            onValueChangeFinished = {

            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.primary,
                activeTrackColor = MaterialTheme.colors.primary
            )
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = (Utilities.getTimeFromMillis(mMaxDuration)))
    }
}