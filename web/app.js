// ANIME STUDIO — Client Engine
// Shared real-time data layer with localStorage fallback

const DEFAULT_ANIME_CATALOG = [
  {
    id: "anime-1",
    title: "Tears of Steel: Cyber Rebirth",
    jpTitle: "鋼の涙 — サイバーリバース",
    genres: ["Sci-Fi", "Mecha", "Action"],
    rating: 9.4,
    year: 2024,
    studio: "Blender Open Movie / Anime Studio",
    status: "Completed",
    poster: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80",
    banner: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1600&auto=format&fit=crop&q=80",
    synopsis: "In a dystopian cyberpunk neo-Tokyo, a squad of veteran mech pilots and biomechanical tech scientists gather at the last sacred shrine to stage a desperate holographic neural reconstruction and stop humanity's rogue AI guardians.",
    featured: true,
    views: 18450,
    episodes: [
      {
        num: 1,
        title: "The Overtaking Protocol",
        duration: "12m",
        videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
        thumbnail: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80"
      },
      {
        num: 2,
        title: "Ghost in the Sanctuary",
        duration: "14m",
        videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
        thumbnail: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80"
      }
    ]
  },
  {
    id: "anime-2",
    title: "Sintel: Dragon Whisperer",
    jpTitle: "シンテル — 竜の伝承",
    genres: ["Fantasy", "Action", "Drama"],
    rating: 9.1,
    year: 2023,
    studio: "Ton Roosendaal & Anime Studio",
    status: "Completed",
    poster: "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&auto=format&fit=crop&q=80",
    banner: "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1600&auto=format&fit=crop&q=80",
    synopsis: "A lonely young warrior girl searches across snowstorms and treacherous arid desert ruins for Scales, a baby dragon she nurtured back to life after an ambush, only to discover a heartbreaking truth in the dragon's lair.",
    featured: false,
    views: 14200,
    episodes: [
      {
        num: 1,
        title: "The Foundling in Winter",
        duration: "15m",
        videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
        thumbnail: "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&auto=format&fit=crop&q=80"
      }
    ]
  },
  {
    id: "anime-3",
    title: "Cosmos Laundromat: Astral Odyssey",
    jpTitle: "コスモス・ランドロマット",
    genres: ["Fantasy", "Sci-Fi", "Adventure"],
    rating: 8.9,
    year: 2024,
    studio: "Mathieu Auvray & Anime Studio",
    status: "Completed",
    poster: "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600&auto=format&fit=crop&q=80",
    banner: "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1600&auto=format&fit=crop&q=80",
    synopsis: "On a deserted island, a depressed ram named Franck meets Victor, a mysterious salesman from an astral laundry service who offers him an otherworldly journey across alternate dimensions to find purpose.",
    featured: false,
    views: 11200,
    episodes: [
      {
        num: 1,
        title: "First Cycle — Alternate Reality",
        duration: "12m",
        videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        thumbnail: "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600&auto=format&fit=crop&q=80"
      }
    ]
  },
  {
    id: "anime-4",
    title: "Elephants Dream: Infinite Citadel",
    jpTitle: "無限回廊の城 — ドリーム",
    genres: ["Sci-Fi", "Mystery", "Psychological"],
    rating: 8.8,
    year: 2022,
    studio: "Orange Animation / Anime Studio",
    status: "Completed",
    poster: "https://images.unsplash.com/photo-1563089145-599997674d42?w=600&auto=format&fit=crop&q=80",
    banner: "https://images.unsplash.com/photo-1563089145-599997674d42?w=1600&auto=format&fit=crop&q=80",
    synopsis: "Two wanderers, Proog and the inexperienced Emo, navigate through the labyrinthine clockwork interiors of a colossal thinking machine known as the Citadel, where machine thoughts materialize as deadly phenomena.",
    featured: false,
    views: 9800,
    episodes: [
      {
        num: 1,
        title: "The Cable Tree Awakening",
        duration: "11m",
        videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        thumbnail: "https://images.unsplash.com/photo-1563089145-599997674d42?w=600&auto=format&fit=crop&q=80"
      }
    ]
  },
  {
    id: "anime-5",
    title: "Chrono Valkyrie: Astral Awakening",
    jpTitle: "クロノ・ヴァルキリー",
    genres: ["Isekai", "Action", "Romance"],
    rating: 9.3,
    year: 2025,
    studio: "Shibuya Production House",
    status: "Airing",
    poster: "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=600&auto=format&fit=crop&q=80",
    banner: "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=1600&auto=format&fit=crop&q=80",
    synopsis: "Reincarnated into the high-tech empire of Elysium as a celestial blade-master, Ayame must choose between her sworn imperial vows and the rebel prince trying to shatter the planetary barrier.",
    featured: false,
    views: 19500,
    episodes: [
      {
        num: 1,
        title: "The Starlit Descent",
        duration: "24m",
        videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        thumbnail: "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=600&auto=format&fit=crop&q=80"
      }
    ]
  },
  {
    id: "anime-6",
    title: "Cherry Blossom Academy: Slice of Magic",
    jpTitle: "桜花学園の魔法生活",
    genres: ["Slice of Life", "Romance", "Comedy"],
    rating: 8.7,
    year: 2024,
    studio: "Kyoto Kyoto Works",
    status: "Airing",
    poster: "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=600&auto=format&fit=crop&q=80",
    banner: "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=1600&auto=format&fit=crop&q=80",
    synopsis: "A warm, heartwarming school story following four best friends exploring enchanted after-school clubs, making herbal teas, and solving cute town mysteries in a scenic mountain village.",
    featured: false,
    views: 8600,
    episodes: [
      {
        num: 1,
        title: "Tea, Runes & Spring Morning",
        duration: "22m",
        videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
        thumbnail: "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=600&auto=format&fit=crop&q=80"
      }
    ]
  }
];

