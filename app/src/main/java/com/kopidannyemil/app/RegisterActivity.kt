package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kopidannyemil.app.databinding.ActivityRegisterBinding
import com.kopidannyemil.app.model.Akun
import com.kopidannyemil.app.penyimpanan.PenyimpananAkun
import com.kopidannyemil.app.penyimpanan.PenyimpananSesi

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var penyimpananAkun: PenyimpananAkun
    private lateinit var penyimpananSesi: PenyimpananSesi

    companion object {
        private const val KUNCI_NAMA = "state_nama"
        private const val KUNCI_USER = "state_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        penyimpananAkun = PenyimpananAkun(this)
        penyimpananSesi = PenyimpananSesi(this)

        savedInstanceState?.let {
            binding.etNamaLengkap.setText(it.getString(KUNCI_NAMA, ""))
            binding.etUsername.setText(it.getString(KUNCI_USER, ""))
        }

        binding.etNamaLengkap.doAfterTextChanged {
            binding.tilNamaLengkap.error = null
        }

        binding.etUsername.doAfterTextChanged {
            binding.tilUsername.error = null
        }

        binding.etPassword.doAfterTextChanged {
            binding.tilPassword.error = null
        }

        binding.btnRegister.setOnClickListener {
            prosesRegistrasi()
        }

        binding.tvToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KUNCI_NAMA, binding.etNamaLengkap.text.toString())
        outState.putString(KUNCI_USER, binding.etUsername.text.toString())
    }

    private fun prosesRegistrasi() {
        val namaLengkap = binding.etNamaLengkap.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        var adaError = false

        if (namaLengkap.isEmpty()) {
            binding.tilNamaLengkap.error = getString(R.string.error_wajib_diisi)
            adaError = true
        }

        if (username.isEmpty()) {
            binding.tilUsername.error = getString(R.string.error_wajib_diisi)
            adaError = true
        } else if (!penyimpananAkun.apakahUsernameTersedia(username)) {
            binding.tilUsername.error = getString(R.string.error_username_terdaftar)
            adaError = true
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_wajib_diisi)
            adaError = true
        } else if (password.length < 6) {
            binding.tilPassword.error = getString(R.string.error_password_pendek)
            adaError = true
        }

        if (adaError) return

        val akunBaru = Akun(namaLengkap, username, password)
        val berhasil = penyimpananAkun.daftarkan(akunBaru)

        if (berhasil) {
            penyimpananSesi.simpanSesi(akunBaru)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
