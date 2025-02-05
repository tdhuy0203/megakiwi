package com.plame.megakiwi.features.items.ui.item_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.gson.JsonObject
import com.plame.megakiwi.core.components.Loader
import com.plame.megakiwi.core.utils.MediaHelper

@Composable
fun ItemList(
    onItemClicked: (String) -> Unit,
) {
    val viewModel: ItemListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Box {
        LazyVerticalGrid(columns = GridCells.Fixed(5)) {
            items(uiState.itemList) { item ->
                Item(
                    item = item,
                    modifier = Modifier.padding(top = 10.dp),
                    onItemClicked = onItemClicked
                )
            }
        }
        if (uiState.loading) {
            Loader(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun Item(
    modifier: Modifier = Modifier,
    item: JsonObject,
    onItemClicked: (String) -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onItemClicked(item["id"].asString)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = MediaHelper.getImageUrl(item.get("iconPath").asString),
            contentDescription = item.get("name").asString
        )
        Text(
            text = item.get("name").asString,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}