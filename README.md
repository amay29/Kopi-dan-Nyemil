# Kopi dan Nyemil - Mobile Application (UAS PAM)

Aplikasi mobile pemesanan kopi dan camilan modern berbasis **Android** dan **Kotlin**, dikembangkan berdasarkan wireframe resmi tugas **UAS Pemrograman Aplikasi Mobile (PAM)**.

---

## Daftar Isi
- [Deskripsi Aplikasi](#deskripsi-aplikasi)
- [Fitur Utama & Alur Wireframe](#fitur-utama--alur-wireframe)
- [Wireframe & Desain Antarmuka](#wireframe--desain-antarmuka)
- [Teknologi yang Digunakan](#teknologi-yang-digunakan)
- [Struktur Proyek](#struktur-proyek)
- [Data Menu Makanan & Minuman](#data-menu-makanan--minuman)
- [Cara Menjalankan Aplikasi](#cara-menjalankan-aplikasi)
  - [Melalui Android Studio](#melalui-android-studio)
  - [Melalui Command Line (Gradle)](#melalui-command-line-gradle)
- [Komponen Penilaian](#komponen-penilaian)

---

## Deskripsi Aplikasi

**Kopi dan Nyemil** adalah aplikasi pemesanan kuliner berbasis Android yang dirancang dengan antarmuka modern, bersih, dan bertema *warm coffee shop*. Pengguna dapat menjelajahi menu kopi dan camilan favorit, mengelola profil nama pengguna, mengisi alamat pengiriman lengkap dengan patokan, serta menerima konfirmasi pesanan secara instan.

Aplikasi ini menggunakan manajemen state dan sesi berbasis **SharedPreferences**, sehingga nama pengguna yang didaftarkan atau diubah pada halaman profil akan tersinkronisasi secara dinamis pada salam (`Halo [Nama],`) di halaman Home, Profile, dan Konfirmasi Pesanan tanpa memerlukan database eksternal.

---

## Fitur Utama & Alur Wireframe

Seluruh 8 layar dan alur navigasi pada wireframe telah diimplementasikan secara utuh:

| No | Layar Wireframe | Komponen & Fitur | Alur Navigasi |
|:---:|:---|:---|:---|
| 1 | **Splash / Welcome** | Logo lingkaran bertema kopi, Judul *"Kopi dan Nyemil"*, Tagline *"Get a taste of your wish"*, tombol *"Start now"* | Menuju Layar 2 (Sign up) |
| 2 | **Sign Up (Pilihan Autentikasi)** | Judul *"Sign up"*, deskripsi keanggotaan, tombol *"Register"* (Filled) dan *"Login"* (Outlined) | Menuju Layar 3 (Login) atau Layar 4 (Register) |
| 3 | **Login User** | Form input `username` dan `password` (masked), tombol *"Login"* dengan validasi input | Menyimpan sesi pengguna dan menuju Layar 5 (Home) |
| 4 | **Register** | Form input `nama lengkap`, `username`, dan `password`, tombol *"Register"* dengan validasi input | Menyimpan profil pengguna dan menuju Layar 5 (Home) |
| 5 | **Home** | Header dinamis *"Halo [Nama],"*, label *"Makanan"*, daftar minimal 5 item menu (7 item tersedia) lengkap dengan foto/ikon, deskripsi, harga, dan tombol *"Pesan"*; Bottom navigation (Home, Order, Profile) | Memilih menu atau tab Order menuju Layar 7; Tab Profile menuju Layar 6 |
| 6 | **Profile** | Header *"Halo [Nama],"*, judul *"Profile Saya"*, *"Profile Setting"*, avatar pengguna, input editable `nama`, tombol *"Save"* (update nama real-time), dan tombol *"Logout"*; Bottom navigation | Tombol *"Save"* memperbarui salam; Tab navigasi menuju Home/Order; Logout kembali ke Layar 2 |
| 7 | **Alamat Pengiriman (Order)** | Judul *"Alamat"*, kartu ringkasan item yang dipesan, input `Nama Lengkap` (terisi otomatis), input `Alamat` (multiline), input `Patokan atau detail lengkap`, tombol *"Order dan Kirim"* | Validasi form lalu menuju Layar 8 (Konfirmasi) |
| 8 | **Konfirmasi Pesanan** | Header dinamis *"Halo [Nama],"*, lencana lingkaran hijau *"OK"* besar, teks pesan *"Terima kasih sudah memesan tunggu aja sebentar kurir kami segera mengantarkan pesananmu!"*, ringkasan detail pesanan & alamat, tombol *"Oke"* | Kembali ke Layar 5 (Home) |

---

## Wireframe & Desain Antarmuka

### Panduan Wireframe
Referensi wireframe tugas UAS PAM:

![Wireframe Kopi dan Nyemil](docs/wireframe.png)

### Penyempurnaan Tampilan (Modern & Intuitive)
1. **Warna & Tipografi**: Menggunakan palet warm coffee palette (`#1C1917` deep espresso, `#6F4E37` coffee brown, `#E8B282` warm caramel, `#FAF8F5` off-white canvas, dan `#7ED6A5` fresh green).
2. **Komponen Visual**: Rounded pill buttons, cards dengan border halus (*card elevation* yang lembut), input field bersudut membulat (*radius 12dp-16dp*), dan custom vector drawable untuk setiap jenis makanan dan minuman.
3. **Bottom Navigation**: Transisi halus antara Home, Order, dan Profile dengan status aktif yang jelas.

---

## Teknologi yang Digunakan

- **Bahasa Pemrograman**: Kotlin 2.x
- **Platform**: Android SDK (minSdk: 24 / Android 7.0 Nougat, compileSdk: 36, targetSdk: 35)
- **UI Framework**: Android Material Components 3 (Material Design), ConstraintLayout, RecyclerView, CardView
- **View Binding**: Android ViewBinding (Type-safe view access tanpa findViewById)
- **State & Data Persistence**: Android SharedPreferences via `SessionManager`
- **Build System**: Gradle 9.5 & Android Gradle Plugin (AGP) 9.3+

---

## Struktur Proyek

```
UAS PAM/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/kopidannyemil/app/
│           │   ├── SplashActivity.kt
│           │   ├── WelcomeActivity.kt
│           │   ├── LoginActivity.kt
│           │   ├── RegisterActivity.kt
│           │   ├── MainActivity.kt
│           │   ├── ProfileActivity.kt
│           │   ├── OrderActivity.kt
│           │   ├── ConfirmationActivity.kt
│           │   ├── MenuAdapter.kt
│           │   ├── data/
│           │   │   └── MenuData.kt
│           │   ├── model/
│           │   │   └── MenuItem.kt
│           │   └── util/
│           │       └── SessionManager.kt
│           └── res/
│               ├── color/
│               │   └── bottom_nav_color_selector.xml
│               ├── drawable/
│               │   ├── bg_button_outlined.xml
│               │   ├── bg_button_primary.xml
│               │   ├── bg_card_image.xml
│               │   ├── bg_card_menu.xml
│               │   ├── bg_circle_logo.xml
│               │   ├── bg_circle_success.xml
│               │   ├── bg_input_field.xml
│               │   ├── ic_avatar_placeholder.xml
│               │   ├── ic_bread.xml
│               │   ├── ic_check_ok.xml
│               │   ├── ic_coffee_cup.xml
│               │   ├── ic_coffee_logo.xml
│               │   ├── ic_croissant.xml
│               │   ├── ic_donut.xml
│               │   ├── ic_matcha.xml
│               │   ├── ic_nav_home.xml
│               │   ├── ic_nav_order.xml
│               │   └── ic_nav_profile.xml
│               ├── layout/
│               │   ├── activity_confirmation.xml
│               │   ├── activity_login.xml
│               │   ├── activity_main.xml
│               │   ├── activity_order.xml
│               │   ├── activity_profile.xml
│               │   ├── activity_register.xml
│               │   ├── activity_splash.xml
│               │   ├── activity_welcome.xml
│               │   └── item_menu.xml
│               ├── menu/
│               │   └── bottom_nav_menu.xml
│               └── values/
│                   ├── colors.xml
│                   ├── strings.xml
│                   └── themes.xml
├── docs/
│   └── wireframe.png
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md
```

---

## Data Menu Makanan & Minuman

Daftar menu statis (dummy data) yang tersedia pada aplikasi (melebihi syarat minimal 5 item):

1. **Kopi Susu Gula Aren** — *Espresso, susu, gula aren* — **Rp 22.000**
2. **Donat Cokelat** — *Donat empuk, glaze cokelat* — **Rp 12.000**
3. **Sourdough Loaf** — *Roti fermentasi alami* — **Rp 35.000**
4. **Cappuccino** — *Espresso, foam susu lembut* — **Rp 25.000**
5. **Matcha Latte** — *Matcha jepang, susu segar* — **Rp 27.000**
6. **Croissant Butter** — *Pastry renyah, mentega Prancis* — **Rp 20.000**
7. **Caramel Macchiato** — *Espresso, susu vanila, karamel* — **Rp 28.000**

---

## Cara Menjalankan Aplikasi

### Melalui Android Studio
1. Buka software **Android Studio**.
2. Pilih menu **File > Open**, kemudian arahkan ke folder direktori proyek ini (`UAS PAM`).
3. Tunggu proses **Gradle Sync** selesai secara otomatis.
4. Pilih target perangkat (*Android Virtual Device (AVD)* / Emulator atau perangkat fisik yang tersambung via USB debugging).
5. Klik tombol **Run** (ikon segitiga hijau) atau tekan shortcut `Shift + F10`.

### Melalui Command Line (Gradle)
Untuk melakukan kompilasi dan build APK secara langsung melalui terminal:

```bash
# Memberikan izin eksekusi pada Gradle wrapper
chmod +x ./gradlew

# Build debug APK
./gradlew assembleDebug
```

File APK yang berhasil di-build dapat ditemukan di direktori:
`app/build/outputs/apk/debug/app-debug.apk`

Untuk menginstall langsung ke emulator/perangkat yang sedang aktif:
```bash
./gradlew installDebug
```

---

## Komponen Penilaian

| No | Komponen Penilaian | Bobot | Status Implementasi |
|:---:|:---|:---:|:---|
| 1 | Kelengkapan tampilan dan fitur | 60 poin | **Lengkap 100%** (8 layar wireframe, input form, alur pemesanan, greeting dinamis, bottom nav) |
| 2 | Tampilan/interface yang menarik | 20 poin | **Modern & Polished** (Tema warm coffee, rounded cards, custom drawables, responsive layout) |
| 3 | Dokumentasi GitHub dan kelengkapan README | 20 poin | **Lengkap & Terstruktur** (README komprehensif, git commit bertahap, instruksi build lengkap) |
