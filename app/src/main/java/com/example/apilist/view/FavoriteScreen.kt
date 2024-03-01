package com.example.apilist.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.apilist.R
import com.example.apilist.viewmodel.APIViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun FavoriteScreen(navController: NavController, myViewModel: APIViewModel) {
    val bottomNavigationItems = myViewModel.bottomNavigationItems
    val searchText: String by myViewModel.searchText.observeAsState("")

    Scaffold(
        topBar = { FavoriteTopBar(myViewModel) },
        bottomBar = { MyBottomBar(navController, myViewModel, bottomNavigationItems)},
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                Image(
                    painter = painterResource(id = R.drawable.fons),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.zIndex(0f)

                )
                myViewModel.getFavorites()
                val filteredFavs = myViewModel.favorites.value?.filter { it.name.contains(searchText, ignoreCase = true) }
                LazyColumn() {
                    items(filteredFavs.orEmpty()) { favorite ->
                        CardItem(card = favorite, navController, myViewModel)
                    }
                }

            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar(myViewModel: APIViewModel) {
    val showSearchBar: Boolean by myViewModel.showSearchBar.observeAsState(false)
    TopAppBar(
        title = { Text(text = "Favorites", fontFamily = FontFamily(Font(R.font.pokemon))) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            if (showSearchBar) {
                MySearchBar(myViewModel)
            }
            IconButton(onClick = { myViewModel.deploySearchBar(true) }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}