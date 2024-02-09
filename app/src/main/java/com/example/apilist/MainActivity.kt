package com.example.apilist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apilist.navigation.Routes
import com.example.apilist.ui.theme.APIListTheme
import com.example.apilist.view.MenuScreen
import com.example.apilist.view.SplashScreen
import com.example.apilist.viewmodel.APIViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        val myViewModel by viewModels<APIViewModel>()
        setContent {
            APIListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.SplashScreen.route
                    ) {
                        composable(Routes.SplashScreen.route) { SplashScreen(navigationController) }
                        composable(Routes.MenuScreen.route) {MenuScreen(navigationController,myViewModel)}
                    }
                }
            }
        }
    }
}



