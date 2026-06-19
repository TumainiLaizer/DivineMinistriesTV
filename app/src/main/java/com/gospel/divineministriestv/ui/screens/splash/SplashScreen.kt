package com.gospel.divineministriestv.ui.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.gospel.divineministriestv.R
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            AsyncImage(
                model = R.drawable.divinelogo,
                contentDescription = "Ministry Logo",
                modifier = Modifier.size(150.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "DIVINE MINISTRIES TV",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Raising Kings & Priests For The Last Days Glory of God To Nations",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun SplashScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        SplashScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun SplashScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        SplashScreen()
    }
}
