package com.example.apilist.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("com.example.apilist.SplashScreen")
    object MenuScreen:Routes("com.example.apilist.MenuScreen")
}
