package com.example.apilist.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.apilist.R
import com.example.apilist.model.BottomNavigationScreen
import com.example.apilist.model.CardList
import com.example.apilist.model.Data
import com.example.apilist.navigation.Routes
import com.example.apilist.viewmodel.APIViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuScreen(navController: NavController, myViewModel: APIViewModel) {
    val searchText: String by myViewModel.searchText.observeAsState("")
    val bottomNavigationItems = myViewModel.bottomNavigationItems
    Scaffold(
        topBar = { MenuTopAppBar(myViewModel) },
        bottomBar = { MyBottomBar(navController, bottomNavigationItems)},
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
                MyRecyclerView(myViewModel = myViewModel, navController = navController,searchText = searchText)
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MyRecyclerView(myViewModel: APIViewModel, navController: NavController, searchText: String) {
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val cards: CardList by myViewModel.cards.observeAsState(CardList(0, emptyList(), 0, 0, 0))
    myViewModel.getCards()
    if (showLoading) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    } else {
        val filteredCards = cards.data.filter{ it.name.contains(searchText, ignoreCase = true) }
        LazyColumn() {
            items(filteredCards) {
                CardItem(card = it, navController, myViewModel)
            }

        }
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CardItem(card: Data, navController: NavController, myViewModel: APIViewModel) {
    var rareza = if (card.rarity == null) "" else card.rarity
    var type = card.types?.get(0).toString()
    var typeIcon = myViewModel.pickIcon(type)
    Box(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = typeIcon),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            alpha = 0.6f
        )
        Card(
            border = BorderStroke(2.dp, Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(Color.DarkGray.copy(alpha = 0.6f)),
            onClick = {
                myViewModel.setIDx(card.id)
                navController.navigate(Routes.DetailScreen.route)}
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        model = card.images.large,
                        contentDescription = "Card Image",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.size(150.dp)
                    )
                    Text(
                        text = "${card.name} \n$rareza",
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily(Font(R.font.hiro)),
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center, modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopAppBar(myViewModel: APIViewModel) {
    val showSearchBar: Boolean by myViewModel.showSearchBar.observeAsState(false)
    TopAppBar(
        title = { Text(text = "Card List", fontFamily = FontFamily(Font(R.font.pokemon))) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            if (showSearchBar) {
                MySearchBar(APIViewModel())
            }
            IconButton(onClick = { myViewModel.deploySearchBar() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}

@Composable
fun MyBottomBar(
    navigationController: NavController,
    bottomNavigationItems: List<BottomNavigationScreen>
) {
    BottomNavigation(backgroundColor = Color.Black, contentColor = Color.White) {
        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavigationItems.forEach { item ->
            val selectedColor = if(currentRoute == item.route) Color.Green else Color.White
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label, tint = selectedColor)},
                label = { Text(text = item.label, color = selectedColor, fontFamily = FontFamily(Font(R.font.pokemon)))},
                selected = currentRoute == item.route,
                alwaysShowLabel = false,
                onClick = {
                    if (currentRoute != item.route) {
                        navigationController.navigate(item.route)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar (myViewModel: APIViewModel) {
    val searchText: String by myViewModel.searchText.observeAsState("")
    SearchBar(
        colors = SearchBarDefaults.colors(Color.Black, inputFieldColors = TextFieldDefaults.colors(Color.White)),
        query = searchText,
        onQueryChange = { myViewModel.onSearchTextChange(it) },
        onSearch = { myViewModel.onSearchTextChange(it) },
        trailingIcon = {
                Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "CloseSearch",
                tint = Color.White,
                modifier = Modifier.clickable {
                    myViewModel.deploySearchBar()
                    myViewModel.onSearchTextChange("")
                })
        },
        active = true,
        placeholder = { Text(text = "Search...", fontFamily = FontFamily(Font(R.font.pokemon)), color = Color.White) },
        onActiveChange = {},
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .clip(CircleShape)) {
    }
}
