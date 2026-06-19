package com.gospel.divineministriestv.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    // New Initial Screens
    object Splash : Screen("splash", "Splash")
    object Onboarding : Screen("onboarding", "Onboarding")

    // Main Bottom Nav Screens
    object Home : Screen("home", "Home", Icons.Default.Home)
    object LiveTv : Screen("live_tv", "Live TV", Icons.Default.LiveTv)
    object Library : Screen("library", "Library", Icons.Default.VideoLibrary)
    object Prayer : Screen("prayer", "Prayer", Icons.Default.VolunteerActivism)
    object More : Screen("more", "More", Icons.Default.MoreHoriz)
    
    // Feature Screens
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Categories : Screen("categories", "Categories")
    object GivingDetails : Screen("giving_details/{methodType}", "Giving Details") {
        fun createRoute(methodType: String) = "giving_details/$methodType"
    }
    object ProphetessProfile : Screen("prophetess_profile", "Prophetess Profile")
    object MinistryInfo : Screen("ministry_info", "Ministry Info")
    object SocialHub : Screen("social_hub", "Social Hub")
    object UserProfile : Screen("user_profile", "User Profile")
    object Settings : Screen("settings", "Settings")

    // Details
    object VideoDetail : Screen("video_detail/{videoId}", "Video Detail") {
        fun createRoute(videoId: String) = "video_detail/$videoId"
    }
}
