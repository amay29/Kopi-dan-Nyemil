package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopidannyemil.app.data.DaftarMenu
import com.kopidannyemil.app.databinding.ActivityMainBinding
import com.kopidannyemil.app.model.ItemMenuUi
import com.kopidannyemil.app.model.KategoriMenu
import com.kopidannyemil.app.penyimpanan.PenyimpananKeranjang
import com.kopidannyemil.app.penyimpanan.PenyimpananSesi
import com.kopidannyemil.app.util.formatRupiah

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var penyimpananSesi: PenyimpananSesi
    private lateinit var penyimpananKeranjang: PenyimpananKeranjang
    private lateinit var adapter: HidanganAdapter

    private var kategoriTerpilih: KategoriMenu = KategoriMenu.SEMUA
    private var kataKunciPencarian: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        penyimpananSesi = PenyimpananSesi(this)
        penyimpananKeranjang = PenyimpananKeranjang(this)

        setupRecyclerView()
        setupPencarianDanFilter()
        setupBottomNavigation()
        setupKeranjangBar()
    }

    override fun onResume() {
        super.onResume()
        val nama = penyimpananSesi.ambilNamaLengkap().ifEmpty { getString(R.string.default_user_name) }
        binding.tvGreeting.text = "Halo $nama,"
        binding.bottomNavigation.selectedItemId = R.id.nav_home
        perbaruiDaftar()
        perbaruiTampilanKeranjang()
    }

    private fun setupRecyclerView() {
        adapter = HidanganAdapter(
            onTambah = { hidangan ->
                penyimpananKeranjang.tambah(hidangan)
                perbaruiDaftar()
                perbaruiTampilanKeranjang()
            },
            onKurang = { hidangan ->
                penyimpananKeranjang.kurang(hidangan.id)
                perbaruiDaftar()
                perbaruiTampilanKeranjang()
            }
        )
        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.adapter = adapter
    }

    private fun setupPencarianDanFilter() {
        binding.etPencarian.doAfterTextChanged { text ->
            kataKunciPencarian = text?.toString()?.trim() ?: ""
            perbaruiDaftar()
        }

        binding.chipGroupKategori.setOnCheckedStateChangeListener { _, checkedIds ->
            kategoriTerpilih = when {
                checkedIds.contains(R.id.chipKopi) -> KategoriMenu.KOPI
                checkedIds.contains(R.id.chipNonKopi) -> KategoriMenu.NON_KOPI
                checkedIds.contains(R.id.chipCamilan) -> KategoriMenu.CAMILAN
                checkedIds.contains(R.id.chipRoti) -> KategoriMenu.ROTI
                else -> KategoriMenu.SEMUA
            }
            perbaruiDaftar()
        }
    }

    private fun perbaruiDaftar() {
        val semuaMenu = DaftarMenu.items
        val terfilter = semuaMenu.filter { hidangan ->
            val cocokKategori = kategoriTerpilih == KategoriMenu.SEMUA || hidangan.kategori == kategoriTerpilih
            val cocokKataKunci = kataKunciPencarian.isEmpty() ||
                    hidangan.nama.contains(kataKunciPencarian, ignoreCase = true) ||
                    hidangan.deskripsi.contains(kataKunciPencarian, ignoreCase = true)
            cocokKategori && cocokKataKunci
        }

        val listUi = terfilter.map { hidangan ->
            val kuantitas = penyimpananKeranjang.ambilKuantitas(hidangan.id)
            ItemMenuUi(hidangan, kuantitas)
        }

        adapter.submitList(listUi)
    }

    private fun setupKeranjangBar() {
        binding.layoutKeranjangBar.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            }
            startActivity(intent)
        }
    }

    private fun perbaruiTampilanKeranjang() {
        if (penyimpananKeranjang.apakahKosong()) {
            binding.layoutKeranjangBar.visibility = View.GONE
        } else {
            val totalItem = penyimpananKeranjang.hitungTotalKuantitas()
            val totalHarga = penyimpananKeranjang.hitungTotalHarga()

            binding.layoutKeranjangBar.visibility = View.VISIBLE
            binding.tvCartItemCount.text = "$totalItem item"
            binding.tvCartTotalPrice.text = formatRupiah(totalHarga)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    }
                    startActivity(intent)
                    false
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    }
                    startActivity(intent)
                    false
                }
                else -> false
            }
        }
    }
}
