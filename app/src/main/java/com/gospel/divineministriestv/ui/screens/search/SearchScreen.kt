package com.gospel.divineministriestv.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onVideoClick: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val recentSearches = listOf("Prayer and Fasting", "Prophetic Promises", "Spiritual Warfare", "Kingdom Leadership")
    val popularSearches = listOf("Prayer", "Fasting", "Faith", "Healing", "Revival", "Holy Spirit", "Deliverance", "Prophecy")

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Search", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // Custom Search Bar Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Search sermons, topics, scriptures...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(recentSearches) { search ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Handle search */ }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = search, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Popular Searches",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Flow-like layout using Row + Column
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            popularSearches.take(4).forEach { search ->
                                SearchChip(text = search)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            popularSearches.drop(4).forEach { search ->
                                SearchChip(text = search)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchChip(text: String) {
    Surface(
        onClick = { },
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = CircleShape
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun SearchScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        SearchScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun SearchScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        SearchScreen()
    }
}
