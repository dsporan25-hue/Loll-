// ANIME STUDIO — Admin Control Engine
// Full Admin Dashboard CRUD & Configuration

const DB_KEYS = {
  ANIME: "animestudio_catalog_v1",
  WATCHLIST: "animestudio_watchlist_v1",
  HISTORY: "animestudio_history_v1",
  USER: "animestudio_user_v1",
  ANNOUNCEMENT: "animestudio_announcement_v1",
  SETTINGS: "animestudio_settings_v1",
  ADMIN_AUTH: "animestudio_admin_auth_v1",
  USERS_LIST: "animestudio_users_list_v1"
};

const DEFAULT_USERS = [
  { id: "u-1", name: "Kenji Sato", email: "kenji@otakumail.com", role: "Viewer", watchlistCount: 8, status: "Active" },
  { id: "u-2", name: "Aoi Miyamoto", email: "aoi@tokyoproductions.jp", role: "Contributor", watchlistCount: 14, status: "Active" },
  { id: "u-3", name: "Riku Tanaka", email: "riku.fan@gmail.com", role: "Viewer", watchlistCount: 3, status: "Active" },
  { id: "u-4", name: "SpamBot_99", email: "bot@anonymous-crawler.net", role: "Suspicious", watchlistCount: 0, status: "Deactivated" }
];

let catalogData = [];
let currentAdminTab = "overview";

document.addEventListener("DOMContentLoaded", () => {
  checkAdminAuth();
  loadCatalogData();
  setupSettingsTab();
  setupAnnouncementsTab();
  setupUsersTab();
});

function checkAdminAuth() {
  const isAuth = sessionStorage.getItem(DB_KEYS.ADMIN_AUTH) === "true";
  const lockScreen = document.getElementById("adminLockScreen");
  const layout = document.getElementById("adminLayout");

  if (isAuth) {
    lockScreen.style.display = "none";
    layout.style.display = "flex";
    renderAllAdminViews();
  } else {
    lockScreen.style.display = "flex";
    layout.style.display = "none";
  }
}

function verifyAdminLogin() {
  const pass = document.getElementById("adminPasswordInput").value.trim();
  // Secure default passcode for admin access
  if (pass === "admin123" || pass === "animestudio2026") {
    sessionStorage.setItem(DB_KEYS.ADMIN_AUTH, "true");
    checkAdminAuth();
  } else {
    alert("Incorrect administrative passcode. Please enter the valid admin credential.");
  }
}

function adminLogout() {
  sessionStorage.removeItem(DB_KEYS.ADMIN_AUTH);
  window.location.reload();
}

function loadCatalogData() {
  const stored = localStorage.getItem(DB_KEYS.ANIME);
  if (stored) {
    try {
      catalogData = JSON.parse(stored);
    } catch (e) {
      catalogData = [];
    }
  } else {
    catalogData = [];
  }
}

function saveCatalogData() {
  localStorage.setItem(DB_KEYS.ANIME, JSON.stringify(catalogData));
  // Dispatch custom storage event for same-window updates
  window.dispatchEvent(new Event("storage"));
  renderAllAdminViews();
}

function renderAllAdminViews() {
  renderOverview();
  renderAnimeManagementTable(catalogData);
  populateEpisodeAnimeSelector();
}

// Tab Switching
function switchAdminTab(tabName) {
  currentAdminTab = tabName;
  const tabs = ["overview", "anime", "episodes", "announcements", "users", "settings"];

  tabs.forEach(t => {
    const elem = document.getElementById(`tab${t.charAt(0).toUpperCase() + t.slice(1)}`);
    if (elem) elem.style.display = (t === tabName) ? "block" : "none";
  });

  document.querySelectorAll(".admin-nav-item").forEach(item => {
    item.classList.toggle("active", item.textContent.toLowerCase().includes(tabName));
  });

  const titles = {
    overview: "Dashboard Overview",
    anime: "Anime Catalog Management",
    episodes: "Episode & Video Source Control",
    announcements: "Broadcast Notice Banner",
    users: "User Account Administration",
    settings: "Website & SEO Configuration"
  };
  document.getElementById("adminTabTitle").textContent = titles[tabName] || "Control Center";
}

