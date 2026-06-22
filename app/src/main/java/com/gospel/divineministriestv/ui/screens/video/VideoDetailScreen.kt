package com.gospel.divineministriestv.ui.screens.video

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gospel.divineministriestv.data.model.Comment
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
    val context = LocalContext.current

    LaunchedEffect(videoId) {
        viewModel?.fetchVideoDetails(videoId)
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Summary", "Scriptures", "Prayer Points", "Comments")

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
                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Visibility, 
                                contentDescription = null, 
                                tint = MaterialTheme.colorScheme.secondary, 
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = " ${video.viewCount ?: "0"} views",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Icon(
                                imageVector = Icons.Default.ThumbUp, 
                                contentDescription = null, 
                                tint = MaterialTheme.colorScheme.secondary, 
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = " ${video.likeCount ?: "0"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Comment, 
                                contentDescription = null, 
                                tint = MaterialTheme.colorScheme.secondary, 
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = " ${video.commentCount ?: "0"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = video.publishedAt.take(10),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            VideoActionButton(
                                icon = Icons.Default.PlayArrow, 
                                label = "Watch",
                                onClick = { Toast.makeText(context, "Playing Video", Toast.LENGTH_SHORT).show() }
                            )
                            VideoActionButton(
                                icon = Icons.Default.Headset, 
                                label = "Listen",
                                onClick = { Toast.makeText(context, "Audio Mode Activated", Toast.LENGTH_SHORT).show() }
                            )
                            VideoActionButton(
                                icon = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                label = "Favorite",
                                iconColor = if (state.isFavorite) Color.Red else Color.White,
                                onClick = { viewModel?.toggleFavorite() }
                            )
                            VideoActionButton(
                                icon = Icons.Default.Share, 
                                label = "Share",
                                onClick = {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, "Watch ${video.title} on Divine Ministries TV: https://www.youtube.com/watch?v=$videoId")
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    context.startActivity(shareIntent)
                                }
                            )
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

                        // Tab Content - Using a box to fill remaining space
                        Box(modifier = Modifier.weight(1f)) {
                            when (selectedTab) {
                                0 -> {
                                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                                        Spacer(modifier = Modifier.height(24.dp))
                                    }
                                }
                                3 -> {
                                    if (state.comments.isEmpty()) {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Text(text = "No comments found", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    } else {
                                        LazyColumn(
                                            verticalArrangement = Arrangement.spacedBy(16.dp),
                                            contentPadding = PaddingValues(bottom = 24.dp)
                                        ) {
                                            items(state.comments) { comment ->
                                                CommentItem(comment = comment)
                                            }
                                        }
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
}

@Composable
fun CommentItem(comment: Comment) {
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = comment.authorImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = comment.authorName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = comment.publishedAt.take(10),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.text,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 18.sp
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ThumbUp, 
                    contentDescription = null, 
                    tint = MaterialTheme.colorScheme.secondary, 
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = " ${comment.likeCount}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun VideoActionButton(
    icon: ImageVector, 
    label: String, 
    iconColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Icon(icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.White, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun TopicChip(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
        shape = CircleShape,
        border = null
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
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
