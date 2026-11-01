package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kopidannyemil.app.databinding.ActivityProfileBinding
import com.kopidannyemil.app.penyimpanan.PenyimpananAkun
import com.kopidannyemil.app.penyimpanan.PenyimpananSesi

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var penyimpananSesi: PenyimpananSesi
    private lateinit var penyimpananAkun: PenyimpananAkun

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        penyimpananSesi = PenyimpananSesi(this)
        penyimpananAkun = PenyimpananAkun(this)

        setupData()
        setupListeners()
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_profile
        setupData()
    }

    private fun setupData() {
        val namaLengkap = penyimpananSesi.ambilNamaLengkap().ifEmpty { getString(R.string.default_user_name) }
        val username = penyimpananSesi.ambilUsername()

        binding.tvGreetingProfile.text = "Halo $namaLengkap,"
        binding.tvNamaLengkapDisplay.text = namaLengkap
        binding.tvUsernameDisplay.text = if (username.isNotEmpty()) "@$username" else "@pengguna"
        binding.etNama.setText(namaLengkap)
        binding.tvAvatarInitials.text = buatInisialNama(namaLengkap)
    }

    private fun buatInisialNama(nama: String): String {
        val bagian = nama.trim().split(" ").filter { it.isNotEmpty() }
        return when {
            bagian.isEmpty() -> "U"
            bagian.size == 1 -> bagian[0].take(1).uppercase()
            else -> (bagian[0].take(1) + bagian[1].take(1)).uppercase()
        }
    }

    private fun setupListeners() {
        binding.etNama.doAfterTextChanged {
            binding.tilNama.error = null
        }

        binding.btnSave.setOnClickListener {
            val namaBaru = binding.etNama.text.toString().trim()
            if (namaBaru.isEmpty()) {
                binding.tilNama.error = getString(R.string.error_wajib_diisi)
                return@setOnClickListener
            }

            penyimpananSesi.perbaruiNamaLengkap(namaBaru)
            penyimpananAkun.perbaruiNamaPengguna(penyimpananSesi.ambilUsername(), namaBaru)

            setupData()
            Snackbar.make(binding.root, getString(R.string.profil_tersimpan), Snackbar.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            tampilkanDialogLogout()
        }
    }

    private fun tampilkanDialogLogout() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.konfirmasi_logout_judul))
            .setMessage(getString(R.string.konfirmasi_logout_pesan))
            .setPositiveButton(getString(R.string.ya_keluar)) { _, _ ->
                penyimpananSesi.keluar()
                val intent = Intent(this, WelcomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
            .setNegativeButton(getString(R.string.batal), null)
            .show()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_profile
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    }
                    startActivity(intent)
                    true
                }
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    }
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}
