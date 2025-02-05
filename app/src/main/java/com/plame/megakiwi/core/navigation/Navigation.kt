package com.plame.megakiwi.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plame.megakiwi.features.champions.ui.champion_detail.ChampionDetail
import com.plame.megakiwi.features.champions.ui.champion_list.ChampionList
import com.plame.megakiwi.features.items.ui.item_detail.ItemDetail
import com.plame.megakiwi.features.items.ui.item_list.ItemList
import com.plame.megakiwi.features.rune_list.ui.RuneList
import com.plame.megakiwi.features.summoner_spell_list.ui.SummonerSpellList

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val icons = listOf(
        Icons.Rounded.Home,
        Icons.Rounded.Star,
        Icons.Rounded.DateRange,
        Icons.Rounded.Place
    )
    val routes = listOf(
        NavRoute.Champions.name,
        NavRoute.Items.name,
        NavRoute.SummonerSpells.name,
        NavRoute.Runes.name
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val inBottomRoute = currentRoute in routes
    var selectedItem by remember { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (inBottomRoute) {
                NavigationBar() {
                    routes.forEachIndexed { index, route ->
                        NavigationBarItem(
//                            label = { Text(route) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(icons[index], contentDescription = null) },
//                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = Color.Black,
//                            selectedTextColor = Color.Black,
//                            unselectedIconColor = Color.Black.copy(alpha = 0.5f),
//                            unselectedTextColor = Color.Black.copy(alpha = 0.5f)
//                        )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.Champions.name,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                fadeIn(tween(100))
            },
            exitTransition = {
                fadeOut(tween(100))
            }
        ) {
            composable(
                route = NavRoute.Champions.name,
            ) {
                ChampionList(
                    onChampionClicked = { championAlias ->
                        navController.navigate("${NavRoute.ChampionDetail.name}/$championAlias")
                    }
                )
            }
            composable(route = NavRoute.Items.name) {
                ItemList(
                    onItemClicked = { itemId ->
                        navController.navigate("${NavRoute.ItemDetail.name}/$itemId")
                    }
                )
            }
            composable(route = NavRoute.SummonerSpells.name) {
                SummonerSpellList()
            }
            composable(route = NavRoute.Runes.name) {
                RuneList()
            }
            composable(
                route = "${NavRoute.ChampionDetail.name}/{champion_alias}",
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(500))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(500))
                }
            ) {
                ChampionDetail()
            }
            composable(route = "${NavRoute.ItemDetail.name}/{item_id}") {
                ItemDetail()
            }
        }
    }
}

enum class NavRoute(name: String) {
    Champions(name = "Champion"),
    Items(name = "Item"),
    SummonerSpells(name = "SummonerSpell"),
    Runes(name = "Rune"),
    ChampionDetail(name = "ChampionDetail"),
    ItemDetail(name = "ItemDetail")
}