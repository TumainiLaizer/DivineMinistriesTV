package com.gospel.divineministriestv.ui.screens.more

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    onNavigate: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("More", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
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
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            MoreHubItem(title = "Ministry Information", icon = Icons.Outlined.Info, onClick = { onNavigate("ministry_info") })
            MoreHubItem(title = "Give an Offering", icon = Icons.Outlined.VolunteerActivism, onClick = { onNavigate("giving") })
            MoreHubItem(title = "Prayer & Counseling", icon = Icons.Outlined.Forum, onClick = { onNavigate("prayer") })
            MoreHubItem(title = "Events", icon = Icons.Outlined.CalendarToday, onClick = { /* TODO */ })
            MoreHubItem(title = "Social Media", icon = Icons.Outlined.Public, onClick = { onNavigate("social_hub") })
            MoreHubItem(title = "Contact Us", icon = Icons.Outlined.Phone, onClick = { /* TODO */ })
            MoreHubItem(title = "Settings", icon = Icons.Outlined.Settings, onClick = { onNavigate("settings") })
            MoreHubItem(title = "Help & Support", icon = Icons.AutoMirrored.Outlined.Help, onClick = { /* TODO */ }, isLast = true)

            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "Divine Ministries TV v1.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun MoreHubItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isLast: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = Color.White, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
        }
    }
    if (!isLast) {
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), thickness = 0.5.dp)
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MoreScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        MoreScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MoreScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        MoreScreen()
    }
}
