package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kopidannyemil.app.databinding.ActivityLoginBinding
import com.kopidannyemil.app.penyimpanan.PenyimpananAkun
import com.kopidannyemil.app.penyimpanan.PenyimpananSesi

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var penyimpananAkun: PenyimpananAkun
    private lateinit var penyimpananSesi: PenyimpananSesi

    companion object {
        private const val KUNCI_INPUT_USERNAME = "input_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        penyimpananAkun = PenyimpananAkun(this)
        penyimpananSesi = PenyimpananSesi(this)

        savedInstanceState?.let {
            binding.etUsername.setText(it.getString(KUNCI_INPUT_USERNAME, ""))
        }

        binding.etUsername.doAfterTextChanged {
            binding.tilUsername.error = null
        }

        binding.etPassword.doAfterTextChanged {
            binding.tilPassword.error = null
        }

        binding.btnLogin.setOnClickListener {
            prosesLogin()
        }

        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KUNCI_INPUT_USERNAME, binding.etUsername.text.toString())
    }

    private fun prosesLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        var adaError = false

        if (username.isEmpty()) {
            binding.tilUsername.error = getString(R.string.error_wajib_diisi)
            adaError = true
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_wajib_diisi)
            adaError = true
        }

        if (adaError) return

        val akun = penyimpananAkun.validasiMasuk(username, password)
        if (akun == null) {
            binding.tilPassword.error = getString(R.string.error_login_salah)
            return
        }

        penyimpananSesi.simpanSesi(akun)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
