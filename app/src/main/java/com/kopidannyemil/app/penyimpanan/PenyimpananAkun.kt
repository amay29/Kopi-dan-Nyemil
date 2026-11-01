package com.kopidannyemil.app.penyimpanan

import android.content.Context
import android.content.SharedPreferences
import com.kopidannyemil.app.model.Akun
import org.json.JSONArray
import org.json.JSONObject

class PenyimpananAkun(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(NAMA_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val NAMA_PREF = "daftar_akun_pref"
        private const val KUNCI_AKUN = "data_akun"
        private const val KUNCI_DEMO_DIBUAT = "demo_dibuat"
    }

    init {
        inisialisasiAkunDemo()
    }

    private fun inisialisasiAkunDemo() {
        if (!prefs.getBoolean(KUNCI_DEMO_DIBUAT, false)) {
            val akunDemo = Akun(
                namaLengkap = "Pengguna Demo",
                username = "demo",
                kataSandi = "demo123"
            )
            val daftar = ambilSemuaAkun().toMutableList()
            if (daftar.none { it.username.equals(akunDemo.username, ignoreCase = true) }) {
                daftar.add(akunDemo)
                simpanDaftarAkun(daftar)
            }
            prefs.edit().putBoolean(KUNCI_DEMO_DIBUAT, true).apply()
        }
    }

    private fun ambilSemuaAkun(): List<Akun> {
        val jsonString = prefs.getString(KUNCI_AKUN, null) ?: return emptyList()
        val list = mutableListOf<Akun>()
        val array = JSONArray(jsonString)
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(
                Akun(
                    namaLengkap = obj.getString("namaLengkap"),
                    username = obj.getString("username"),
                    kataSandi = obj.getString("kataSandi")
                )
            )
        }
        return list
    }

    private fun simpanDaftarAkun(daftar: List<Akun>) {
        val array = JSONArray()
        for (akun in daftar) {
            val obj = JSONObject().apply {
                put("namaLengkap", akun.namaLengkap)
                put("username", akun.username)
                put("kataSandi", akun.kataSandi)
            }
            array.put(obj)
        }
        prefs.edit().putString(KUNCI_AKUN, array.toString()).apply()
    }

    fun apakahUsernameTersedia(username: String): Boolean {
        val daftar = ambilSemuaAkun()
        return daftar.none { it.username.equals(username.trim(), ignoreCase = true) }
    }

    fun daftarkan(akun: Akun): Boolean {
        if (!apakahUsernameTersedia(akun.username)) {
            return false
        }
        val daftar = ambilSemuaAkun().toMutableList()
        daftar.add(akun)
        simpanDaftarAkun(daftar)
        return true
    }

    fun validasiMasuk(username: String, kataSandi: String): Akun? {
        val daftar = ambilSemuaAkun()
        return daftar.firstOrNull {
            it.username.equals(username.trim(), ignoreCase = true) && it.kataSandi == kataSandi
        }
    }

    fun perbaruiNamaPengguna(username: String, namaBaru: String) {
        val daftar = ambilSemuaAkun().toMutableList()
        val index = daftar.indexOfFirst { it.username.equals(username, ignoreCase = true) }
        if (index != -1) {
            val lama = daftar[index]
            daftar[index] = lama.copy(namaLengkap = namaBaru)
            simpanDaftarAkun(daftar)
        }
    }
}
