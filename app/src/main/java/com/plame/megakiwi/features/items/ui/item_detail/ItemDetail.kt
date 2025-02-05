package com.plame.megakiwi.features.items.ui.item_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.plame.megakiwi.core.utils.Globals
import com.plame.megakiwi.core.utils.MediaHelper

@Composable
fun ItemDetail() {
    val viewModel: ItemDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val detail = uiState.detail
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.size(60.dp),
                model = MediaHelper.getImageUrl(detail["iconPath"].asString),
                contentDescription = detail["name"].asString
            )
            Text(text = detail["name"].asString)
        }
        Text(text = detail["description"].asString)
        if (detail["from"].asJsonArray.size() > 0) {

        }
        if (detail["to"].asJsonArray.size() > 0) {
            BuildInto(
                items = detail["to"].asJsonArray,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecipePreview() {
    val item = JsonObject().apply {
        addProperty("name", "Tester 1")
        addProperty("iconPath", "/lol-game-data/assets/ASSETS/Items/Icons2D/1001_Class_T1_BootsofSpeed.png")
    }
    val recipe = JsonArray()
    recipe.add(JsonObject().apply {
        addProperty("name", "Tester 2")
        addProperty("iconPath", "/lol-game-data/assets/ASSETS/Items/Icons2D/1001_Class_T1_BootsofSpeed.png")
        add("from", JsonArray().apply {
            add(JsonObject().apply {
                addProperty("name", "Tester 2-1")
                addProperty("iconPath", "/lol-game-data/assets/ASSETS/Items/Icons2D/1001_Class_T1_BootsofSpeed.png")
                add("from", JsonArray())
            })
        })
    })
    recipe.add(JsonObject().apply {
        addProperty("name", "Tester 3")
        addProperty("iconPath", "/lol-game-data/assets/ASSETS/Items/Icons2D/1001_Class_T1_BootsofSpeed.png")
        add("from", JsonArray())
    })
    recipe.add(JsonObject().apply {
        addProperty("name", "Tester 4")
        addProperty("iconPath", "/lol-game-data/assets/ASSETS/Items/Icons2D/1001_Class_T1_BootsofSpeed.png")
        add("from", JsonArray())
    })
    item.add("from", recipe)
    Recipe(item = item, isRoot = true)
}

@Composable
fun Recipe(
    modifier: Modifier = Modifier,
    item: JsonObject,
    isRoot: Boolean = false
) {
    val recipes = item["from"].asJsonArray
    Box {
        Column(
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                //            AsyncImage(
                //                modifier = Modifier.size(60.dp),
                //                model = Helper.getImageUrl(item["iconPath"].asString),
                //                contentDescription = item["name"].asString
                //            )
                Spacer(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color = Color.Transparent)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = item["name"].asString)
            }
            //            Spacer(
            //                modifier = Modifier
            //                    .width(50.dp)
            //                    .height(itemHeight * recipes.size())
            //                    .drawBehind {
            //                        drawLine(
            //                            start = Offset(25.dp.toPx(), 0.dp.toPx()),
            //                            end = Offset(25.dp.toPx(), size.height - (itemHeight.toPx() / 2)),
            //                            color = Color.Red,
            //                            strokeWidth = 2f
            //                        )
            //                        recipes.forEachIndexed { index, recipe ->
            //                            drawLine(
            //                                start = Offset(25.dp.toPx(), ((25 + index * 50).dp.toPx())),
            //                                end = Offset(50.dp.toPx(), ((25 + index * 50).dp.toPx())),
            //                                color = Color.Red,
            //                                strokeWidth = 2f
            //                            )
            //                        }
            //                    }
            //            )
            recipes.forEachIndexed { _, recipe ->
                Recipe(
                    item = recipe.asJsonObject,
                    isRoot = false,
                    modifier = Modifier.padding(start = 50.dp)
                )
                Canvas(modifier = Modifier) {
//                    drawLine(
//                        start = Offset.Zero,
//                        end = Offset(100f, 100f),
//                        color = Color.Red,
//                        strokeWidth = 2f
//                    )
                }
            }
        }
    }
//        drawLine(
//            start = Offset(25.dp.toPx(), 50.dp.toPx()),
//            end = Offset(25.dp.toPx(), 75.dp.toPx()),
//            color = Color.Cyan,
//            strokeWidth = 2f
//        )
//        drawLine(
//            start = Offset(25.dp.toPx(), 75.dp.toPx()),
//            end = Offset(50.dp.toPx(), 75.dp.toPx()),
//            color = Color.Red,
//            strokeWidth = 2f
//        )
}


@Composable
fun BuildInto(
    modifier: Modifier = Modifier,
    items: JsonArray
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Ghép thành các đồ:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        items.forEach { itemId ->
            val item = Globals.items[itemId.asString] ?: JsonObject()
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.size(60.dp),
                    model = MediaHelper.getImageUrl(item["iconPath"].asString),
                    contentDescription = item["name"].asString
                )
                Text(text = item["name"].asString)
            }
        }
    }
}