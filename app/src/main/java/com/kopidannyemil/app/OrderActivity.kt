package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopidannyemil.app.databinding.ActivityOrderBinding
import com.kopidannyemil.app.penyimpanan.PenyimpananKeranjang
import com.kopidannyemil.app.penyimpanan.PenyimpananPengiriman
import com.kopidannyemil.app.penyimpanan.PenyimpananSesi
import com.kopidannyemil.app.util.formatRupiah

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var penyimpananSesi: PenyimpananSesi
    private lateinit var penyimpananKeranjang: PenyimpananKeranjang
    private lateinit var penyimpananPengiriman: PenyimpananPengiriman

    companion object {
        const val EXTRA_TOTAL_ITEM = "extra_total_item"
        const val EXTRA_TOTAL_HARGA = "extra_total_harga"
        private const val KUNCI_ALAMAT = "state_alamat"
        private const val KUNCI_PATOKAN = "state_patokan"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        penyimpananSesi = PenyimpananSesi(this)
        penyimpananKeranjang = PenyimpananKeranjang(this)
        penyimpananPengiriman = PenyimpananPengiriman(this)

        savedInstanceState?.let {
            binding.etAlamat.setText(it.getString(KUNCI_ALAMAT, ""))
            binding.etPatokan.setText(it.getString(KUNCI_PATOKAN, ""))
        }

        binding.etNamaLengkapOrder.doAfterTextChanged {
            binding.tilNamaLengkapOrder.error = null
        }

        binding.etAlamat.doAfterTextChanged {
            binding.tilAlamat.error = null
        }

        binding.btnLihatMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            }
            startActivity(intent)
            finish()
        }

        binding.btnOrderDanKirim.setOnClickListener {
            prosesOrder()
        }

        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_order
        perbaruiTampilan()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KUNCI_ALAMAT, binding.etAlamat.text.toString())
        outState.putString(KUNCI_PATOKAN, binding.etPatokan.text.toString())
    }

    private fun perbaruiTampilan() {
        if (penyimpananKeranjang.apakahKosong()) {
            binding.layoutKeranjangKosong.visibility = View.VISIBLE
            binding.layoutKontenOrder.visibility = View.GONE
        } else {
            binding.layoutKeranjangKosong.visibility = View.GONE
            binding.layoutKontenOrder.visibility = View.VISIBLE

            val items = penyimpananKeranjang.ambilSemuaItem()
            binding.rvItemPesanan.layoutManager = LinearLayoutManager(this)
            binding.rvItemPesanan.adapter = PesananItemAdapter(items)

            val totalHarga = penyimpananKeranjang.hitungTotalHarga()
            binding.tvTotalHargaCard.text = formatRupiah(totalHarga)

            if (binding.etNamaLengkapOrder.text.isNullOrEmpty()) {
                binding.etNamaLengkapOrder.setText(penyimpananSesi.ambilNamaLengkap())
            }

            if (binding.etAlamat.text.isNullOrEmpty()) {
                binding.etAlamat.setText(penyimpananPengiriman.ambilAlamat())
            }

            if (binding.etPatokan.text.isNullOrEmpty()) {
                binding.etPatokan.setText(penyimpananPengiriman.ambilPatokan())
            }
        }
    }

    private fun prosesOrder() {
        val nama = binding.etNamaLengkapOrder.text.toString().trim()
        val alamat = binding.etAlamat.text.toString().trim()
        val patokan = binding.etPatokan.text.toString().trim()

        var adaError = false

        if (nama.isEmpty()) {
            binding.tilNamaLengkapOrder.error = getString(R.string.error_wajib_diisi)
            adaError = true
        }

        if (alamat.isEmpty()) {
            binding.tilAlamat.error = getString(R.string.error_alamat_kosong)
            adaError = true
        }

        if (adaError) return

        val totalItem = penyimpananKeranjang.hitungTotalKuantitas()
        val totalHarga = penyimpananKeranjang.hitungTotalHarga()

        penyimpananSesi.perbaruiNamaLengkap(nama)
        penyimpananPengiriman.simpan(alamat, patokan)
        penyimpananKeranjang.kosongkan()

        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            putExtra(EXTRA_TOTAL_ITEM, totalItem)
            putExtra(EXTRA_TOTAL_HARGA, totalHarga)
        }
        startActivity(intent)
        finish()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_order
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    }
                    startActivity(intent)
                    true
                }
                R.id.nav_order -> true
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
