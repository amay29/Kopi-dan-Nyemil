package com.kopidannyemil.app.model

data class ItemPesanan(
    val hidangan: Hidangan,
    var kuantitas: Int
) {
    val subtotal: Int
        get() = hidangan.harga * kuantitas
}
