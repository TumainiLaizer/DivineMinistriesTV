package com.gospel.divineministriestv.ui.screens.prayer

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gospel.divineministriestv.R
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme
import com.gospel.divineministriestv.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerCounselingScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

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

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Prayer & Counseling",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
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
                    text = "We are here for you.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Reach out to us directly.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrayerItem(
                    title = "WhatsApp Prayer",
                    subtitle = "Chat on WhatsApp",
                    icon = Icons.AutoMirrored.Filled.Chat,
                    onClick = { openWhatsApp(context, Constants.WHATSAPP_NUMBER) }
                )
                PrayerItem(
                    title = "Call Ministry",
                    subtitle = Constants.MINISTRY_PHONE,
                    icon = Icons.Default.Phone,
                    onClick = { makePhoneCall(context, Constants.MINISTRY_PHONE) }
                )
                PrayerItem(
                    title = "Email Ministry",
                    subtitle = Constants.MINISTRY_EMAIL,
                    icon = Icons.Default.Email,
                    onClick = { sendEmail(context, Constants.MINISTRY_EMAIL) }
                )
                PrayerItem(
                    title = "Counseling Request",
                    subtitle = "Chat on WhatsApp",
                    icon = Icons.Default.ChatBubbleOutline,
                    onClick = { openWhatsApp(context, Constants.WHATSAPP_NUMBER) }
                )
                PrayerItem(
                    title = "Share Your Testimony",
                    subtitle = "Send on WhatsApp",
                    icon = Icons.AutoMirrored.Filled.Send,
                    onClick = { openWhatsApp(context, Constants.WHATSAPP_NUMBER) }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun PrayerItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
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
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
        }
    }
}

private fun openWhatsApp(context: Context, number: String) {
    val url = "https://wa.me/$number"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

private fun makePhoneCall(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
    context.startActivity(intent)
}

private fun sendEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
    context.startActivity(intent)
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun PrayerCounselingScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        PrayerCounselingScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun PrayerCounselingScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        PrayerCounselingScreen()
    }
}