// LocalStorage Database Keys
const DB_KEYS = {
  ANIME: "animestudio_catalog_v1",
  WATCHLIST: "animestudio_watchlist_v1",
  HISTORY: "animestudio_history_v1",
  USER: "animestudio_user_v1",
  ANNOUNCEMENT: "animestudio_announcement_v1",
  SETTINGS: "animestudio_settings_v1"
};

// Initialize or load catalog
function getCatalog() {
  const stored = localStorage.getItem(DB_KEYS.ANIME);
  if (!stored) {
    localStorage.setItem(DB_KEYS.ANIME, JSON.stringify(DEFAULT_ANIME_CATALOG));
    return DEFAULT_ANIME_CATALOG;
  }
  try {
    return JSON.parse(stored);
  } catch (e) {
    return DEFAULT_ANIME_CATALOG;
  }
}

function getWatchlist() {
  const stored = localStorage.getItem(DB_KEYS.WATCHLIST);
  return stored ? JSON.parse(stored) : [];
}

function saveWatchlist(list) {
  localStorage.setItem(DB_KEYS.WATCHLIST, JSON.stringify(list));
  updateWatchlistBadge();
}

function getHistory() {
  const stored = localStorage.getItem(DB_KEYS.HISTORY);
  return stored ? JSON.parse(stored) : [];
}

function saveHistory(hist) {
  localStorage.setItem(DB_KEYS.HISTORY, JSON.stringify(hist));
}

let currentSelectedAnime = null;
let currentPlayingEpisodeIndex = 0;

// Application Boot
document.addEventListener("DOMContentLoaded", () => {
  renderHomepage();
  setupAnnouncement();
  setupUserProfile();
  updateWatchlistBadge();

  // Listen to storage events so when Admin changes anime in another tab, we update live!
  window.addEventListener("storage", (e) => {
    if (e.key === DB_KEYS.ANIME || e.key === DB_KEYS.ANNOUNCEMENT) {
      renderHomepage();
      setupAnnouncement();
    }
  });

  // Video time update listener for progress tracking
  const player = document.getElementById("mainVideoPlayer");
  if (player) {
    player.addEventListener("timeupdate", () => {
      if (player.duration > 0 && currentSelectedAnime) {
        const percent = (player.currentTime / player.duration) * 100;
        updateContinueWatchingProgress(currentSelectedAnime.id, currentPlayingEpisodeIndex, percent, player.currentTime);
      }
    });
  }
});

