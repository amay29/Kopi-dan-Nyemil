package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.databinding.ActivitySplashBinding
import com.kopidannyemil.app.penyimpanan.PenyimpananAkun

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PenyimpananAkun(this)

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
