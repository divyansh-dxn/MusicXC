package com.dxn.musicxc.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dxn.musicxc.R

@Composable
fun ActionBar(
    onNavigationIconClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClicked() }) {
                Icon(Icons.Default.Menu, contentDescription = "Drawer icon")
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.Search, contentDescription = "Drawer icon")
            }
        },
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = 2.dp
    )
}