// 1. Overview Tab
function renderOverview() {
  document.getElementById("statTotalAnime").textContent = catalogData.length;

  let totalEps = 0;
  let totalViews = 0;
  catalogData.forEach(a => {
    totalEps += (a.episodes ? a.episodes.length : 0);
    totalViews += (a.views || 0);
  });

  document.getElementById("statTotalEpisodes").textContent = totalEps;
  document.getElementById("statTotalViews").textContent = totalViews > 1000 ? `${(totalViews / 1000).toFixed(1)}k` : totalViews;

  const tbody = document.getElementById("overviewTableBody");
  tbody.innerHTML = catalogData.slice(0, 6).map(anime => `
    <tr>
      <td><img src="${anime.poster}" alt="Poster" class="table-thumb" onerror="this.src='https://images.unsplash.com/photo-1578632767115-351597cf2477?w=100&q=80'"></td>
      <td>
        <b>${escapeHtml(anime.title)}</b><br>
        <small style="color:var(--text-muted);">${escapeHtml(anime.jpTitle || "")}</small>
      </td>
      <td>${anime.genres.slice(0, 2).join(", ")}</td>
      <td><span style="color:var(--gold);">★ ${anime.rating}</span></td>
      <td>${anime.episodes ? anime.episodes.length : 0} eps</td>
      <td><span style="color:#00E676;">${anime.status}</span></td>
      <td>
        <div class="action-btn-group">
          <button class="btn btn-sm btn-outline" onclick="editAnime('${anime.id}')">Edit</button>
          <button class="btn btn-sm btn-secondary" onclick="deleteAnime('${anime.id}')">Delete</button>
        </div>
      </td>
    </tr>
  `).join("");
}

// 2. Anime Management Tab
function renderAnimeManagementTable(list) {
  const tbody = document.getElementById("animeManagementTableBody");
  tbody.innerHTML = list.map(anime => `
    <tr>
      <td><img src="${anime.poster}" alt="Poster" class="table-thumb" onerror="this.src='https://images.unsplash.com/photo-1578632767115-351597cf2477?w=100&q=80'"></td>
      <td>
        <b>${escapeHtml(anime.title)}</b><br>
        <small style="color:var(--text-muted);">${escapeHtml(anime.studio || "Studio")}</small>
      </td>
      <td>${anime.year}</td>
      <td>${anime.genres.join(", ")}</td>
      <td>★ ${anime.rating}</td>
      <td>${anime.featured ? '<span style="color:var(--primary); font-weight:700;">★ Featured</span>' : 'Standard'}</td>
      <td>
        <div class="action-btn-group">
          <button class="btn btn-sm btn-outline" onclick="editAnime('${anime.id}')">Edit</button>
          <button class="btn btn-sm btn-secondary" style="color:#FF5252;" onclick="deleteAnime('${anime.id}')">Delete</button>
        </div>
      </td>
    </tr>
  `).join("");
}

function filterAdminAnime(term) {
  const q = term.toLowerCase();
  const filtered = catalogData.filter(a =>
    a.title.toLowerCase().includes(q) ||
    a.genres.some(g => g.toLowerCase().includes(q))
  );
  renderAnimeManagementTable(filtered);
}

// Add/Edit Anime Modal
function openAddAnimeModal() {
  document.getElementById("animeModalTitle").textContent = "Add New Anime";
  document.getElementById("editAnimeId").value = "";
  document.getElementById("animeTitleInput").value = "";
  document.getElementById("animeJpTitleInput").value = "";
  document.getElementById("animeGenresInput").value = "Action, Fantasy";
  document.getElementById("animeRatingInput").value = "8.8";
  document.getElementById("animeYearInput").value = "2025";
  document.getElementById("animeStatusInput").value = "Completed";
  document.getElementById("animeStudioInput").value = "Anime Studio Works";
  document.getElementById("animePosterInput").value = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&auto=format&fit=crop&q=80";
  document.getElementById("animeBannerInput").value = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1600&auto=format&fit=crop&q=80";
  document.getElementById("animeSynopsisInput").value = "";
  document.getElementById("animeFeaturedInput").checked = false;

  document.getElementById("animeModal").style.display = "flex";
}

function editAnime(id) {
  const anime = catalogData.find(a => a.id === id);
  if (!anime) return;

  document.getElementById("animeModalTitle").textContent = "Edit Anime Information";
  document.getElementById("editAnimeId").value = anime.id;
  document.getElementById("animeTitleInput").value = anime.title;
  document.getElementById("animeJpTitleInput").value = anime.jpTitle || "";
  document.getElementById("animeGenresInput").value = anime.genres.join(", ");
  document.getElementById("animeRatingInput").value = anime.rating;
  document.getElementById("animeYearInput").value = anime.year;
  document.getElementById("animeStatusInput").value = anime.status;
  document.getElementById("animeStudioInput").value = anime.studio || "";
  document.getElementById("animePosterInput").value = anime.poster || "";
  document.getElementById("animeBannerInput").value = anime.banner || "";
  document.getElementById("animeSynopsisInput").value = anime.synopsis || "";
  document.getElementById("animeFeaturedInput").checked = !!anime.featured;

  document.getElementById("animeModal").style.display = "flex";
}

function closeAnimeModal() {
  document.getElementById("animeModal").style.display = "none";
}