function renderHomepage() {
  const catalog = getCatalog();
  renderHero(catalog);
  renderTrending(catalog);
  renderLatest(catalog);
  renderPopular(catalog);
  renderContinueWatching();
  renderWatchlistGrid();
}

function renderHero(catalog) {
  const featured = catalog.find(a => a.featured) || catalog[0];
  if (!featured) return;

  const heroCard = document.getElementById("heroCard");
  const heroTitle = document.getElementById("heroTitle");
  const heroSynopsis = document.getElementById("heroSynopsis");
  const heroGenre = document.getElementById("heroGenre");
  const heroRating = document.getElementById("heroRating");

  heroCard.style.backgroundImage = `url('${featured.banner || featured.poster}')`;
  heroTitle.textContent = featured.title;
  heroSynopsis.textContent = featured.synopsis;
  heroGenre.textContent = featured.genres.join(" / ");
  heroRating.textContent = `★ ${featured.rating}`;

  // Update watchlist button state
  const watchlist = getWatchlist();
  const isSaved = watchlist.includes(featured.id);
  const btn = document.getElementById("heroWatchlistBtn");
  if (isSaved) {
    btn.classList.add("favorited");
  } else {
    btn.classList.remove("favorited");
  }

  window.currentFeaturedAnime = featured;
}

function playFeaturedAnime() {
  if (window.currentFeaturedAnime) {
    openVideoPlayer(window.currentFeaturedAnime, 0);
  }
}

function openFeaturedDetails() {
  if (window.currentFeaturedAnime) {
    openAnimeDetails(window.currentFeaturedAnime.id);
  }
}

function toggleHeroWatchlist() {
  if (window.currentFeaturedAnime) {
    toggleWatchlist(window.currentFeaturedAnime.id);
    const btn = document.getElementById("heroWatchlistBtn");
    btn.classList.toggle("favorited");
  }
}

function renderTrending(catalog) {
  const container = document.getElementById("trendingGrid");
  const sorted = [...catalog].sort((a, b) => (b.views || 0) - (a.views || 0));
  container.innerHTML = sorted.map(anime => createAnimeCardHtml(anime)).join("");
}

function renderLatest(catalog) {
  const container = document.getElementById("latestGrid");
  const sorted = [...catalog].sort((a, b) => b.year - a.year);
  container.innerHTML = sorted.map(anime => createAnimeCardHtml(anime)).join("");
}

function renderPopular(catalog) {
  const container = document.getElementById("popularGrid");
  const sorted = [...catalog].sort((a, b) => b.rating - a.rating);
  container.innerHTML = sorted.map(anime => createAnimeCardHtml(anime)).join("");
}

function createAnimeCardHtml(anime) {
  const watchlist = getWatchlist();
  const isSaved = watchlist.includes(anime.id);
  const epCount = anime.episodes ? anime.episodes.length : 1;

  return `
    <div class="anime-card" onclick="openAnimeDetails('${anime.id}')">
      <div class="card-poster-wrapper">
        <img src="${anime.poster}" alt="${escapeHtml(anime.title)}" class="card-poster" loading="lazy" onerror="this.src='https://images.unsplash.com/photo-1578632767115-351597cf2477?w=400&q=80'">
        <span class="card-score">★ ${anime.rating}</span>
        <span class="card-episodes-badge">${epCount} EP</span>
      </div>
      <div class="card-info">
        <h4 class="card-title">${escapeHtml(anime.title)}</h4>
        <div class="card-meta">
          <span>${anime.genres[0] || 'Anime'}</span>
          <span>${anime.year}</span>
        </div>
      </div>
    </div>
  `;
}

