package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.databinding.ActivityConfirmationBinding
import com.kopidannyemil.app.penyimpanan.PenyimpananPengiriman
import com.kopidannyemil.app.penyimpanan.PenyimpananSesi
import com.kopidannyemil.app.util.formatRupiah

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationBinding
    private lateinit var penyimpananSesi: PenyimpananSesi
    private lateinit var penyimpananPengiriman: PenyimpananPengiriman

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        penyimpananSesi = PenyimpananSesi(this)
        penyimpananPengiriman = PenyimpananPengiriman(this)

        setupData()
        setupAnimasi()
        setupListeners()
    }

    private fun setupData() {
        val userName = penyimpananSesi.ambilNamaLengkap().ifEmpty { getString(R.string.default_user_name) }
        binding.tvGreetingConfirmation.text = "Halo $userName,"

        val totalItem = intent.getIntExtra(OrderActivity.EXTRA_TOTAL_ITEM, 1)
        val totalHarga = intent.getIntExtra(OrderActivity.EXTRA_TOTAL_HARGA, 0)
        val alamat = penyimpananPengiriman.ambilAlamat()

        binding.tvSummaryTotalItem.text = "$totalItem item"
        binding.tvSummaryTotalPrice.text = formatRupiah(totalHarga)
        binding.tvSummaryAddress.text = if (alamat.isNotEmpty()) "Alamat: $alamat" else "Alamat: Alamat Pengiriman Utama"
    }

    private fun setupAnimasi() {
        binding.frameOkBadge.scaleX = 0.3f
        binding.frameOkBadge.scaleY = 0.3f
        binding.frameOkBadge.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setInterpolator(OvershootInterpolator())
            .start()
    }

    private fun setupListeners() {
        binding.btnOke.setOnClickListener {
            kembaliKeHome()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                kembaliKeHome()
            }
        })
    }

    private fun kembaliKeHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        finish()
    }
}
