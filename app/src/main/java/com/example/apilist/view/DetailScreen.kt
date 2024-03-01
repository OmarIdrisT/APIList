package com.example.apilist.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.apilist.R
import com.example.apilist.model.Data
import com.example.apilist.model.Images
import com.example.apilist.model.PokemonDetails
import com.example.apilist.navigation.Routes
import com.example.apilist.viewmodel.APIViewModel

@Composable
fun DetailScreen(navController: NavController, myViewModel: APIViewModel) {
    val bottomNavigationItems = myViewModel.bottomNavigationItems
    val loading = myViewModel.loading.observeAsState()
    myViewModel.getCardById()
    val card : PokemonDetails by myViewModel.cardDetails.observeAsState(PokemonDetails(Data(0, "", emptyList(), "", "", "", Images("",""), "", "", "", "", "", emptyList(), emptyList(), emptyList(), "", emptyList())))
    Scaffold(
        topBar = { DetailTopAppBar(navController, myViewModel) },
        bottomBar = { MyBottomBar(navController,myViewModel, bottomNavigationItems)},
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
    val favorite by myViewModel.isFavorite.observeAsState(false)
    myViewModel.isFavorite(card.data)
    val favIcon = if(favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(Color.DarkGray.copy(alpha = 0.6f)),
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    model = card.data.images.large,
                    contentDescription = "Card Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(250.dp)
                )

            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = card.data.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily(Font(R.font.times)),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                IconButton(onClick = {
                    myViewModel.isFavorite(card.data)
                    if (!favorite) {
                        myViewModel.saveAsFavorite(card.data)
                    }else {
                        myViewModel.deleteFavorite(card.data)
                    }
                }){
                    Icon(imageVector = favIcon, contentDescription = "Favorito", tint = Color.Red, modifier = Modifier.size(50.dp))
                }
            }
            Row(modifier = Modifier.fillMaxWidth(0.9f)) {
                Text(
                    text = if(card.data.flavorText==null) "" else "\n${card.data.flavorText}",
                    fontFamily = FontFamily(Font(R.font.times)),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.fillMaxHeight(0.1f))
            Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text(
                    text = if(card.data.rarity==null) "Rarity: None" else "Rarity: ${card.data.rarity}",
                    fontFamily = FontFamily(Font(R.font.times)),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = if(card.data.supertype==null) "" else "Supertype: ${card.data.supertype}",
                    fontFamily = FontFamily(Font(R.font.times)),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                if (card.data.supertype == "Pokémon") {
                    Text(
                        text = if(card.data.evolvesFrom==null) "Evolves from:" else "Evolves from: ${card.data.evolvesFrom}",
                        fontFamily = FontFamily(Font(R.font.times)),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if(card.data.evolvesTo==null) "Evolves to:" else "Evolves to: ${card.data.evolvesTo.joinToString(", ")}",
                        fontFamily = FontFamily(Font(R.font.times)),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(navController: NavController, myViewModel: APIViewModel) {
    TopAppBar(
        title = { Text(text = "Card details", fontFamily = FontFamily(Font(R.font.pokemon))) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = {
                myViewModel.deploySearchBar(false)
                navController.navigateUp()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
    )
}
