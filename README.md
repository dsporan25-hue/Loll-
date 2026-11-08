# ANIME STUDIO — Full-Stack Anime Discovery, Streaming & Admin Platform

![ANIME STUDIO](web/styles.css)

**ANIME STUDIO** is a production-grade, full-stack anime streaming, discovery, and studio management platform built for both **Web** and **Android (Jetpack Compose)**.

---

## 🌟 Platform Highlights

### 1. 🌐 Public User Website
- **Modern Anime Aesthetics**: Cyberpunk & dark neon aesthetic (`#0A0B14`, `#FF2E63`, `#8A2BE2`) with high performance.
- **Hero Showcase Carousel**: Highlighted masterpiece anime with quick play, storyline synopsis, and watchlist toggle.
- **Video Player**: High-definition video player with custom playback controls, timeline scrubber, episode progression, and authorized Creative Commons streaming sources.
- **Continue Watching Row**: Real-time watch history tracking with exact resume percentage.
- **Categories & Genres**: Instant filtering by Action, Sci-Fi, Fantasy, Romance, Isekai, Shonen, and Slice of Life.
- **Search System**: Live search across anime titles, Japanese romaji, synopses, and studios.
- **Watchlist & User Profile**: Saved favorites and personalized watch statistics.

### 2. 🛡️ Secure Admin Control Center
- **Access URL**: `admin.html` (Separate administrative portal protected by passcode/credentials).
- **Default Admin Passcode**: `admin123` (or `animestudio2026`).
- **Real-Time Statistics**: Live counts of Total Anime Catalog, Authorized Episodes, Active Members, and Streams Served.
- **Anime Catalog CRUD**: Add new anime, edit titles, update posters/banners, adjust ratings, set status, and toggle homepage featured status.
- **Episode & Stream Manager**: Add episodes with episode number, duration, thumbnail, and authorized video stream URLs (MP4 / HLS).
- **Broadcast Announcements**: Publish instant banner notices to all website visitors.
- **User Account Administration**: Monitor user accounts and activate/deactivate access.
- **Backup & Restore**: Export full catalog and settings to a JSON backup file.

### 3. 📱 Native Android Application (Jetpack Compose)
- **Application Name**: `ANIME STUDIO`
- **Application ID**: `com.aistudio.animestudio.app`
- **Architecture**: MVVM + Android Jetpack Compose + Room Database + Coil.
- **Custom Adaptive Icon**: Neon kitsune fox mask and cinema play emblem on `#0D0E1A`.
- **Dual Mode**: High-performance Native Jetpack Compose UI + Embedded Web Portal with offline fallback.
- **Video Playback**: Full-screen video playback with fast forward, rewind, timeline slider, and orientation support.
- **Offline Reliability**: Graceful network monitoring with offline mode and local Room database caching.

### 4. 🔍 Google Search SEO Integration
- **Canonical URLs**: Defined for all primary pages.
- **Structured Data (JSON-LD)**: Schema.org `WebSite` and `Movie` tags configured for rich snippets.
- **Sitemap**: Production `sitemap.xml` listing core endpoints.
- **Robots.txt**: Permitting crawler indexing on public anime pages while strictly disallowing private administrative dashboards.
- **Google Search Console**: Verification tag slot provided in Admin Panel settings.

---

## 🚀 Live URLs

- **Development Web App**: `https://ais-dev-77qrgbdi7wjaonezz4lwh4-49134668239.asia-east1.run.app/`
- **Admin Dashboard**: `https://ais-dev-77qrgbdi7wjaonezz4lwh4-49134668239.asia-east1.run.app/admin.html`
- **XML Sitemap**: `https://ais-dev-77qrgbdi7wjaonezz4lwh4-49134668239.asia-east1.run.app/sitemap.xml`
- **Robots File**: `https://ais-dev-77qrgbdi7wjaonezz4lwh4-49134668239.asia-east1.run.app/robots.txt`

---

## 📦 Building the Android APK

### Option A: Via GitHub Actions (Automated CI/CD)
1. Push code to your GitHub repository.
2. The `.github/workflows/android-build.yml` workflow triggers automatically.
3. Once completed, navigate to **Actions** → Select latest run → Download **`AnimeStudio-Release-APK`** or **`AnimeStudio-Debug-APK`**.

### Option B: Local Command Line
```bash
# Compile and build debug APK
gradle :app:assembleDebug

# Compile and build release APK
gradle :app:assembleRelease
```
The resulting APK is generated in:
`app/build/outputs/apk/release/app-release.apk`
`app/build/outputs/apk/debug/app-debug.apk`

---

## 🔑 Administrative Passcode & Secrets

- **Admin Login Passcode**: `admin123`
- **Environment File**: `.env` (configured via AI Studio Secrets Panel).

---

## 📜 Authorized Media & Copyright Policy

All video content and sample streams included in ANIME STUDIO are authorized Creative Commons and Open Movie productions (including *Tears of Steel*, *Sintel*, *Cosmos Laundromat*, *Big Buck Bunny*, and *Elephants Dream*). No copyrighted or unauthorized third-party media is hosted.