// Continue Watching Feature
function renderContinueWatching() {
  const history = getHistory();
  const section = document.getElementById("continueWatchingSection");
  const row = document.getElementById("continueWatchingRow");

  if (!history || history.length === 0) {
    section.style.display = "none";
    return;
  }

  section.style.display = "block";
  const catalog = getCatalog();

  const itemsHtml = history.map(item => {
    const anime = catalog.find(a => a.id === item.animeId);
    if (!anime) return "";
    const epNum = item.episodeIndex + 1;
    const progress = Math.min(100, Math.max(5, item.progressPercent || 20));

    return `
      <div class="continue-card" onclick="resumeWatching('${anime.id}', ${item.episodeIndex})">
        <div class="continue-thumb-box">
          <img src="${anime.banner || anime.poster}" alt="${escapeHtml(anime.title)}">
          <div class="continue-progress-bar" style="width: ${progress}%;"></div>
        </div>
        <div class="continue-info">
          <div class="continue-title">${escapeHtml(anime.title)}</div>
          <div class="continue-sub">Episode ${epNum} • ${Math.round(progress)}% completed</div>
        </div>
      </div>
    `;
  }).filter(h => h.length > 0).join("");

  row.innerHTML = itemsHtml;
}

function updateContinueWatchingProgress(animeId, episodeIndex, percent, timeSeconds) {
  let history = getHistory();
  // Filter out existing
  history = history.filter(h => h.animeId !== animeId);
  history.unshift({
    animeId,
    episodeIndex,
    progressPercent: percent,
    currentTime: timeSeconds,
    updatedAt: Date.now()
  });
  // Keep last 10
  history = history.slice(0, 10);
  saveHistory(history);
}

function clearWatchHistory() {
  saveHistory([]);
  renderContinueWatching();
}

function resumeWatching(animeId, episodeIndex) {
  const catalog = getCatalog();
  const anime = catalog.find(a => a.id === animeId);
  if (anime) {
    openVideoPlayer(anime, episodeIndex);
  }
}

// Watchlist
function toggleWatchlist(animeId) {
  let list = getWatchlist();
  if (list.includes(animeId)) {
    list = list.filter(id => id !== animeId);
  } else {
    list.push(animeId);
  }
  saveWatchlist(list);
  renderWatchlistGrid();
}

function renderWatchlistGrid() {
  const list = getWatchlist();
  const catalog = getCatalog();
  const grid = document.getElementById("watchlistGrid");
  const countLabel = document.getElementById("watchlistCount");

  countLabel.textContent = list.length;
  const savedAnime = catalog.filter(a => list.includes(a.id));

  if (savedAnime.length === 0) {
    grid.innerHTML = `
      <div style="grid-column: 1/-1; text-align:center; padding: 40px; color: var(--text-muted); background: var(--card-bg); border-radius: var(--radius-md);">
        <p>No anime in your watchlist yet. Browse titles and save your favorites!</p>
      </div>
    `;
    return;
  }

  grid.innerHTML = savedAnime.map(anime => createAnimeCardHtml(anime)).join("");
}

function updateWatchlistBadge() {
  const list = getWatchlist();
  const countLabel = document.getElementById("watchlistCount");
  if (countLabel) countLabel.textContent = list.length;
}

// Search
let searchTimeout = null;
function handleSearch(query) {
  clearTimeout(searchTimeout);
  const clearBtn = document.getElementById("clearSearchBtn");
  clearBtn.style.display = query ? "block" : "none";

  searchTimeout = setTimeout(() => {
    executeSearch(query.trim());
  }, 250);
}

function clearSearch() {
  const input = document.getElementById("searchInput");
  input.value = "";
  document.getElementById("clearSearchBtn").style.display = "none";
  executeSearch("");
}

