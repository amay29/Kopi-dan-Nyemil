package com.kopidannyemil.app.model

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val priceFormatted: String,
    val iconResId: Int,
    val category: String = "Makanan"
)
