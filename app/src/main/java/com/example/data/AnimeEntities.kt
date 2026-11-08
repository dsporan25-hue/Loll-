package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val jpTitle: String,
    val genres: String, // comma-separated e.g. "Sci-Fi, Mecha, Action"
    val rating: Double,
    val year: Int,
    val studio: String,
    val status: String, // "Completed", "Airing"
    val posterUrl: String,
    val bannerUrl: String,
    val synopsis: String,
    val isFeatured: Boolean = false,
    val views: Long = 0L
)

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val animeId: String,
    val episodeNum: Int,
    val title: String,
    val duration: String,
    val videoUrl: String,
    val thumbnail: String
)

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey val animeId: String,
    val episodeIndex: Int,
    val progressPercent: Float,
    val currentTimeSeconds: Long,
    val lastWatchedTimestamp: Long
)

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val animeId: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "announcements")
data class AnnouncementEntity(
    @PrimaryKey val id: String,
    val text: String,
    val isActive: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)

// UI Model combining anime and episodes
data class AnimeWithEpisodes(
    val anime: AnimeEntity,
    val episodes: List<EpisodeEntity>,
    val isWatchlist: Boolean = false,
    val history: WatchHistoryEntity? = null
)
