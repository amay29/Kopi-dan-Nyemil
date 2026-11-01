package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.databinding.ActivitySplashBinding
import com.kopidannyemil.app.penyimpanan.PenyimpananAkun
import com.kopidannyemil.app.penyimpanan.PenyimpananSesi

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PenyimpananAkun(this)
        val sesi = PenyimpananSesi(this)

        if (sesi.apakahSudahMasuk()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.centerLayout.alpha = 0f
        binding.centerLayout.translationY = 40f
        binding.centerLayout.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .start()

        binding.btnStartNow.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}
