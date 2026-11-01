package com.kopidannyemil.app.penyimpanan

import android.content.Context
import android.content.SharedPreferences
import com.kopidannyemil.app.model.Akun

class PenyimpananSesi(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(NAMA_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val NAMA_PREF = "sesi_pengguna_pref"
        private const val KUNCI_SUDAH_MASUK = "sudah_masuk"
        private const val KUNCI_NAMA_LENGKAP = "nama_lengkap"
        private const val KUNCI_USERNAME = "username"
    }

    fun simpanSesi(akun: Akun) {
        prefs.edit()
            .putBoolean(KUNCI_SUDAH_MASUK, true)
            .putString(KUNCI_NAMA_LENGKAP, akun.namaLengkap)
            .putString(KUNCI_USERNAME, akun.username)
            .apply()
    }

    fun apakahSudahMasuk(): Boolean {
        return prefs.getBoolean(KUNCI_SUDAH_MASUK, false)
    }

    fun ambilNamaLengkap(): String {
        return prefs.getString(KUNCI_NAMA_LENGKAP, "") ?: ""
    }

    fun perbaruiNamaLengkap(namaBaru: String) {
        prefs.edit().putString(KUNCI_NAMA_LENGKAP, namaBaru).apply()
    }

    fun ambilUsername(): String {
        return prefs.getString(KUNCI_USERNAME, "") ?: ""
    }

    fun keluar() {
        prefs.edit()
            .putBoolean(KUNCI_SUDAH_MASUK, false)
            .putString(KUNCI_NAMA_LENGKAP, "")
            .putString(KUNCI_USERNAME, "")
            .apply()
    }
}
