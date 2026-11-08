package com.example

import com.example.data.AnimeEntity
import com.example.data.EpisodeEntity
import com.example.data.WatchHistoryEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AnimeStudioUnitTest {

    @Test
    fun testAnimeEntityCreation() {
        val anime = AnimeEntity(
            id = "test-1",
            title = "Tears of Steel",
            jpTitle = "鋼の涙",
            genres = "Sci-Fi, Mecha, Action",
            rating = 9.4,
            year = 2024,
            studio = "Blender Open Movie",
            status = "Completed",
            posterUrl = "https://example.com/poster.jpg",
            bannerUrl = "https://example.com/banner.jpg",
            synopsis = "A futuristic saga.",
            isFeatured = true,
            views = 1000L
        )

        assertEquals("test-1", anime.id)
        assertEquals("Tears of Steel", anime.title)
        assertTrue(anime.genres.contains("Sci-Fi"))
        assertTrue(anime.isFeatured)
    }

    @Test
    fun testEpisodeEntity() {
        val ep = EpisodeEntity(
            id = 1L,
            animeId = "anime-1",
            episodeNum = 1,
            title = "Awakening",
            duration = "24m",
            videoUrl = "https://example.com/stream.mp4",
            thumbnail = "https://example.com/thumb.jpg"
        )

        assertEquals(1, ep.episodeNum)
        assertEquals("Awakening", ep.title)
        assertTrue(ep.videoUrl.endsWith(".mp4"))
    }

    @Test
    fun testWatchProgressCalculation() {
        val currentPosition = 60000L // 60s
        val totalDuration = 120000L  // 120s
        val progress = (currentPosition.toFloat() / totalDuration.toFloat()) * 100f

        val history = WatchHistoryEntity(
            animeId = "anime-1",
            episodeIndex = 0,
            progressPercent = progress,
            currentTimeSeconds = currentPosition / 1000,
            lastWatchedTimestamp = System.currentTimeMillis()
        )

        assertEquals(50f, history.progressPercent, 0.01f)
        assertEquals(60L, history.currentTimeSeconds)
    }

    @Test
    fun testSearchFilter() {
        val list = listOf(
            AnimeEntity("1", "Sintel", "シンテル", "Fantasy, Action", 9.1, 2023, "Blender", "Completed", "", "", "Dragon tale"),
            AnimeEntity("2", "Tears of Steel", "鋼の涙", "Sci-Fi, Mecha", 9.4, 2024, "Blender", "Completed", "", "", "Cyber robots"),
            AnimeEntity("3", "Chrono Valkyrie", "クロノ", "Isekai, Romance", 9.3, 2025, "Shibuya", "Airing", "", "", "Celestial blade")
        )

        val query = "sci-fi"
        val filtered = list.filter {
            it.title.lowercase().contains(query) ||
            it.genres.lowercase().contains(query) ||
            it.synopsis.lowercase().contains(query)
        }

        assertEquals(1, filtered.size)
        assertEquals("Tears of Steel", filtered.first().title)
    }
}
