package com.kopidannyemil.app.model

data class Hidangan(
    val id: Int,
    val nama: String,
    val deskripsi: String,
    val harga: Int,
    val iconResId: Int,
    val kategori: KategoriMenu
)
