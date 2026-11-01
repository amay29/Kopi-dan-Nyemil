package com.kopidannyemil.app.penyimpanan

import android.content.Context
import android.content.SharedPreferences

class PenyimpananPengiriman(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(NAMA_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val NAMA_PREF = "info_pengiriman_pref"
        private const val KUNCI_ALAMAT = "alamat_pengiriman"
        private const val KUNCI_PATOKAN = "patokan_pengiriman"
    }

    fun simpan(alamat: String, patokan: String) {
        prefs.edit()
            .putString(KUNCI_ALAMAT, alamat)
            .putString(KUNCI_PATOKAN, patokan)
            .apply()
    }

    fun ambilAlamat(): String {
        return prefs.getString(KUNCI_ALAMAT, "") ?: ""
    }

    fun ambilPatokan(): String {
        return prefs.getString(KUNCI_PATOKAN, "") ?: ""
    }
}
