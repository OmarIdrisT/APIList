package com.example.apilist.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.apilist.R
import com.example.apilist.model.Data
import com.example.apilist.model.Images
import com.example.apilist.model.PokemonDetails
import com.example.apilist.viewmodel.APIViewModel

@Composable
fun DetailScreen(navController: NavController, myViewModel: APIViewModel) {
    val loading = myViewModel.loading.observeAsState()
    myViewModel.getCardById()
    val card : PokemonDetails by myViewModel.cardDetails.observeAsState(PokemonDetails(Data(emptyList(), emptyList(), 0, "", emptyList(), "", "", "", Images("",""), "", "", emptyList(), "", "", "", emptyList(), emptyList(), emptyList(), emptyList(), "", emptyList(), emptyList())))
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


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardDetails(card: PokemonDetails, myViewModel: APIViewModel) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(Color.DarkGray.copy(alpha = 0.25f)),
    ) {
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
            Text(
                text = "${card.data.id}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
            )
        }
    }
}
