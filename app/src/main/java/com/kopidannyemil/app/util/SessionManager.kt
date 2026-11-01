package com.kopidannyemil.app.util

import android.content.Context
import android.content.SharedPreferences
import com.kopidannyemil.app.data.MenuData
import com.kopidannyemil.app.model.MenuItem

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "kopi_dan_nyemil_prefs"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USERNAME = "username"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_SELECTED_MENU_ID = "selected_menu_id"
        private const val KEY_DELIVERY_ADDRESS = "delivery_address"
        private const val KEY_DELIVERY_NOTE = "delivery_note"
        private const val DEFAULT_NAME = "Amay"
    }

    fun saveUser(name: String, username: String) {
        val editor = prefs.edit()
        editor.putString(KEY_USER_NAME, if (name.isNotBlank()) name else DEFAULT_NAME)
        editor.putString(KEY_USERNAME, username)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
    }

    fun setUserName(name: String) {
        prefs.edit().putString(KEY_USER_NAME, if (name.isNotBlank()) name else DEFAULT_NAME).apply()
    }

    fun getUsername(): String {
        return prefs.getString(KEY_USERNAME, "") ?: ""
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setSelectedMenuId(id: Int) {
        prefs.edit().putInt(KEY_SELECTED_MENU_ID, id).apply()
    }

    fun getSelectedMenu(): MenuItem {
        val id = prefs.getInt(KEY_SELECTED_MENU_ID, 1)
        return MenuData.getById(id)
    }

    fun saveDeliveryInfo(address: String, note: String) {
        val editor = prefs.edit()
        editor.putString(KEY_DELIVERY_ADDRESS, address)
        editor.putString(KEY_DELIVERY_NOTE, note)
        editor.apply()
    }

    fun getDeliveryAddress(): String {
        return prefs.getString(KEY_DELIVERY_ADDRESS, "") ?: ""
    }

    fun getDeliveryNote(): String {
        return prefs.getString(KEY_DELIVERY_NOTE, "") ?: ""
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