function saveAnimeRecord() {
  const id = document.getElementById("editAnimeId").value;
  const title = document.getElementById("animeTitleInput").value.trim();
  if (!title) {
    alert("Please enter a valid Anime Title.");
    return;
  }

  const jpTitle = document.getElementById("animeJpTitleInput").value.trim();
  const genres = document.getElementById("animeGenresInput").value.split(",").map(g => g.trim()).filter(g => g);
  const rating = parseFloat(document.getElementById("animeRatingInput").value) || 8.0;
  const year = parseInt(document.getElementById("animeYearInput").value) || 2024;
  const status = document.getElementById("animeStatusInput").value;
  const studio = document.getElementById("animeStudioInput").value.trim();
  const poster = document.getElementById("animePosterInput").value.trim();
  const banner = document.getElementById("animeBannerInput").value.trim();
  const synopsis = document.getElementById("animeSynopsisInput").value.trim();
  const featured = document.getElementById("animeFeaturedInput").checked;

  if (featured) {
    // Unset other featured items so there's one primary featured
    catalogData.forEach(a => a.featured = false);
  }

  if (id) {
    // Update existing
    const index = catalogData.findIndex(a => a.id === id);
    if (index !== -1) {
      catalogData[index] = {
        ...catalogData[index],
        title, jpTitle, genres, rating, year, status, studio, poster, banner, synopsis, featured
      };
    }
  } else {
    // Create new
    const newAnime = {
      id: `anime-${Date.now()}`,
      title,
      jpTitle,
      genres,
      rating,
      year,
      status,
      studio,
      poster,
      banner,
      synopsis,
      featured,
      views: 120,
      episodes: [
        {
          num: 1,
          title: "Episode 1: The Beginning",
          duration: "24m",
          videoUrl: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
          thumbnail: poster
        }
      ]
    };
    catalogData.unshift(newAnime);
  }

  saveCatalogData();
  closeAnimeModal();
}

function deleteAnime(id) {
  if (confirm("Are you sure you want to permanently delete this anime from the catalog?")) {
    catalogData = catalogData.filter(a => a.id !== id);
    saveCatalogData();
  }
}

// 3. Episodes Tab
function populateEpisodeAnimeSelector() {
  const sel = document.getElementById("episodeAnimeSelector");
  if (!sel) return;
  sel.innerHTML = catalogData.map(a => `
    <option value="${a.id}">${escapeHtml(a.title)} (${a.episodes ? a.episodes.length : 0} episodes)</option>
  `).join("");

  if (catalogData.length > 0) {
    loadEpisodesForSelectedAnime(catalogData[0].id);
  }
}

function loadEpisodesForSelectedAnime(animeId) {
  const anime = catalogData.find(a => a.id === animeId);
  const tbody = document.getElementById("episodesTableBody");
  if (!anime || !anime.episodes) {
    tbody.innerHTML = `<tr><td colspan="5" style="text-align:center; color:var(--text-muted);">No episodes found.</td></tr>`;
    return;
  }

  tbody.innerHTML = anime.episodes.map((ep, idx) => `
    <tr>
      <td><b>EP ${ep.num}</b></td>
      <td>${escapeHtml(ep.title)}</td>
      <td>${ep.duration || '24m'}</td>
      <td style="max-width:320px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap; color:var(--text-muted); font-size:0.85rem;">
        ${escapeHtml(ep.videoUrl)}
      </td>
      <td>
        <button class="btn btn-sm btn-secondary" onclick="deleteEpisode('${anime.id}', ${idx})">Delete</button>
      </td>
    </tr>
  `).join("");
}

function openAddEpisodeModal() {
  document.getElementById("episodeNumInput").value = "1";
  document.getElementById("episodeTitleInput").value = "";
  document.getElementById("episodeDurationInput").value = "24m";
  document.getElementById("episodeVideoUrlInput").value = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4";
  document.getElementById("episodeModal").style.display = "flex";
}

function closeEpisodeModal() {
  document.getElementById("episodeModal").style.display = "none";
}

function saveEpisodeRecord() {
  const animeId = document.getElementById("episodeAnimeSelector").value;
  const anime = catalogData.find(a => a.id === animeId);
  if (!anime) return;

  const num = parseInt(document.getElementById("episodeNumInput").value) || (anime.episodes ? anime.episodes.length + 1 : 1);
  const title = document.getElementById("episodeTitleInput").value.trim() || `Episode ${num}`;
  const duration = document.getElementById("episodeDurationInput").value.trim() || "24m";
  const videoUrl = document.getElementById("episodeVideoUrlInput").value.trim();

  if (!videoUrl) {
    alert("Please provide a valid authorized streaming URL.");
    return;
  }

  if (!anime.episodes) anime.episodes = [];
  anime.episodes.push({
    num,
    title,
    duration,
    videoUrl,
    thumbnail: anime.poster
  });

  saveCatalogData();
  closeEpisodeModal();
  loadEpisodesForSelectedAnime(animeId);
}

