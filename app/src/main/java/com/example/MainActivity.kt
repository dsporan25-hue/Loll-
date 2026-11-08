package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AdminDashboardScreen
import com.example.ui.AnimeDetailsScreen
import com.example.ui.AnimeViewModel
import com.example.ui.HomeScreen
import com.example.ui.Screen
import com.example.ui.SearchScreen
import com.example.ui.VideoPlayerScreen
import com.example.ui.WatchlistScreen
import com.example.ui.WebPortalScreen
import com.example.ui.theme.AnimeDarkBg
import com.example.ui.theme.AnimePrimary
import com.example.ui.theme.AnimeStudioTheme
import com.example.ui.theme.AnimeSurface
import com.example.ui.theme.TextMuted

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeStudioTheme {
                AnimeStudioApp()
            }
        }
    }
}

@Composable
fun AnimeStudioApp(
    viewModel: AnimeViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    // Show Bottom Navigation only on primary tabs
    val isPrimaryTab = currentScreen is Screen.Home ||
            currentScreen is Screen.Search ||
            currentScreen is Screen.Watchlist ||
            currentScreen is Screen.WebPortal ||
            currentScreen is Screen.AdminDashboard

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isPrimaryTab && currentScreen !is Screen.Player) {
                NavigationBar(
                    containerColor = AnimeSurface,
                    contentColor = Color.White,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("main_navigation_bar")
                ) {
                    NavigationBarItem(
                        selected = currentScreen is Screen.Home,
                        onClick = { viewModel.navigateTo(Screen.Home) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen is Screen.Home) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = navItemColors(),
                        modifier = Modifier.testTag("nav_home")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.Search,
                        onClick = { viewModel.navigateTo(Screen.Search) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen is Screen.Search) Icons.Filled.Search else Icons.Outlined.Search,
                                contentDescription = "Search"
                            )
                        },
                        label = { Text("Search", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = navItemColors(),
                        modifier = Modifier.testTag("nav_search")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.Watchlist,
                        onClick = { viewModel.navigateTo(Screen.Watchlist) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen is Screen.Watchlist) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Watchlist"
                            )
                        },
                        label = { Text("Watchlist", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = navItemColors(),
                        modifier = Modifier.testTag("nav_watchlist")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.WebPortal,
                        onClick = { viewModel.navigateTo(Screen.WebPortal) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen is Screen.WebPortal) Icons.Filled.Language else Icons.Outlined.Language,
                                contentDescription = "Web Portal"
                            )
                        },
                        label = { Text("Web Site", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = navItemColors(),
                        modifier = Modifier.testTag("nav_web_portal")
                    )

                    NavigationBarItem(
                        selected = currentScreen is Screen.AdminDashboard,
                        onClick = { viewModel.navigateTo(Screen.AdminDashboard) },
                        icon = {
                            Icon(
                                imageVector = if (currentScreen is Screen.AdminDashboard) Icons.Filled.AdminPanelSettings else Icons.Outlined.AdminPanelSettings,
                                contentDescription = "Admin"
                            )
                        },
                        label = { Text("Admin", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = navItemColors(),
                        modifier = Modifier.testTag("nav_admin")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AnimeDarkBg)
                .padding(if (isPrimaryTab && currentScreen !is Screen.Player) innerPadding else androidx.compose.foundation.layout.PaddingValues(0.dp))
        ) {
            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onAnimeClick = { animeId -> viewModel.navigateTo(Screen.Details(animeId)) },
                        onPlayClick = { animeId, epIdx -> viewModel.navigateTo(Screen.Player(animeId, epIdx)) },
                        onAdminClick = { viewModel.navigateTo(Screen.AdminDashboard) }
                    )
                }

                is Screen.Search -> {
                    SearchScreen(
                        viewModel = viewModel,
                        onAnimeClick = { animeId -> viewModel.navigateTo(Screen.Details(animeId)) }
                    )
                }

                is Screen.Watchlist -> {
                    WatchlistScreen(
                        viewModel = viewModel,
                        onAnimeClick = { animeId -> viewModel.navigateTo(Screen.Details(animeId)) }
                    )
                }

                is Screen.WebPortal -> {
                    WebPortalScreen(
                        initialTarget = "public",
                        onBack = { viewModel.popBack() }
                    )
                }

                is Screen.AdminDashboard -> {
                    AdminDashboardScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.popBack() }
                    )
                }

                is Screen.Details -> {
                    AnimeDetailsScreen(
                        animeId = screen.animeId,
                        viewModel = viewModel,
                        onBack = { viewModel.popBack() },
                        onPlayEpisode = { animeId, epIdx -> viewModel.navigateTo(Screen.Player(animeId, epIdx)) }
                    )
                }

                is Screen.Player -> {
                    VideoPlayerScreen(
                        animeId = screen.animeId,
                        episodeIndex = screen.episodeIndex,
                        viewModel = viewModel,
                        onBack = { viewModel.popBack() }
                    )
                }
            }
        }
    }
}

@Composable
private fun navItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color.White,
    selectedTextColor = AnimePrimary,
    indicatorColor = AnimePrimary,
    unselectedIconColor = TextMuted,
    unselectedTextColor = TextMuted
)
