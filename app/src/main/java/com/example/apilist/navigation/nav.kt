package com.example.apilist.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("SplashScreen")
    object MenuScreen:Routes("MenuScreen")
    object DetailScreen:Routes("DetailScreen")
    object FavoriteScreen:Routes("FavoriteScreen")
}
