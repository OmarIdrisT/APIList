package com.example.apilist.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.apilist.R
import com.example.apilist.model.CardList
import com.example.apilist.model.Data
import com.example.apilist.viewmodel.APIViewModel

@Composable
fun MenuScreen(navController: NavController, myViewModel: APIViewModel) {
    Image(
        painter = painterResource(id = R.drawable.fons),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    MyRecyclerView(myViewModel = myViewModel)

}




@Composable
fun MyRecyclerView(myViewModel: APIViewModel) {
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val cards: CardList by myViewModel.cards.observeAsState(CardList(0, emptyList(), 0, 0, 0))
    myViewModel.getCards()
    if(showLoading){
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary
        )
    }
    else{
        LazyColumn() {
            items(cards.data) {
                CardItem(card = it)
            }

        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardItem(card: Data) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            GlideImage(
                model = card.images.large,
                contentDescription = "Card Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = card.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
            )
        }
    }
}
