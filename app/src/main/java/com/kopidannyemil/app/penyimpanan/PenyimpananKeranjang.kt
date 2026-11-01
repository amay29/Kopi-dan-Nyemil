package com.kopidannyemil.app.penyimpanan

import android.content.Context
import android.content.SharedPreferences
import com.kopidannyemil.app.data.DaftarMenu
import com.kopidannyemil.app.model.Hidangan
import com.kopidannyemil.app.model.ItemPesanan
import org.json.JSONArray
import org.json.JSONObject

class PenyimpananKeranjang(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(NAMA_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val NAMA_PREF = "keranjang_pesanan_pref"
        private const val KUNCI_ITEM = "item_keranjang"
    }

    fun ambilSemuaItem(): List<ItemPesanan> {
        val jsonString = prefs.getString(KUNCI_ITEM, null) ?: return emptyList()
        val list = mutableListOf<ItemPesanan>()
        val array = JSONArray(jsonString)
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val id = obj.getInt("id")
            val kuantitas = obj.getInt("kuantitas")
            val hidangan = DaftarMenu.cariBerdasarkanId(id)
            list.add(ItemPesanan(hidangan, kuantitas))
        }
        return list
    }

    private fun simpanSemuaItem(items: List<ItemPesanan>) {
        val array = JSONArray()
        for (item in items) {
            if (item.kuantitas > 0) {
                val obj = JSONObject().apply {
                    put("id", item.hidangan.id)
                    put("kuantitas", item.kuantitas)
                }
                array.put(obj)
            }
        }
        prefs.edit().putString(KUNCI_ITEM, array.toString()).apply()
    }

    fun ambilKuantitas(hidanganId: Int): Int {
        val items = ambilSemuaItem()
        return items.firstOrNull { it.hidangan.id == hidanganId }?.kuantitas ?: 0
    }

    fun tambah(hidangan: Hidangan) {
        val items = ambilSemuaItem().toMutableList()
        val index = items.indexOfFirst { it.hidangan.id == hidangan.id }
        if (index != -1) {
            items[index].kuantitas += 1
        } else {
            items.add(ItemPesanan(hidangan, 1))
        }
        simpanSemuaItem(items)
    }

    fun kurang(hidanganId: Int) {
        val items = ambilSemuaItem().toMutableList()
        val index = items.indexOfFirst { it.hidangan.id == hidanganId }
        if (index != -1) {
            if (items[index].kuantitas > 1) {
                items[index].kuantitas -= 1
            } else {
                items.removeAt(index)
            }
            simpanSemuaItem(items)
        }
    }

    fun hitungTotalKuantitas(): Int {
        return ambilSemuaItem().sumOf { it.kuantitas }
    }

    fun hitungTotalHarga(): Int {
        return ambilSemuaItem().sumOf { it.subtotal }
    }

    fun apakahKosong(): Boolean {
        return ambilSemuaItem().isEmpty()
    }

    fun kosongkan() {
        prefs.edit().remove(KUNCI_ITEM).apply()
    }
}
