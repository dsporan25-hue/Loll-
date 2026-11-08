package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AnimeEntity
import com.example.data.EpisodeEntity
import com.example.ui.theme.AnimeCardBg
import com.example.ui.theme.AnimeDarkBg
import com.example.ui.theme.AnimeGold
import com.example.ui.theme.AnimePrimary
import com.example.ui.theme.AnimeSecondary
import com.example.ui.theme.AnimeSurface
import com.example.ui.theme.BorderColor
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun AdminDashboardScreen(
    viewModel: AnimeViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var isAuthenticated by remember { mutableStateOf(false) }
    var passwordInput by remember { mutableStateOf("admin123") }
    var authError by remember { mutableStateOf(false) }

    val animeList by viewModel.allAnime.collectAsState()
    val announcement by viewModel.activeAnnouncement.collectAsState()

    var showAddAnimeDialog by remember { mutableStateOf(false) }
    var showAddEpisodeDialog by remember { mutableStateOf(false) }
    var showAnnouncementDialog by remember { mutableStateOf(false) }
    var selectedAnimeForEpisode by remember { mutableStateOf<AnimeEntity?>(null) }

    // PIN Gate Screen
    if (!isAuthenticated) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(AnimeDarkBg)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AnimeCardBg)
                    .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FF2E63)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Admin Lock",
                        tint = AnimePrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "STUDIO ADMIN ACCESS",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "Enter administrator passcode to manage catalog & streams",
                    color = TextMuted,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text("Admin Passcode", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AnimeSurface,
                        unfocusedContainerColor = AnimeSurface,
                        focusedBorderColor = AnimePrimary,
                        unfocusedBorderColor = BorderColor,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (authError) {
                    Text(
                        text = "Invalid credentials. Try 'admin123'",
                        color = Color(0xFFFF5252),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (passwordInput.trim() == "admin123" || passwordInput.trim() == "animestudio2026") {
                            isAuthenticated = true
                            authError = false
                        } else {
                            authError = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AnimePrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().testTag("admin_unlock_button")
                ) {
                    Text("Unlock Admin Control")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onBack) {
                    Text("Return to App", color = TextMuted)
                }
            }
        }
        return
    }

    // Authenticated Dashboard
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AnimeDarkBg)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AnimeSurface)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Admin Control Center",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { showAddAnimeDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = AnimePrimary),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Anime", fontSize = 12.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Metrics Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricBox(
                        title = "Catalog",
                        value = animeList.size.toString(),
                        color = AnimePrimary,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Episodes",
                        value = (animeList.size * 2).toString(),
                        color = Color(0xFF00E676),
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Status",
                        value = "ONLINE",
                        color = Color(0xFF00E5FF),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Notice Banner Controller
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AnimeCardBg)
                        .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Campaign,
                                    contentDescription = "Announcement",
                                    tint = AnimeGold
                                )
                                Text(
                                    text = "Studio Notice Banner",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            Button(
                                onClick = { showAnnouncementDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = AnimeSurface),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("Edit Notice", color = Color.White, fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = announcement?.text ?: "No active broadcast notice.",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Anime Management List
            item {
                Text(
                    text = "Anime Catalog (${animeList.size})",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(animeList) { anime ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AnimeCardBg)
                        .border(1.dp, BorderColor, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = anime.title,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${anime.genres} • ★ ${anime.rating} • ${anime.status}",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Button(
                                onClick = {
                                    selectedAnimeForEpisode = anime
                                    showAddEpisodeDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = AnimeSurface),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("+ EP", color = Color.White, fontSize = 11.sp)
                            }

                            IconButton(
                                onClick = { viewModel.deleteAnimeAdmin(anime.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color(0xFFFF5252),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Anime Dialog
    if (showAddAnimeDialog) {
        var title by remember { mutableStateOf("") }
        var jpTitle by remember { mutableStateOf("") }
        var genres by remember { mutableStateOf("Action, Fantasy") }
        var rating by remember { mutableStateOf("8.8") }
        var year by remember { mutableStateOf("2025") }
        var studio by remember { mutableStateOf("Anime Studio Works") }
        var poster by remember { mutableStateOf("https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80") }
        var banner by remember { mutableStateOf("https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1600&auto=format&fit=crop&q=80") }
        var synopsis by remember { mutableStateOf("An epic anime saga of discovery and courage.") }

        AlertDialog(
            onDismissRequest = { showAddAnimeDialog = false },
            title = { Text("Add Anime to Studio Catalog", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Anime Title *") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = jpTitle,
                        onValueChange = { jpTitle = it },
                        label = { Text("Japanese / Romaji Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = genres,
                        onValueChange = { genres = it },
                        label = { Text("Genres (comma separated)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = synopsis,
                        onValueChange = { synopsis = it },
                        label = { Text("Synopsis") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            viewModel.saveAnimeAdmin(
                                AnimeEntity(
                                    id = "anime-${System.currentTimeMillis()}",
                                    title = title,
                                    jpTitle = jpTitle,
                                    genres = genres,
                                    rating = rating.toDoubleOrNull() ?: 8.5,
                                    year = year.toIntOrNull() ?: 2024,
                                    studio = studio,
                                    status = "Completed",
                                    posterUrl = poster,
                                    bannerUrl = banner,
                                    synopsis = synopsis,
                                    isFeatured = false,
                                    views = 100L
                                )
                            )
                            showAddAnimeDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AnimePrimary)
                ) {
                    Text("Add Anime")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddAnimeDialog = false }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            containerColor = AnimeCardBg
        )
    }

    // Add Episode Dialog
    if (showAddEpisodeDialog && selectedAnimeForEpisode != null) {
        var epNum by remember { mutableStateOf("1") }
        var epTitle by remember { mutableStateOf("Awakening of the Power") }
        var duration by remember { mutableStateOf("24m") }
        var streamUrl by remember { mutableStateOf("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4") }

        AlertDialog(
            onDismissRequest = { showAddEpisodeDialog = false },
            title = { Text("Add Episode to ${selectedAnimeForEpisode!!.title}", color = TextPrimary, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = epNum,
                        onValueChange = { epNum = it },
                        label = { Text("Episode Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = epTitle,
                        onValueChange = { epTitle = it },
                        label = { Text("Episode Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = streamUrl,
                        onValueChange = { streamUrl = it },
                        label = { Text("Authorized Stream URL (MP4)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addEpisodeAdmin(
                            EpisodeEntity(
                                animeId = selectedAnimeForEpisode!!.id,
                                episodeNum = epNum.toIntOrNull() ?: 1,
                                title = epTitle,
                                duration = duration,
                                videoUrl = streamUrl,
                                thumbnail = selectedAnimeForEpisode!!.posterUrl
                            )
                        )
                        showAddEpisodeDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AnimePrimary)
                ) {
                    Text("Save Episode")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddEpisodeDialog = false }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            containerColor = AnimeCardBg
        )
    }

    // Announcement Dialog
    if (showAnnouncementDialog) {
        var noticeText by remember { mutableStateOf(announcement?.text ?: "New authorized anime releases added to Anime Studio catalog!") }

        AlertDialog(
            onDismissRequest = { showAnnouncementDialog = false },
            title = { Text("Publish Notice Banner", color = TextPrimary) },
            text = {
                OutlinedTextField(
                    value = noticeText,
                    onValueChange = { noticeText = it },
                    label = { Text("Banner Announcement Text") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.publishAnnouncementAdmin(noticeText)
                        showAnnouncementDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AnimePrimary)
                ) {
                    Text("Publish")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.disableAnnouncementsAdmin()
                    showAnnouncementDialog = false
                }) {
                    Text("Disable Notice", color = Color(0xFFFF5252))
                }
            },
            containerColor = AnimeCardBg
        )
    }
}

@Composable
private fun MetricBox(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(AnimeCardBg)
            .border(1.dp, BorderColor, RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = title,
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                color = color,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
