package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class AnimeRepository(private val dao: AnimeDao) {

    val allAnime: Flow<List<AnimeEntity>> = dao.observeAllAnime()
    val featuredAnime: Flow<AnimeEntity?> = dao.observeFeaturedAnime()
    val trendingAnime: Flow<List<AnimeEntity>> = dao.observeTrendingAnime()
    val latestAnime: Flow<List<AnimeEntity>> = dao.observeLatestAnime()
    val topRatedAnime: Flow<List<AnimeEntity>> = dao.observeTopRatedAnime()
    val watchlistItems: Flow<List<WatchlistEntity>> = dao.observeWatchlist()
    val watchHistory: Flow<List<WatchHistoryEntity>> = dao.observeWatchHistory()
    val activeAnnouncement: Flow<AnnouncementEntity?> = dao.observeActiveAnnouncement()

    fun search(query: String): Flow<List<AnimeEntity>> {
        return dao.searchAnime(query)
    }

    suspend fun getAnimeById(id: String): AnimeEntity? {
        return dao.getAnimeById(id)
    }

    fun observeEpisodesForAnime(animeId: String): Flow<List<EpisodeEntity>> {
        return dao.observeEpisodesForAnime(animeId)
    }

    suspend fun getEpisodesForAnime(animeId: String): List<EpisodeEntity> {
        return dao.getEpisodesForAnime(animeId)
    }

    fun isAnimeInWatchlist(animeId: String): Flow<Boolean> {
        return dao.isAnimeInWatchlist(animeId)
    }

    suspend fun toggleWatchlist(animeId: String, currentStatus: Boolean) {
        if (currentStatus) {
            dao.removeFromWatchlist(animeId)
        } else {
            dao.addToWatchlist(WatchlistEntity(animeId = animeId))
        }
    }

    suspend fun updateWatchProgress(animeId: String, episodeIndex: Int, percent: Float, currentTimeSeconds: Long) {
        dao.updateWatchHistory(
            WatchHistoryEntity(
                animeId = animeId,
                episodeIndex = episodeIndex,
                progressPercent = percent,
                currentTimeSeconds = currentTimeSeconds,
                lastWatchedTimestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun clearWatchHistory() {
        dao.clearWatchHistory()
    }

    // Admin Operations
    suspend fun saveAnime(anime: AnimeEntity) {
        dao.insertAnime(anime)
    }

    suspend fun deleteAnime(animeId: String) {
        dao.deleteAnimeById(animeId)
        dao.deleteEpisodesForAnime(animeId)
        dao.removeFromWatchlist(animeId)
    }

    suspend fun addEpisode(episode: EpisodeEntity) {
        dao.insertEpisode(episode)
    }

    suspend fun deleteEpisode(episodeId: Long) {
        dao.deleteEpisodeById(episodeId)
    }

    suspend fun publishAnnouncement(text: String) {
        dao.deactivateAllAnnouncements()
        dao.setAnnouncement(
            AnnouncementEntity(
                id = "ann-${System.currentTimeMillis()}",
                text = text,
                isActive = true
            )
        )
    }

    suspend fun disableAnnouncements() {
        dao.deactivateAllAnnouncements()
    }

    suspend fun getStats(): Pair<Int, Int> {
        val animeCount = dao.getAnimeCount()
        val episodeCount = dao.getEpisodeCount()
        return Pair(animeCount, episodeCount)
    }
}