function executeSearch(q) {
  const searchSection = document.getElementById("searchResultsSection");
  const searchGrid = document.getElementById("searchResultsGrid");
  const queryLabel = document.getElementById("searchQueryLabel");
  const countLabel = document.getElementById("searchResultsCount");

  if (!q) {
    searchSection.style.display = "none";
    return;
  }

  searchSection.style.display = "block";
  queryLabel.textContent = q;

  const catalog = getCatalog();
  const term = q.toLowerCase();
  const filtered = catalog.filter(a =>
    a.title.toLowerCase().includes(term) ||
    (a.jpTitle && a.jpTitle.toLowerCase().includes(term)) ||
    a.genres.some(g => g.toLowerCase().includes(term)) ||
    (a.synopsis && a.synopsis.toLowerCase().includes(term)) ||
    (a.studio && a.studio.toLowerCase().includes(term))
  );

  countLabel.textContent = `${filtered.length} found`;

  if (filtered.length === 0) {
    searchGrid.innerHTML = `
      <div style="grid-column: 1/-1; padding: 40px; text-align:center; color: var(--text-muted);">
        No anime found matching "${escapeHtml(q)}". Try searching for "Action", "Sci-Fi", or "Sintel".
      </div>
    `;
    return;
  }

  searchGrid.innerHTML = filtered.map(a => createAnimeCardHtml(a)).join("");
  searchSection.scrollIntoView({ behavior: "smooth" });
}

// Genre Filter
function filterByGenre(genre) {
  document.querySelectorAll(".genre-chip").forEach(btn => {
    btn.classList.toggle("active", btn.textContent.includes(genre));
  });

  if (genre === "All") {
    document.getElementById("searchResultsSection").style.display = "none";
    return;
  }

  executeSearch(genre);
}

// Navigation Helper
function navigateTo(target) {
  document.querySelectorAll(".nav-item").forEach(a => {
    a.classList.toggle("active", a.getAttribute("href") === `#${target}`);
  });

  const sectionMap = {
    home: "heroSection",
    trending: "trendingSection",
    popular: "popularSection",
    categories: "genreFilterBar",
    watchlist: "watchlistSection"
  };

  const elem = document.getElementById(sectionMap[target]);
  if (elem) {
    elem.scrollIntoView({ behavior: "smooth" });
  }
}

// Anime Details Modal
function openAnimeDetails(animeId) {
  const catalog = getCatalog();
  const anime = catalog.find(a => a.id === animeId);
  if (!anime) return;

  currentSelectedAnime = anime;
  const modal = document.getElementById("detailsModal");
  const banner = document.getElementById("detailsBanner");
  const poster = document.getElementById("detailsPoster");
  const title = document.getElementById("detailsTitle");
  const jpTitle = document.getElementById("detailsJpTitle");
  const rating = document.getElementById("detailsRating");
  const year = document.getElementById("detailsYear");
  const status = document.getElementById("detailsStatus");
  const synopsis = document.getElementById("detailsSynopsis");
  const studio = document.getElementById("detailsStudio");
  const tagsContainer = document.getElementById("detailsTags");
  const episodesList = document.getElementById("episodesListContainer");
  const epCounter = document.getElementById("detailsEpisodeCounter");

  banner.style.backgroundImage = `url('${anime.banner || anime.poster}')`;
  poster.src = anime.poster;
  title.textContent = anime.title;
  jpTitle.textContent = anime.jpTitle || "";
  rating.textContent = anime.rating;
  year.textContent = anime.year;
  status.textContent = anime.status;
  synopsis.textContent = anime.synopsis;
  studio.textContent = anime.studio || "Anime Studio";

  tagsContainer.innerHTML = anime.genres.map(g => `<span class="badge-genre">${g}</span>`).join(" ");

  const episodes = anime.episodes || [];
  epCounter.textContent = `${episodes.length} Episodes`;

  episodesList.innerHTML = episodes.map((ep, idx) => `
    <div class="episode-item-card" onclick="openVideoPlayerFromDetails(${idx})">
      <div class="ep-num-pill">${ep.num}</div>
      <div class="ep-info">
        <div class="ep-title">${escapeHtml(ep.title)}</div>
        <div class="ep-duration">${ep.duration || '24m'} • Authorized Stream</div>
      </div>
      <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor" style="color:var(--primary); flex-shrink:0;">
        <polygon points="5 3 19 12 5 21 5 3"></polygon>
      </svg>
    </div>
  `).join("");

  modal.style.display = "flex";
}

function closeDetailsModal() {
  document.getElementById("detailsModal").style.display = "none";
}

