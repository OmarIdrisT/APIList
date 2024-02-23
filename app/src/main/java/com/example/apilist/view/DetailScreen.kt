package com.example.apilist.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.apilist.R
import com.example.apilist.model.BottomNavigationScreen
import com.example.apilist.model.Data
import com.example.apilist.model.Images
import com.example.apilist.model.PokemonDetails
import com.example.apilist.navigation.Routes
import com.example.apilist.viewmodel.APIViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, myViewModel: APIViewModel) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreen.Home,
        BottomNavigationScreen.Favorite
    )
    val loading = myViewModel.loading.observeAsState()
    myViewModel.getCardById()
    val card : PokemonDetails by myViewModel.cardDetails.observeAsState(PokemonDetails(Data(0, "", emptyList(), "", "", "", Images("",""), "", "", emptyList(), "", "", "", emptyList(), emptyList(), emptyList(), "", emptyList())))
    Scaffold(
        topBar = { DetailTopAppBar(navController) },
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
                if (loading.value == true) {
                    CircularProgressIndicator()
                } else {
                    CardDetails(card, myViewModel)
                }
            }
        }
    )

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardDetails(card: PokemonDetails, myViewModel: APIViewModel) {
    val favorito by myViewModel.isFavorite.observeAsState(false)
    myViewModel.isFavorite(card.data)
    val favIcon = if(favorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(Color.DarkGray.copy(alpha = 0.6f)),
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = card.data.images.large,
                    contentDescription = "Card Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(200.dp)
                )
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        text = "LVL: ${card.data.level}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "HP: ${card.data.hp}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Types: ${card.data.types}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Subtypes:\n${card.data.subtypes}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${card.data.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                IconButton(onClick = {
                    myViewModel.isFavorite(card.data)
                    if (!favorito) {
                        myViewModel.saveAsFavorite(card.data)
                    }else {
                        myViewModel.deleteFavorite(card.data)
                    }
                }){
                    Icon(imageVector = favIcon, contentDescription = "Favorito", tint = Color.Red, modifier = Modifier.size(50.dp))
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Card List") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = {navController.navigate(Routes.MenuScreen.route)}) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Menu")
            }
        }
    )
}
