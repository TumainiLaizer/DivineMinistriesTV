package com.gospel.divineministriestv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gospel.divineministriestv.ui.screens.MainScreen
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DivineMinistriesTVTheme {
                MainScreen()
            }
        }
    }
}
