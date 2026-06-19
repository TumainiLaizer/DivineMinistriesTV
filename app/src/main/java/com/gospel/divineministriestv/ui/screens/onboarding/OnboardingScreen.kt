package com.gospel.divineministriestv.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.gospel.divineministriestv.R
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme

@Composable
fun OnboardingScreen(onFinished: () -> Unit = {}) {
    var step by remember { mutableIntStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        AsyncImage(
            model = R.drawable.onboarding_screen_background,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            AsyncImage(
                model = R.drawable.divinelogo,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            when (step) {
                1 -> StepOneContent()
                2 -> StepTwoContent()
            }
        }

        Button(
            onClick = {
                if (step < 2) step++ else onFinished()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                if (step == 1) "NEXT" else "GET STARTED",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun StepOneContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "WELCOME TO\nDIVINE MINISTRIES TV",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "A global house of prayer, raising prophetic intercessors for the end time harvest.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(40.dp))

        OnboardingListItem(Icons.Default.EmojiEvents, "TRIUMPHING", "2 Corinthians 2:14")
        Spacer(modifier = Modifier.height(16.dp))
        OnboardingListItem(Icons.Default.MilitaryTech, "REIGNING", "Revelation 5:10")
        Spacer(modifier = Modifier.height(16.dp))
        OnboardingListItem(Icons.Default.Stars, "LAST DAYS GLORY", "Haggai 2:9")
    }
}

@Composable
fun StepTwoContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "WATCH. PRAY.\nGROW. GO.",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Access powerful teachings, live broadcasts, prayer gatherings and more.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        OnboardingListItem(Icons.Default.LiveTv, "Live TV & Chat", null)
        Spacer(modifier = Modifier.height(16.dp))
        OnboardingListItem(Icons.Default.AutoAwesome, "AI Sermon Insights", null)
        Spacer(modifier = Modifier.height(16.dp))
        OnboardingListItem(Icons.Default.AudioFile, "Audio Mode", null)
        Spacer(modifier = Modifier.height(16.dp))
        OnboardingListItem(Icons.Default.VolunteerActivism, "Prayer & Counseling", null)
        Spacer(modifier = Modifier.height(16.dp))
        OnboardingListItem(Icons.Default.CalendarToday, "Events & Conferences", null)
    }
}

@Composable
fun OnboardingListItem(icon: ImageVector, title: String, subtitle: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun OnboardingScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        OnboardingScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun OnboardingScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        OnboardingScreen()
    }
}
