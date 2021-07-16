package com.dxn.musicxc.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dxn.musicxc.R
import com.dxn.musicxc.ui.components.ActionBar
import com.dxn.musicxc.ui.screens.*
import com.dxn.musicxc.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    val mainViewModel = viewModel<MainViewModel>()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState,
        drawerState = drawerState)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentDestination = currentRoute(navController = navController)
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Favourites,
        BottomNavigationScreens.Playlists,
        BottomNavigationScreens.Albums)

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.secondary,
            ) {

                bottomNavigationItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = screen.route) },
                        label = { Text(stringResource(id = screen.resourceId)) },
                        selected = currentDestination == screen.route,
                        onClick = {
                            navController.navigate(screen.route)
                        },
                    )
                }

            }
        },
    ) {
        BottomSheetScaffold(
            topBar = {
                ActionBar(onNavigationIconClicked = {
                    if (drawerState.isClosed) {
                        scope.launch { drawerState.open() }
                    } else {
                        scope.launch { drawerState.close() }
                    }
                })
            },
            sheetContent = {
                Playing(
                    viewModel = mainViewModel,
                    isExpanded = mutableStateOf(bottomSheetState.currentValue == BottomSheetValue.Expanded),
                )
            },
            scaffoldState = bottomSheetScaffoldState,
            drawerContent = {

            },
            drawerBackgroundColor = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 56.dp),
            sheetPeekHeight = 48.dp
        ) {
            NavHost(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 48.dp),
                navController = navController,
                startDestination = BottomNavigationScreens.Home.route
            ) {
                composable(BottomNavigationScreens.Home.route) {
                    Home(
                        viewModel = mainViewModel,
                    )
                }
                composable(BottomNavigationScreens.Favourites.route) { Favourites() }
                composable(BottomNavigationScreens.Playlists.route) { Playlist() }
                composable(BottomNavigationScreens.Albums.route) { Album() }
            }
        }
    }
}


sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home :
        BottomNavigationScreens("Home", R.string.home_route, Icons.Rounded.Home)

    object Favourites :
        BottomNavigationScreens("Favourites", R.string.favourites_route, Icons.Rounded.Favorite)

    object Playlists :
        BottomNavigationScreens("Playlists", R.string.playlist_route, Icons.Rounded.Lock)

    object Albums :
        BottomNavigationScreens("Albums", R.string.albums_route, Icons.Rounded.Star)
}


@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(navBackStackEntry?.destination?.route)
}