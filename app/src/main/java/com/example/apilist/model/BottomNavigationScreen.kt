package com.example.apilist.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.apilist.navigation.Routes

sealed class BottomNavigationScreen (val route: String, val icon: ImageVector, val label: String) {
    object Home:BottomNavigationScreen(Routes.MenuScreen.route, Icons.Filled.Home, "Home")
    object Favorite:BottomNavigationScreen(Routes.FavoriteScreen.route, Icons.Filled.Favorite, "Favourites")
}