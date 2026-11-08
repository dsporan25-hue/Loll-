package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime ORDER BY views DESC")
    fun observeAllAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime WHERE isFeatured = 1 LIMIT 1")
    fun observeFeaturedAnime(): Flow<AnimeEntity?>

    @Query("SELECT * FROM anime ORDER BY views DESC LIMIT 10")
    fun observeTrendingAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime ORDER BY year DESC, rating DESC LIMIT 10")
    fun observeLatestAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime ORDER BY rating DESC LIMIT 10")
    fun observeTopRatedAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime WHERE id = :id LIMIT 1")
    suspend fun getAnimeById(id: String): AnimeEntity?

    @Query("SELECT * FROM episodes WHERE animeId = :animeId ORDER BY episodeNum ASC")
    fun observeEpisodesForAnime(animeId: String): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodes WHERE animeId = :animeId ORDER BY episodeNum ASC")
    suspend fun getEpisodesForAnime(animeId: String): List<EpisodeEntity>

    @Query("SELECT * FROM anime WHERE title LIKE '%' || :query || '%' OR genres LIKE '%' || :query || '%' OR synopsis LIKE '%' || :query || '%'")
    fun searchAnime(query: String): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnime(animeList: List<AnimeEntity>)

    @Query("DELETE FROM anime WHERE id = :animeId")
    suspend fun deleteAnimeById(animeId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisodes(episodes: List<EpisodeEntity>)

    @Query("DELETE FROM episodes WHERE id = :episodeId")
    suspend fun deleteEpisodeById(episodeId: Long)

    @Query("DELETE FROM episodes WHERE animeId = :animeId")
    suspend fun deleteEpisodesForAnime(animeId: String)

    // Watchlist
    @Query("SELECT * FROM watchlist ORDER BY addedAt DESC")
    fun observeWatchlist(): Flow<List<WatchlistEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE animeId = :animeId)")
    fun isAnimeInWatchlist(animeId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchlist(item: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE animeId = :animeId")
    suspend fun removeFromWatchlist(animeId: String)

    // Watch History
    @Query("SELECT * FROM watch_history ORDER BY lastWatchedTimestamp DESC LIMIT 10")
    fun observeWatchHistory(): Flow<List<WatchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWatchHistory(item: WatchHistoryEntity)

    @Query("DELETE FROM watch_history")
    suspend fun clearWatchHistory()

    // Announcements
    @Query("SELECT * FROM announcements WHERE isActive = 1 ORDER BY createdAt DESC LIMIT 1")
    fun observeActiveAnnouncement(): Flow<AnnouncementEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAnnouncement(announcement: AnnouncementEntity)

    @Query("UPDATE announcements SET isActive = 0")
    suspend fun deactivateAllAnnouncements()

    @Query("SELECT COUNT(*) FROM anime")
    suspend fun getAnimeCount(): Int

    @Query("SELECT COUNT(*) FROM episodes")
    suspend fun getEpisodeCount(): Int
}
