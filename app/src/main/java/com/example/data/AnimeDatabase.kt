package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        AnimeEntity::class,
        EpisodeEntity::class,
        WatchHistoryEntity::class,
        WatchlistEntity::class,
        AnnouncementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao

    companion object {
        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getInstance(context: Context): AnimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java,
                    "anime_studio.db"
                ).addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.let { database ->
                    seedInitialData(database.animeDao())
                }
            }
        }

        private suspend fun seedInitialData(dao: AnimeDao) {
            val animeList = listOf(
                AnimeEntity(
                    id = "anime-1",
                    title = "Tears of Steel: Cyber Rebirth",
                    jpTitle = "鋼の涙 — サイバーリバース",
                    genres = "Sci-Fi, Mecha, Action",
                    rating = 9.4,
                    year = 2024,
                    studio = "Blender Open Movie / Anime Studio",
                    status = "Completed",
                    posterUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80",
                    bannerUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1600&auto=format&fit=crop&q=80",
                    synopsis = "In a dystopian cyberpunk neo-Tokyo, veteran mech pilots and tech scientists gather at the last sacred shrine to stage a desperate holographic neural reconstruction and stop humanity's rogue AI guardians.",
                    isFeatured = true,
                    views = 18450L
                ),
                AnimeEntity(
                    id = "anime-2",
                    title = "Sintel: Dragon Whisperer",
                    jpTitle = "シンテル — 竜の伝承",
                    genres = "Fantasy, Action, Drama",
                    rating = 9.1,
                    year = 2023,
                    studio = "Ton Roosendaal & Anime Studio",
                    status = "Completed",
                    posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&auto=format&fit=crop&q=80",
                    bannerUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1600&auto=format&fit=crop&q=80",
                    synopsis = "A lonely young warrior girl searches across snowstorms and treacherous arid desert ruins for Scales, a baby dragon she nurtured back to life after an ambush, only to discover a heartbreaking truth in the dragon's lair.",
                    isFeatured = false,
                    views = 14200L
                ),
                AnimeEntity(
                    id = "anime-3",
                    title = "Cosmos Laundromat: Astral Odyssey",
                    jpTitle = "コスモス・ランドロマット",
                    genres = "Fantasy, Sci-Fi, Adventure",
                    rating = 8.9,
                    year = 2024,
                    studio = "Mathieu Auvray & Anime Studio",
                    status = "Completed",
                    posterUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600&auto=format&fit=crop&q=80",
                    bannerUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1600&auto=format&fit=crop&q=80",
                    synopsis = "On a deserted island, Franck meets Victor, a mysterious salesman from an astral laundry service who offers him an otherworldly journey across alternate dimensions to find purpose.",
                    isFeatured = false,
                    views = 11200L
                ),
                AnimeEntity(
                    id = "anime-4",
                    title = "Elephants Dream: Infinite Citadel",
                    jpTitle = "無限回廊の城 — ドリーム",
                    genres = "Sci-Fi, Mystery, Psychological",
                    rating = 8.8,
                    year = 2022,
                    studio = "Orange Animation / Anime Studio",
                    status = "Completed",
                    posterUrl = "https://images.unsplash.com/photo-1563089145-599997674d42?w=600&auto=format&fit=crop&q=80",
                    bannerUrl = "https://images.unsplash.com/photo-1563089145-599997674d42?w=1600&auto=format&fit=crop&q=80",
                    synopsis = "Two wanderers, Proog and the inexperienced Emo, navigate through the labyrinthine clockwork interiors of a colossal thinking machine known as the Citadel.",
                    isFeatured = false,
                    views = 9800L
                ),
                AnimeEntity(
                    id = "anime-5",
                    title = "Chrono Valkyrie: Astral Awakening",
                    jpTitle = "クロノ・ヴァルキリー",
                    genres = "Isekai, Action, Romance",
                    rating = 9.3,
                    year = 2025,
                    studio = "Shibuya Production House",
                    status = "Airing",
                    posterUrl = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=600&auto=format&fit=crop&q=80",
                    bannerUrl = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=1600&auto=format&fit=crop&q=80",
                    synopsis = "Reincarnated into the high-tech empire of Elysium as a celestial blade-master, Ayame must choose between her sworn imperial vows and the rebel prince trying to shatter the planetary barrier.",
                    isFeatured = false,
                    views = 19500L
                ),
                AnimeEntity(
                    id = "anime-6",
                    title = "Cherry Blossom Academy: Magic Life",
                    jpTitle = "桜花学園の魔法生活",
                    genres = "Slice of Life, Romance, Comedy",
                    rating = 8.7,
                    year = 2024,
                    studio = "Kyoto Kyoto Works",
                    status = "Airing",
                    posterUrl = "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=600&auto=format&fit=crop&q=80",
                    bannerUrl = "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=1600&auto=format&fit=crop&q=80",
                    synopsis = "A warm, heartwarming school story following four best friends exploring enchanted after-school clubs, making herbal teas, and solving cute town mysteries in a scenic mountain village.",
                    isFeatured = false,
                    views = 8600L
                )
            )

            dao.insertAllAnime(animeList)

            // Episodes with authorized Creative Commons video stream URLs
            val episodesList = listOf(
                EpisodeEntity(
                    animeId = "anime-1",
                    episodeNum = 1,
                    title = "The Overtaking Protocol",
                    duration = "12m",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                    thumbnail = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80"
                ),
                EpisodeEntity(
                    animeId = "anime-1",
                    episodeNum = 2,
                    title = "Ghost in the Sanctuary",
                    duration = "14m",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                    thumbnail = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80"
                ),
                EpisodeEntity(
                    animeId = "anime-2",
                    episodeNum = 1,
                    title = "The Foundling in Winter",
                    duration = "15m",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                    thumbnail = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&auto=format&fit=crop&q=80"
                ),
                EpisodeEntity(
                    animeId = "anime-3",
                    episodeNum = 1,
                    title = "First Cycle — Alternate Reality",
                    duration = "12m",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    thumbnail = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600&auto=format&fit=crop&q=80"
                ),
                EpisodeEntity(
                    animeId = "anime-4",
                    episodeNum = 1,
                    title = "The Cable Tree Awakening",
                    duration = "11m",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                    thumbnail = "https://images.unsplash.com/photo-1563089145-599997674d42?w=600&auto=format&fit=crop&q=80"
                ),
                EpisodeEntity(
                    animeId = "anime-5",
                    episodeNum = 1,
                    title = "The Starlit Descent",
                    duration = "24m",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                    thumbnail = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=600&auto=format&fit=crop&q=80"
                ),
                EpisodeEntity(
                    animeId = "anime-6",
                    episodeNum = 1,
                    title = "Tea, Runes & Spring Morning",
                    duration = "22m",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
                    thumbnail = "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=600&auto=format&fit=crop&q=80"
                )
            )

            dao.insertAllEpisodes(episodesList)

            // Initial announcement
            dao.setAnnouncement(
                AnnouncementEntity(
                    id = "ann-1",
                    text = "Welcome to ANIME STUDIO official mobile app! All streaming content is authorized and free to watch.",
                    isActive = true
                )
            )
        }
    }
}
