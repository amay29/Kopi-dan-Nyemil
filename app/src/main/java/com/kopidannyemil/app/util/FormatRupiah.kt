package com.kopidannyemil.app.util

import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(nominal: Int): String {
    val localeId = Locale.forLanguageTag("id-ID")
    val formatter = NumberFormat.getNumberInstance(localeId)
    return "Rp ${formatter.format(nominal)}"
}
