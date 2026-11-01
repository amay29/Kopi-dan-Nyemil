package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopidannyemil.app.data.MenuData
import com.kopidannyemil.app.databinding.ActivityMainBinding
import com.kopidannyemil.app.util.SessionManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupRecyclerView()
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        binding.tvGreeting.text = "Halo ${sessionManager.getUserName()},"
        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun setupRecyclerView() {
        val adapter = MenuAdapter(MenuData.items) { selectedItem ->
            sessionManager.setSelectedMenuId(selectedItem.id)
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }
        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.adapter = adapter
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_order -> {
                    startActivity(Intent(this, OrderActivity::class.java))
                    false
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    false
                }
                else -> false
            }
        }
    }
}
