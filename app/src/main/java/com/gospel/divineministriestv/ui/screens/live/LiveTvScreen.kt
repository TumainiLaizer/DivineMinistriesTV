package com.gospel.divineministriestv.ui.screens.live

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gospel.divineministriestv.data.model.Video
import com.gospel.divineministriestv.ui.components.YouTubePlayer
import com.gospel.divineministriestv.ui.screens.home.HomeUiState
import com.gospel.divineministriestv.ui.screens.home.HomeViewModel
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveTvScreen(
    onBack: () -> Unit = {},
    viewModel: HomeViewModel? = null
) {
    val uiState = viewModel?.uiState?.collectAsState()?.value ?: HomeUiState.Success(
        latestVideos = emptyList(),
        liveVideo = Video("0", "International Prayer Gathering", "Divine Ministries TV", "https://i.ytimg.com/vi/placeholder/maxresdefault.jpg", "Now", isLive = true),
        playlists = emptyList()
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Live TV", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Search") }
                    IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, contentDescription = "More") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is HomeUiState.Success -> {
                    val liveVideo = state.liveVideo ?: return@Column
                    
                    // Video Player
                    Box(modifier = Modifier.fillMaxWidth().aspectRatio(16/9f)) {
                        YouTubePlayer(videoId = liveVideo.id, modifier = Modifier.fillMaxSize())
                        
                        Row(
                            modifier = Modifier.padding(12.dp).align(Alignment.TopStart),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color.Red,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "LIVE",
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                    Text("1.2K", color = Color.White, fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = liveVideo.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Divine Ministries TV\n1.2K watching",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = "Live Chat",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Simulated Chat
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ChatItem("Glory to God!", "Divine Warrior")
                            ChatItem("Amen, Receiving...", "Faith Child")
                            ChatItem("Thank you Jesus!", "John Doe")
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Chat Input
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Chat as Divine Warrior...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun ChatItem(message: String, user: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondary))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = user, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = message, style = MaterialTheme.typography.bodySmall, color = Color.White)
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun LiveTvScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        LiveTvScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LiveTvScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        LiveTvScreen()
    }
}
