package com.plame.megakiwi.features.rune_list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
fun RuneList() {
    val viewModel: RuneListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (uiState.runeList.size > 0) {
                uiState.runeList.forEach { rune ->
                    Rune(
                        modifier = Modifier.padding(top = 30.dp),
                        rune = rune.asJsonObject
                    )
                }
            }
        }
        if (uiState.loading) {
            Loader(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun Rune(
    modifier: Modifier = Modifier,
    rune: JsonObject
) {
    val slots = rune["slots"].asJsonArray
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(30.dp),
            model = MediaHelper.getImageUrl(rune["iconPath"].asString),
            contentDescription = rune["name"].asString,
            contentScale = ContentScale.Crop
        )
        Text(text = rune["name"].asString)
        slots.forEachIndexed { index, slot ->
            RowPerks(
                modifier = Modifier.padding(top = 20.dp),
                slot = slot.asJsonObject,
                isPrimaryPerk = index < 4
            )
        }
    }
}

@Composable
fun RowPerks(
    modifier: Modifier = Modifier,
    slot: JsonObject,
    isPrimaryPerk: Boolean = true
) {
    val perks = slot["perks"].asJsonArray
    val perksDetails = slot["perksDetails"].asJsonArray
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        perks.forEach { perkId ->
            val detail = perksDetails.find { detail ->
                detail.asJsonObject["id"].asString.equals(perkId.asString)
            }
            if (detail != null) {
                Perk(
                    modifier = Modifier.weight(1f),
                    perk = detail.asJsonObject,
                    isPrimaryPerk = isPrimaryPerk
                )
            }
        }
    }
}

@Composable
fun Perk(
    modifier: Modifier = Modifier,
    perk: JsonObject,
    isPrimaryPerk: Boolean = true
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(if (isPrimaryPerk) 50.dp else 30.dp),
            model = MediaHelper.getImageUrl(perk["iconPath"].asString),
            contentDescription = perk["name"].asString,
            contentScale = ContentScale.Crop
        )
        Text(
            text = perk["name"].asString,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}