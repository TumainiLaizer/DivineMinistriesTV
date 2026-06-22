package com.gospel.divineministriestv.ui.screens.social

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gospel.divineministriestv.R
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme
import com.gospel.divineministriestv.util.Constants
import com.gospel.divineministriestv.util.openBrowser
import com.gospel.divineministriestv.util.openWhatsApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialMediaHubScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Social Media Hub", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = "Connect With Us",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Follow, subscribe and stay connected with us.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            SocialHubItem(
                title = "YouTube",
                value = "Divine Ministries TV",
                iconRes = R.drawable.ic_youtube,
                onClick = { context.openBrowser("https://www.youtube.com/@divineministriestv") }
            )
            SocialHubItem(
                title = "Facebook",
                value = "facebook.com/lusako.sekela",
                iconRes = R.drawable.ic_facebook,
                onClick = { context.openBrowser("https://facebook.com/lusako.sekela") }
            )
            SocialHubItem(
                title = "Instagram",
                value = "@divineministriesglobal",
                iconRes = R.drawable.ic_instagram,
                onClick = { context.openBrowser("https://instagram.com/divineministriesglobal") }
            )
            SocialHubItem(
                title = "WhatsApp",
                value = "Chat with us",
                iconRes = R.drawable.ic_whatsapp,
                onClick = { context.openWhatsApp(Constants.WHATSAPP_NUMBER) }
            )
            SocialHubItem(
                title = "Website",
                value = "divineministriesglobal.com",
                iconRes = R.drawable.divinelogo,
                onClick = { context.openBrowser(Constants.WEBSITE_URL) }
            )
        }
    }
}

@Composable
fun SocialHubItem(title: String, value: String, iconRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = iconRes,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun SocialMediaHubScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        SocialMediaHubScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun SocialMediaHubScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        SocialMediaHubScreen()
    }
}
