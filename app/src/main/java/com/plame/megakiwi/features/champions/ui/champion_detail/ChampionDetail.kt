package com.plame.megakiwi.features.champions.ui.champion_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.plame.megakiwi.core.components.ExoPlayerView
import com.plame.megakiwi.core.components.Loader
import com.plame.megakiwi.core.theme.AppTypography
import com.plame.megakiwi.core.utils.MediaHelper
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChampionDetail() {
    val viewModel: ChampionDetailViewModel = hiltViewModel()
    val uiState: ChampionDetailUiState by viewModel.uiState.collectAsState()
    val detail = uiState.detail
    Scaffold(
        containerColor = Color.Black
    ) { contentPadding ->
        if (detail.size() > 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.size(60.dp),
                        model = MediaHelper.getImageUrl(detail["squarePortraitPath"].asString),
                        contentDescription = null
                    )
                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = detail["name"].asString,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = detail["title"].asString,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = detail["shortBio"].asString,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
                Skins(
                    modifier = Modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp),
                    skins = detail["skins"].asJsonArray
                )
                Rating(
                    modifier = Modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp),
                    damage = detail["playstyleInfo"].asJsonObject["damage"].asInt,
                    toughness = detail["playstyleInfo"].asJsonObject["durability"].asInt,
                    cc = detail["playstyleInfo"].asJsonObject["crowdControl"].asInt,
                    mobility = detail["playstyleInfo"].asJsonObject["mobility"].asInt,
                    utility = detail["playstyleInfo"].asJsonObject["utility"].asInt
                )
                Spells(spells = detail["spells"].asJsonArray)
                val spellBuildList = detail["instruction"]
                    .asJsonObject[detail["alias"].asString.lowercase()]
                    .asJsonObject["spells"]
                    .asJsonArray
                for (spellBuild in spellBuildList) {
                    val json = spellBuild.asJsonObject
                    if (json["MapId"].asInt == 11 && json.has("IsDefaultRecommendation")) {
                        val build = mutableListOf<Int>()
                        for (element in json["build"].asJsonArray) {
                            build.add(element.asInt)
                        }
                        SpellBuild(build = build)
                    }
                }
            }
        }
        if (uiState.loading) {
            Loader(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Skins(modifier: Modifier = Modifier, skins: JsonArray = JsonArray()) {
    val pagerState = rememberPagerState(pageCount = { skins.size() })
    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Trang phục",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 10.dp
        ) { page ->
            val skin = skins[page].asJsonObject
            Card(
                modifier = Modifier.graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
            ) {
                Box() {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        model = MediaHelper.getImageUrl(skin["uncenteredSplashPath"].asString),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color.Black.copy(alpha = 0.5f))
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = skin["name"].asString,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Spells(modifier: Modifier = Modifier, spells: JsonArray) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Text(
            text = "Kĩ năng",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        spells.forEach() { element ->
            val spell = element.asJsonObject
            SpellView(
                spell = spell,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}

@Composable
fun SpellView(spell: JsonObject, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp),
                model = MediaHelper.getImageUrl(spell["abilityIconPath"].asString),
                contentDescription = null
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = spell["name"].asString,
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Text(
            text = spell["description"].asString,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(10.dp))
        ExoPlayerView(
            url = MediaHelper.getVideoUrl(spell["abilityVideoPath"].asString),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
fun Rating(
    modifier: Modifier = Modifier,
    damage: Int = 1,
    toughness: Int = 2,
    cc: Int = 2,
    mobility: Int = 3,
    utility: Int = 0
) {
    val textMeasurer = rememberTextMeasurer()
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Đánh giá",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f/3f)
        ) {
            val sin: (Float) -> Float = {
                sin(it)
            }
            val cos: (Float) -> Float = {
                cos(it)
            }
            val marginTop = 25.dp.toPx()
            val deltaX = size.width / 2
            val pi = PI.toFloat()
            //Draw a pentagon
            val x = size.width / 3 // Distance from center to 5 point
            val center = Offset(deltaX, x + marginTop)
            val damage3 = Offset(deltaX, marginTop)
            val toughness3 =
                Offset(deltaX + sin(pi * 2 / 5) * x, x - (cos(pi * 2 / 5) * x) + marginTop)
            val cc3 = Offset(deltaX + sin(pi * 1 / 5) * x, x + (cos(pi * 1 / 5) * x) + marginTop)
            val mobility3 = Offset(deltaX - sin(pi * 1 / 5) * x, cc3.y)
            val utility3 = Offset(deltaX - sin(pi * 2 / 5) * x, toughness3.y)
            val drawLine: (start: Offset, end: Offset) -> Unit = { start, end ->
                drawLine(
                    start = start,
                    end = end,
                    color = Color.White,
                    strokeWidth = 2f
                )
            }
            drawLine(damage3, toughness3)
            drawLine(toughness3, cc3)
            drawLine(cc3, mobility3)
            drawLine(mobility3, utility3)
            drawLine(utility3, damage3)

            // Draw a smaller pentagon
            val xx = x - (x / 3) // Distance from center to 5 point
            val damage2 = Offset(damage3.x, damage3.y + (x / 3))
            val toughness2 =
                Offset(
                    deltaX + sin(pi * 2 / 5) * xx,
                    xx - (cos(pi * 2 / 5) * xx) + (x / 3) + marginTop
                )
            val cc2 = Offset(
                deltaX + sin(pi * 1 / 5) * xx,
                xx + (cos(pi * 1 / 5) * xx) + (x / 3) + marginTop
            )
            val mobility2 = Offset(deltaX - sin(pi * 1 / 5) * xx, cc2.y)
            val utility2 = Offset(deltaX - sin(pi * 2 / 5) * xx, toughness2.y)
            drawLine(damage2, toughness2)
            drawLine(toughness2, cc2)
            drawLine(cc2, mobility2)
            drawLine(mobility2, utility2)
            drawLine(utility2, damage2)

            // Draw a smallest pentagon
            val xxx = xx - (x / 3) // Distance from center to 5 point
            val damage1 = Offset(damage3.x, damage2.y + (x / 3))
            val toughness1 =
                Offset(
                    deltaX + sin(pi * 2 / 5) * xxx,
                    xxx - (cos(pi * 2 / 5) * xxx) + (2 * x / 3) + marginTop
                )
            val cc1 = Offset(
                deltaX + sin(pi * 1 / 5) * xxx,
                xxx + (cos(pi * 1 / 5) * xxx) + (2 * x / 3) + marginTop
            )
            val mobility1 = Offset(deltaX - sin(pi * 1 / 5) * xxx, cc1.y)
            val utility1 = Offset(deltaX - sin(pi * 2 / 5) * xxx, toughness1.y)
            drawLine(damage1, toughness1)
            drawLine(toughness1, cc1)
            drawLine(cc1, mobility1)
            drawLine(mobility1, utility1)
            drawLine(utility1, damage1)

            val damageList = listOf(center, damage1, damage2, damage3)
            val toughnessList = listOf(center, toughness1, toughness2, toughness3)
            val ccList = listOf(center, cc1, cc2, cc3)
            val mobilityList = listOf(center, mobility1, mobility2, mobility3)
            val utilityList = listOf(center, utility1, utility2, utility3)

            // Draw
            drawLine(center, damage3)
            drawLine(center, toughness3)
            drawLine(center, cc3)
            drawLine(center, mobility3)
            drawLine(center, utility3)

            // Draw
            val path = Path()
            path.moveTo(damageList[damage].x, damageList[damage].y)
            path.lineTo(toughnessList[toughness].x, toughnessList[toughness].y)
            path.lineTo(ccList[cc].x, ccList[cc].y)
            path.lineTo(mobilityList[mobility].x, mobilityList[mobility].y)
            path.lineTo(utilityList[utility].x, utilityList[utility].y)
            drawPath(path, color = Color(0xFFFFBF00).copy(alpha = 0.3f))

            // Draw text
            val drawText: (text: String, position: Offset, margin: Int) -> Unit =
                { text, position, margin ->
                    val textWidth = textMeasurer.measure(text).size.width.toFloat()
                    val dx: Float = when (margin) {
                        0 -> textWidth / 2
                        -1 -> textWidth
                        else -> 0f
                    }
                    drawText(
                        textMeasurer, text,
                        topLeft = position.copy(x = position.x - dx),
                        style = AppTypography.bodySmall.copy(
                            color = Color.White
                        )
                    )
                }
            drawText("Sát thương", damage3.copy(y = 0f), 0)
            drawText("Chống chịu", Offset(toughness3.x + 5, toughness3.y - 10), 1)
            drawText("Khống chế", Offset(cc3.x + 5, cc3.y - 10), 1)
            drawText("Cơ động", Offset(mobility3.x, mobility3.y - 10), -1)
            drawText("Đa dụng", Offset(utility3.x, utility3.y - 10), -1)
        }
    }
}

@Composable
fun SpellBuild(modifier: Modifier = Modifier, build: List<Int>) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SpellBuildRow(id = 0, label = "q", build = build)
        SpellBuildRow(id = 1, label = "w", build = build)
        SpellBuildRow(id = 2, label = "e", build = build)
        SpellBuildRow(id = 3, label = "r", build = build)
    }
}

@Composable
fun SpellBuildRow(id: Int, label: String, build: List<Int>) {
    val maxLevel = 18
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.width(10.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                label,
                fontSize = 10.sp,
                lineHeight = 11.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        for (index in 1..maxLevel) {
            val level = if (build[index - 1] == id) index else null
            SpellBuildItem(
                level = level,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SpellBuildItem(level: Int? = null, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(1.dp)
            .aspectRatio(1f)
            .background(
                if (level == null)
                    Color(0xFFFFBF00).copy(alpha = 0.3f)
                else
                    Color(0xFFFFBF00)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (level != null) {
            Text(
                "$level",
                fontSize = 8.sp,
                lineHeight = 9.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpellBuildPreview() {
    val build = listOf(0,2,1,0,0,3,0,2,0,2,3,2,2,1,1,3,1,1)
    SpellBuild(build = build)
}