package com.kopidannyemil.app.data

import com.kopidannyemil.app.R
import com.kopidannyemil.app.model.MenuItem

object MenuData {
    val items: List<MenuItem> = listOf(
        MenuItem(
            id = 1,
            name = "Kopi Susu Gula Aren",
            description = "Espresso, susu, gula aren",
            price = 22000,
            priceFormatted = "Rp 22.000",
            iconResId = R.drawable.ic_coffee_cup,
            category = "Kopi"
        ),
        MenuItem(
            id = 2,
            name = "Donat Cokelat",
            description = "Donat empuk, glaze cokelat",
            price = 12000,
            priceFormatted = "Rp 12.000",
            iconResId = R.drawable.ic_donut,
            category = "Camilan"
        ),
        MenuItem(
            id = 3,
            name = "Sourdough Loaf",
            description = "Roti fermentasi alami",
            price = 35000,
            priceFormatted = "Rp 35.000",
            iconResId = R.drawable.ic_bread,
            category = "Roti"
        ),
        MenuItem(
            id = 4,
            name = "Cappuccino",
            description = "Espresso, foam susu lembut",
            price = 25000,
            priceFormatted = "Rp 25.000",
            iconResId = R.drawable.ic_coffee_cup,
            category = "Kopi"
        ),
        MenuItem(
            id = 5,
            name = "Matcha Latte",
            description = "Matcha jepang, susu segar",
            price = 27000,
            priceFormatted = "Rp 27.000",
            iconResId = R.drawable.ic_matcha,
            category = "Non-Kopi"
        ),
        MenuItem(
            id = 6,
            name = "Croissant Butter",
            description = "Pastry renyah, mentega Prancis",
            price = 20000,
            priceFormatted = "Rp 20.000",
            iconResId = R.drawable.ic_croissant,
            category = "Pastry"
        ),
        MenuItem(
            id = 7,
            name = "Caramel Macchiato",
            description = "Espresso, susu vanila, karamel",
            price = 28000,
            priceFormatted = "Rp 28.000",
            iconResId = R.drawable.ic_coffee_cup,
            category = "Kopi"
        )
    )

    fun getById(id: Int): MenuItem {
        return items.firstOrNull { it.id == id } ?: items[0]
    }
}