// Video Player Modal
function openVideoPlayer(anime, epIndex) {
  currentSelectedAnime = anime;
  currentPlayingEpisodeIndex = epIndex;

  const episode = anime.episodes[epIndex] || anime.episodes[0];
  if (!episode) return;

  const modal = document.getElementById("videoModal");
  const video = document.getElementById("mainVideoPlayer");
  const source = document.getElementById("videoSource");
  const animeTitle = document.getElementById("playerAnimeTitle");
  const epTitle = document.getElementById("playerEpisodeTitle");

  animeTitle.textContent = anime.title;
  epTitle.textContent = `EP ${episode.num}: ${episode.title}`;
  source.src = episode.videoUrl;
  video.load();

  // If we have history with timestamp, seek to it
  const history = getHistory();
  const past = history.find(h => h.animeId === anime.id && h.episodeIndex === epIndex);
  if (past && past.currentTime > 5) {
    video.currentTime = past.currentTime;
  }

  modal.style.display = "flex";
  video.play().catch(() => {});
}

function openVideoPlayerFromDetails(epIndex) {
  closeDetailsModal();
  openVideoPlayer(currentSelectedAnime, epIndex);
}

function closeVideoModal() {
  const modal = document.getElementById("videoModal");
  const video = document.getElementById("mainVideoPlayer");
  video.pause();
  modal.style.display = "none";
  renderContinueWatching();
}

function nextEpisode() {
  if (!currentSelectedAnime || !currentSelectedAnime.episodes) return;
  if (currentPlayingEpisodeIndex < currentSelectedAnime.episodes.length - 1) {
    openVideoPlayer(currentSelectedAnime, currentPlayingEpisodeIndex + 1);
  } else {
    alert("You have reached the latest episode!");
  }
}

function prevEpisode() {
  if (currentPlayingEpisodeIndex > 0) {
    openVideoPlayer(currentSelectedAnime, currentPlayingEpisodeIndex - 1);
  }
}

// Announcements
function setupAnnouncement() {
  const banner = document.getElementById("announcementBanner");
  const textElem = document.getElementById("announcementText");
  const saved = localStorage.getItem(DB_KEYS.ANNOUNCEMENT);

  if (saved) {
    try {
      const data = JSON.parse(saved);
      if (data.active && data.text) {
        textElem.textContent = data.text;
        banner.style.display = "flex";
        return;
      }
    } catch (e) {}
  }
  banner.style.display = "none";
}

function dismissAnnouncement() {
  document.getElementById("announcementBanner").style.display = "none";
}

// Profile Modal
function setupUserProfile() {
  const stored = localStorage.getItem(DB_KEYS.USER);
  const user = stored ? JSON.parse(stored) : { name: "Anime Member", email: "member@animestudio.com" };
  document.getElementById("userNameLabel").textContent = user.name.split(" ")[0];
  document.getElementById("userAvatar").textContent = user.name.charAt(0).toUpperCase();
}

function openProfileModal() {
  const modal = document.getElementById("profileModal");
  const stored = localStorage.getItem(DB_KEYS.USER);
  const user = stored ? JSON.parse(stored) : { name: "Anime Member", email: "member@animestudio.com" };

  document.getElementById("profileNameDisplay").textContent = user.name;
  document.getElementById("profileEmailDisplay").textContent = user.email;
  document.getElementById("profileNameInput").value = user.name;

  const history = getHistory();
  const watchlist = getWatchlist();

  document.getElementById("statWatchedEpisodes").textContent = history.length;
  document.getElementById("statWatchlistCount").textContent = watchlist.length;
  document.getElementById("statHoursStreamed").textContent = `${(history.length * 0.4).toFixed(1)}h`;

  modal.style.display = "flex";
}

function closeProfileModal() {
  document.getElementById("profileModal").style.display = "none";
}

function saveProfile() {
  const name = document.getElementById("profileNameInput").value.trim() || "Anime Fan";
  const user = { name, email: "member@animestudio.com" };
  localStorage.setItem(DB_KEYS.USER, JSON.stringify(user));
  setupUserProfile();
  closeProfileModal();
}

// Security Escape
function escapeHtml(str) {
  if (!str) return "";
  return str.replace(/[&<>"']/g, m => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#039;"
  })[m]);
}
