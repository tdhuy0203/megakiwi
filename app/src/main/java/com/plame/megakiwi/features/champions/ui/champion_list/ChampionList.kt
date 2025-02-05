package com.plame.megakiwi.features.champions.ui.champion_list
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.gson.JsonObject
import com.plame.megakiwi.core.components.Loader
import com.plame.megakiwi.core.utils.MediaHelper

@Composable
fun ChampionList(
    onChampionClicked: (String) -> Unit
) {
    val viewModel: ChampionListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Surface(
        color = Color.Black
    ) {
        if (uiState.loading)
            Loader(modifier = Modifier.fillMaxSize())
        else
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(6.dp)
            ) {
                items(uiState.championList) { champion ->
                    ChampionItem(
                        champion = champion,
                        modifier = Modifier.padding(top = 10.dp),
                        onChampionClicked = onChampionClicked
                    )
                }
            }
    }
}

@Composable
fun ChampionItem(
    modifier: Modifier = Modifier,
    champion: JsonObject,
    onChampionClicked: (String) -> Unit
) {
    Card(
        modifier = modifier.clickable {
            onChampionClicked(champion["alias"].asString)
        },
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp),
                model = MediaHelper.getImageUrl(champion["squarePortraitPath"].asString),
                contentDescription = champion["name"].asString
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(text = champion["name"].asString, fontWeight = FontWeight.Bold)
                Text(text = champion["title"].asString)
            }
        }
    }
}