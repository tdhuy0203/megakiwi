@file:JvmName("SummonerSpellListViewModelKt")

package com.plame.megakiwi.features.summoner_spell_list.ui

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
fun SummonerSpellList() {
    val viewModel: SummonerSpellListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Box {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
        ) {
            items(uiState.summonerSpellList) {
                SummonerSpell(
                    summonerSpell = it,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
        if (uiState.loading) {
            Loader(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun SummonerSpell(
    modifier: Modifier = Modifier,
    summonerSpell: JsonObject
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = MediaHelper.getImageUrl(summonerSpell["iconPath"].asString),
            contentDescription = summonerSpell["name"].asString
        )
        Text(
            text = summonerSpell["name"].asString,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}