package com.plame.megakiwi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.plame.megakiwi.core.navigation.Navigation
import com.plame.megakiwi.core.theme.MegakiwiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MegakiwiTheme {
                Navigation()
            }
        }
    }
}
