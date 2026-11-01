package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.databinding.ActivityConfirmationBinding
import com.kopidannyemil.app.util.SessionManager

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupData()
        setupListeners()
    }

    private fun setupData() {
        val userName = sessionManager.getUserName()
        binding.tvGreetingConfirmation.text = "Halo $userName,"

        val selectedItem = sessionManager.getSelectedMenu()
        binding.tvSummaryItemName.text = selectedItem.name
        binding.tvSummaryItemPrice.text = selectedItem.priceFormatted

        val address = sessionManager.getDeliveryAddress()
        binding.tvSummaryAddress.text = if (address.isNotEmpty()) "Alamat: $address" else "Alamat: Alamat Pengiriman Utama"
    }

    private fun setupListeners() {
        binding.btnOke.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