function deleteEpisode(animeId, index) {
  const anime = catalogData.find(a => a.id === animeId);
  if (!anime || !anime.episodes) return;
  if (confirm("Delete this episode?")) {
    anime.episodes.splice(index, 1);
    saveCatalogData();
    loadEpisodesForSelectedAnime(animeId);
  }
}

// 4. Announcements
function setupAnnouncementsTab() {
  const saved = localStorage.getItem(DB_KEYS.ANNOUNCEMENT);
  if (saved) {
    try {
      const data = JSON.parse(saved);
      document.getElementById("adminAnnouncementInput").value = data.text || "";
    } catch(e) {}
  }
}

function saveAnnouncement() {
  const text = document.getElementById("adminAnnouncementInput").value.trim();
  if (!text) {
    alert("Please enter announcement text.");
    return;
  }
  localStorage.setItem(DB_KEYS.ANNOUNCEMENT, JSON.stringify({ active: true, text }));
  window.dispatchEvent(new Event("storage"));
  alert("Notice banner published to public website!");
}

function clearAnnouncement() {
  localStorage.removeItem(DB_KEYS.ANNOUNCEMENT);
  document.getElementById("adminAnnouncementInput").value = "";
  window.dispatchEvent(new Event("storage"));
  alert("Notice banner disabled.");
}

// 5. Users Tab
function setupUsersTab() {
  let users = DEFAULT_USERS;
  const stored = localStorage.getItem(DB_KEYS.USERS_LIST);
  if (stored) {
    try { users = JSON.parse(stored); } catch(e) {}
  } else {
    localStorage.setItem(DB_KEYS.USERS_LIST, JSON.stringify(DEFAULT_USERS));
  }

  const tbody = document.getElementById("usersTableBody");
  tbody.innerHTML = users.map(u => `
    <tr>
      <td><b>${escapeHtml(u.name)}</b></td>
      <td>${escapeHtml(u.email)}</td>
      <td><span class="role-badge">${u.role}</span></td>
      <td>${u.watchlistCount} saved</td>
      <td><span style="color:${u.status === 'Active' ? '#00E676' : '#FF5252'}; font-weight:700;">${u.status}</span></td>
      <td>
        <button class="btn btn-sm ${u.status === 'Active' ? 'btn-secondary' : 'btn-outline'}" onclick="toggleUserStatus('${u.id}')">
          ${u.status === 'Active' ? 'Deactivate' : 'Activate'}
        </button>
      </td>
    </tr>
  `).join("");
}

function toggleUserStatus(userId) {
  let users = DEFAULT_USERS;
  const stored = localStorage.getItem(DB_KEYS.USERS_LIST);
  if (stored) {
    try { users = JSON.parse(stored); } catch(e) {}
  }

  const user = users.find(u => u.id === userId);
  if (user) {
    user.status = user.status === "Active" ? "Deactivated" : "Active";
    localStorage.setItem(DB_KEYS.USERS_LIST, JSON.stringify(users));
    setupUsersTab();
  }
}

// 6. Settings & Export
function setupSettingsTab() {
  const stored = localStorage.getItem(DB_KEYS.SETTINGS);
  if (stored) {
    try {
      const settings = JSON.parse(stored);
      document.getElementById("settingSiteTitle").value = settings.title || "ANIME STUDIO";
      document.getElementById("settingMetaDesc").value = settings.description || "";
      document.getElementById("settingGscTag").value = settings.gscTag || "";
    } catch(e) {}
  }
}

function saveSiteSettings() {
  const settings = {
    title: document.getElementById("settingSiteTitle").value.trim(),
    description: document.getElementById("settingMetaDesc").value.trim(),
    gscTag: document.getElementById("settingGscTag").value.trim()
  };
  localStorage.setItem(DB_KEYS.SETTINGS, JSON.stringify(settings));
  alert("Settings saved successfully!");
}

function exportDatabaseJson() {
  const data = {
    exportDate: new Date().toISOString(),
    catalog: catalogData,
    settings: JSON.parse(localStorage.getItem(DB_KEYS.SETTINGS) || "{}"),
    announcements: JSON.parse(localStorage.getItem(DB_KEYS.ANNOUNCEMENT) || "{}")
  };
  const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = `anime_studio_backup_${Date.now()}.json`;
  a.click();
}

function resetDefaultSeedData() {
  if (confirm("Reset anime database to default authorized seed catalog?")) {
    localStorage.removeItem(DB_KEYS.ANIME);
    window.location.reload();
  }
}

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
