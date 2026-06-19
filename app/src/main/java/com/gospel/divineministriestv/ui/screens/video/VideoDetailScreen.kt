package com.gospel.divineministriestv.ui.screens.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gospel.divineministriestv.ui.components.YouTubePlayer
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailScreen(
    videoId: String,
    onBack: () -> Unit = {},
    viewModel: VideoDetailViewModel? = null
) {
    val uiState by (viewModel?.uiState ?: MutableStateFlow(VideoDetailUiState.Loading)).collectAsState()

    LaunchedEffect(videoId) {
        viewModel?.fetchVideoDetails(videoId)
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Summary", "Scriptures", "Prayer Points", "Notes")

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Video Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is VideoDetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                }
            }
            is VideoDetailUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
            is VideoDetailUiState.Success -> {
                val video = state.video
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    YouTubePlayer(
                        videoId = videoId,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = video.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${video.publishedAt} • ${video.duration ?: ""} • ${video.viewCount ?: ""} views",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            VideoActionButton(icon = Icons.Default.PlayArrow, label = "Watch")
                            VideoActionButton(icon = Icons.Default.Headset, label = "Listen")
                            VideoActionButton(icon = Icons.Default.FavoriteBorder, label = "Favorite")
                            VideoActionButton(icon = Icons.Default.Share, label = "Share")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Tabs
                        ScrollableTabRow(
                            selectedTabIndex = selectedTab,
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.secondary,
                            edgePadding = 0.dp,
                            divider = {},
                            indicator = { tabPositions ->
                                TabRowDefaults.SecondaryIndicator(
                                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        ) {
                            tabs.forEachIndexed { index, tabTitle ->
                                Tab(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    text = { 
                                        Text(
                                            tabTitle, 
                                            fontSize = 12.sp, 
                                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                        ) 
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tab Content
                        when (selectedTab) {
                            0 -> {
                                Text(
                                    text = video.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.8f),
                                    lineHeight = 22.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Key Topics:",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    TopicChip("Faith")
                                    TopicChip("Prophecy")
                                    TopicChip("Promises")
                                }
                            }
                            else -> {
                                Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                                    Text(text = "${tabs[selectedTab]} content coming soon", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VideoActionButton(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.White, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun TopicChip(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        shape = CircleShape,
        border = null
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun VideoDetailScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        VideoDetailScreen(videoId = "1")
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun VideoDetailScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        VideoDetailScreen(videoId = "1")
    }
}
