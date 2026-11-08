package com.example.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AnimeDatabase
import com.example.data.AnimeEntity
import com.example.data.AnimeRepository
import com.example.data.EpisodeEntity
import com.example.data.WatchHistoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Home : Screen()
    object Search : Screen()
    object Watchlist : Screen()
    object WebPortal : Screen()
    object AdminDashboard : Screen()
    data class Details(val animeId: String) : Screen()
    data class Player(val animeId: String, val episodeIndex: Int) : Screen()
}

data class ContinueWatchingItem(
    val anime: AnimeEntity,
    val history: WatchHistoryEntity,
    val episode: EpisodeEntity?
)

class AnimeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AnimeRepository

    // Screen navigation stack
    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.Home))
    val currentScreen: StateFlow<Screen> = _screenStack
        .combine(MutableStateFlow(Unit)) { stack, _ -> stack.lastOrNull() ?: Screen.Home }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Screen.Home)

    // Network connectivity
    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow("All")
    val selectedGenre: StateFlow<String> = _selectedGenre.asStateFlow()

    init {
        val db = AnimeDatabase.getInstance(application)
        repository = AnimeRepository(db.animeDao())
        monitorNetwork(application)
    }

    val allAnime = repository.allAnime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val featuredAnime = repository.featuredAnime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val trendingAnime = repository.trendingAnime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val latestAnime = repository.latestAnime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val topRatedAnime = repository.topRatedAnime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeAnnouncement = repository.activeAnnouncement
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Watchlist combined with full anime info
    val watchlist = combine(repository.allAnime, repository.watchlistItems) { all, items ->
        val ids = items.map { it.animeId }.toSet()
        all.filter { it.id in ids }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Continue Watching combined with full anime info
    val continueWatching = combine(repository.allAnime, repository.watchHistory) { all, historyItems ->
        historyItems.mapNotNull { hist ->
            val anime = all.find { it.id == hist.animeId }
            if (anime != null) {
                ContinueWatchingItem(anime = anime, history = hist, episode = null)
            } else null
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered search results
    val searchResults = combine(
        repository.allAnime,
        _searchQuery,
        _selectedGenre
    ) { all, query, genre ->
        var list = all
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                it.jpTitle.lowercase().contains(q) ||
                it.genres.lowercase().contains(q) ||
                it.synopsis.lowercase().contains(q)
            }
        }
        if (genre != "All") {
            list = list.filter { it.genres.contains(genre, ignoreCase = true) }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation
    fun navigateTo(screen: Screen) {
        val stack = _screenStack.value.toMutableList()
        if (screen is Screen.Home) {
            _screenStack.value = listOf(Screen.Home)
        } else {
            stack.add(screen)
            _screenStack.value = stack
        }
    }

    fun popBack(): Boolean {
        val stack = _screenStack.value.toMutableList()
        return if (stack.size > 1) {
            stack.removeAt(stack.size - 1)
            _screenStack.value = stack
            true
        } else {
            false
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setGenreFilter(genre: String) {
        _selectedGenre.value = genre
    }

    fun toggleWatchlist(animeId: String) {
        viewModelScope.launch {
            val current = watchlist.value.any { it.id == animeId }
            repository.toggleWatchlist(animeId, current)
        }
    }

    fun updateProgress(animeId: String, epIndex: Int, percent: Float, timeSeconds: Long) {
        viewModelScope.launch {
            repository.updateWatchProgress(animeId, epIndex, percent, timeSeconds)
        }
    }

    fun clearWatchHistory() {
        viewModelScope.launch {
            repository.clearWatchHistory()
        }
    }

    suspend fun getAnimeDetails(animeId: String): AnimeEntity? {
        return repository.getAnimeById(animeId)
    }

    suspend fun getEpisodes(animeId: String): List<EpisodeEntity> {
        return repository.getEpisodesForAnime(animeId)
    }

    // Admin Operations
    fun saveAnimeAdmin(anime: AnimeEntity) {
        viewModelScope.launch {
            repository.saveAnime(anime)
        }
    }

    fun deleteAnimeAdmin(animeId: String) {
        viewModelScope.launch {
            repository.deleteAnime(animeId)
        }
    }

    fun addEpisodeAdmin(episode: EpisodeEntity) {
        viewModelScope.launch {
            repository.addEpisode(episode)
        }
    }

    fun deleteEpisodeAdmin(episodeId: Long) {
        viewModelScope.launch {
            repository.deleteEpisode(episodeId)
        }
    }

    fun publishAnnouncementAdmin(text: String) {
        viewModelScope.launch {
            repository.publishAnnouncement(text)
        }
    }

    fun disableAnnouncementsAdmin() {
        viewModelScope.launch {
            repository.disableAnnouncements()
        }
    }

    // Network Connectivity Monitor
    private fun monitorNetwork(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (connectivityManager == null) {
            _isOnline.value = true
            return
        }

        val active = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(active)
        _isOnline.value = caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isOnline.value = true
            }
            override fun onLost(network: Network) {
                _isOnline.value = false
            }
        })
    }
}
