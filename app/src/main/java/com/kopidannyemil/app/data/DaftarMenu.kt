package com.kopidannyemil.app.data

import com.kopidannyemil.app.R
import com.kopidannyemil.app.model.Hidangan
import com.kopidannyemil.app.model.KategoriMenu

object DaftarMenu {
    val items: List<Hidangan> = listOf(
        Hidangan(
            id = 1,
            nama = "Kopi Susu Gula Aren",
            deskripsi = "Espresso, susu segar, dan gula aren",
            harga = 22000,
            iconResId = R.drawable.ic_kopi_aren,
            kategori = KategoriMenu.KOPI
        ),
        Hidangan(
            id = 2,
            nama = "Donat Cokelat",
            deskripsi = "Donat lembut dengan glaze cokelat pekat",
            harga = 12000,
            iconResId = R.drawable.ic_donat_cokelat,
            kategori = KategoriMenu.CAMILAN
        ),
        Hidangan(
            id = 3,
            nama = "Sourdough Loaf",
            deskripsi = "Roti fermentasi alami dengan kulit renyah",
            harga = 35000,
            iconResId = R.drawable.ic_sourdough_loaf,
            kategori = KategoriMenu.ROTI
        ),
        Hidangan(
            id = 4,
            nama = "Cappuccino",
            deskripsi = "Espresso seimbang dengan foam susu lembut",
            harga = 25000,
            iconResId = R.drawable.ic_cappuccino,
            kategori = KategoriMenu.KOPI
        ),
        Hidangan(
            id = 5,
            nama = "Matcha Latte",
            deskripsi = "Matcha murni Jepang dengan susu segar",
            harga = 27000,
            iconResId = R.drawable.ic_matcha_latte,
            kategori = KategoriMenu.NON_KOPI
        ),
        Hidangan(
            id = 6,
            nama = "Croissant Butter",
            deskripsi = "Pastry mentega Prancis dengan lapisan renyah",
            harga = 20000,
            iconResId = R.drawable.ic_croissant_butter,
            kategori = KategoriMenu.ROTI
        ),
        Hidangan(
            id = 7,
            nama = "Caramel Macchiato",
            deskripsi = "Espresso, susu vanila, dan saus karamel manis",
            harga = 28000,
            iconResId = R.drawable.ic_caramel_macchiato,
            kategori = KategoriMenu.KOPI
        )
    )

    fun cariBerdasarkanId(id: Int): Hidangan {
        return items.firstOrNull { it.id == id } ?: items[0]
    }
}
