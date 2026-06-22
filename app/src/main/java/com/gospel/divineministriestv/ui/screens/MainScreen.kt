package com.gospel.divineministriestv.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gospel.divineministriestv.ui.navigation.Screen
import com.gospel.divineministriestv.ui.screens.categories.CategoriesScreen
import com.gospel.divineministriestv.ui.screens.categories.CategoriesViewModel
import com.gospel.divineministriestv.ui.screens.giving.GivingDetailsScreen
import com.gospel.divineministriestv.ui.screens.giving.OfferingScreen
import com.gospel.divineministriestv.ui.screens.home.HomeScreen
import com.gospel.divineministriestv.ui.screens.home.HomeViewModel
import com.gospel.divineministriestv.ui.screens.info.MinistryInfoScreen
import com.gospel.divineministriestv.ui.screens.library.LibraryScreen
import com.gospel.divineministriestv.ui.screens.library.LibraryViewModel
import com.gospel.divineministriestv.ui.screens.live.LiveTvScreen
import com.gospel.divineministriestv.ui.screens.more.MoreScreen
import com.gospel.divineministriestv.ui.screens.onboarding.OnboardingScreen
import com.gospel.divineministriestv.ui.screens.prayer.PrayerCounselingScreen
import com.gospel.divineministriestv.ui.screens.profile.ProphetessProfileScreen
import com.gospel.divineministriestv.ui.screens.profile.ProphetessProfileViewModel
import com.gospel.divineministriestv.ui.screens.profile.UserProfileScreen
import com.gospel.divineministriestv.ui.screens.search.SearchScreen
import com.gospel.divineministriestv.ui.screens.settings.SettingsScreen
import com.gospel.divineministriestv.ui.screens.social.SocialMediaHubScreen
import com.gospel.divineministriestv.ui.screens.splash.SplashScreen
import com.gospel.divineministriestv.ui.screens.video.VideoDetailScreen
import com.gospel.divineministriestv.ui.screens.video.VideoDetailViewModel
import com.gospel.divineministriestv.ui.theme.RoyalGold
import com.gospel.divineministriestv.ui.theme.RoyalPurple

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.LiveTv,
        Screen.Library,
        Screen.Prayer,
        Screen.More
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = bottomNavItems.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.Black.copy(alpha = 0.9f),
                    contentColor = RoyalPurple
                ) {
                    bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = { Icon(screen.icon!!, contentDescription = null) },
                            label = { Text(screen.title) },
                            selected = selected,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = RoyalGold,
                                selectedTextColor = RoyalGold,
                                unselectedIconColor = RoyalPurple,
                                unselectedTextColor = RoyalPurple,
                                indicatorColor = RoyalGold.copy(alpha = 0.2f)
                            ),
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(onTimeout = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Onboarding.route) {
                OnboardingScreen(onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Home.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    onVideoClick = { videoId ->
                        navController.navigate(Screen.VideoDetail.createRoute(videoId))
                    },
                    onSeeAllVideos = {
                        navController.navigate(Screen.Library.route)
                    },
                    onSeeAllCategories = {
                        navController.navigate(Screen.Categories.route)
                    },
                    onProfileClick = {
                        navController.navigate("prophetess_profile")
                    },
                    onSearchClick = {
                        navController.navigate(Screen.Search.route)
                    },
                    viewModel = viewModel
                )
            }
            composable(Screen.LiveTv.route) { 
                val viewModel: HomeViewModel = hiltViewModel()
                LiveTvScreen(
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                ) 
            }
            composable(Screen.Library.route) { 
                val viewModel: LibraryViewModel = hiltViewModel()
                LibraryScreen(
                    onVideoClick = { videoId ->
                        navController.navigate(Screen.VideoDetail.createRoute(videoId))
                    },
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                ) 
            }
            composable("library_detail/{playlistId}") { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
                val viewModel: LibraryViewModel = hiltViewModel()
                LaunchedEffect(playlistId) {
                    viewModel.fetchVideos(playlistId)
                }
                LibraryScreen(
                    onVideoClick = { videoId ->
                        navController.navigate(Screen.VideoDetail.createRoute(videoId))
                    },
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
            composable(Screen.Prayer.route) { 
                PrayerCounselingScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.More.route) { 
                MoreScreen(
                    onNavigate = { route ->
                        if (route == "giving") {
                            navController.navigate("giving_main")
                        } else {
                            navController.navigate(route)
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.VideoDetail.route) { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
                val viewModel: VideoDetailViewModel = hiltViewModel()
                VideoDetailScreen(
                    videoId = videoId,
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
            
            // Feature Screens
            composable(Screen.Search.route) {
                SearchScreen(
                    onVideoClick = { videoId ->
                        navController.navigate(Screen.VideoDetail.createRoute(videoId))
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Categories.route) {
                val viewModel: CategoriesViewModel = hiltViewModel()
                CategoriesScreen(
                    onCategoryClick = { playlistId ->
                        navController.navigate("library_detail/$playlistId")
                    },
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
            composable("giving_main") {
                OfferingScreen(
                    onMethodClick = { methodType ->
                        navController.navigate(Screen.GivingDetails.createRoute(methodType))
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.GivingDetails.route) { backStackEntry ->
                val methodType = backStackEntry.arguments?.getString("methodType") ?: ""
                GivingDetailsScreen(
                    methodType = methodType,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("ministry_info") { 
                MinistryInfoScreen(onBack = { navController.popBackStack() }) 
            }
            composable("prophetess_profile") { 
                val viewModel: ProphetessProfileViewModel = hiltViewModel()
                ProphetessProfileScreen(
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
            composable("social_hub") { 
                SocialMediaHubScreen(onBack = { navController.popBackStack() }) 
            }
            composable("settings") { 
                SettingsScreen(onBack = { navController.popBackStack() }) 
            }
            composable("user_profile") {
                UserProfileScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
