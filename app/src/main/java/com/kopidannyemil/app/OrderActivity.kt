package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.databinding.ActivityOrderBinding
import com.kopidannyemil.app.util.SessionManager

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupData()
        setupListeners()
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_order
        setupData()
    }

    private fun setupData() {
        val selectedItem = sessionManager.getSelectedMenu()
        binding.tvOrderItemName.text = selectedItem.name
        binding.tvOrderItemPrice.text = selectedItem.priceFormatted
        binding.ivOrderItemIcon.setImageResource(selectedItem.iconResId)

        val currentName = sessionManager.getUserName()
        if (binding.etNamaLengkapOrder.text.isNullOrEmpty()) {
            binding.etNamaLengkapOrder.setText(currentName)
        }

        if (binding.etAlamat.text.isNullOrEmpty()) {
            binding.etAlamat.setText(sessionManager.getDeliveryAddress())
        }

        if (binding.etPatokan.text.isNullOrEmpty()) {
            binding.etPatokan.setText(sessionManager.getDeliveryNote())
        }
    }

    private fun setupListeners() {
        binding.btnOrderDanKirim.setOnClickListener {
            val nama = binding.etNamaLengkapOrder.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()
            val patokan = binding.etPatokan.text.toString().trim()

            if (nama.isEmpty()) {
                Toast.makeText(this, "Nama lengkap wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (alamat.isEmpty()) {
                Toast.makeText(this, "Alamat pengiriman wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sessionManager.setUserName(nama)
            sessionManager.saveDeliveryInfo(alamat, patokan)

            val intent = Intent(this, ConfirmationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_order
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_order -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
