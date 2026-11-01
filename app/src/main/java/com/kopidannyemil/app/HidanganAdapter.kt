package com.kopidannyemil.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kopidannyemil.app.R
import com.kopidannyemil.app.databinding.ItemMenuBinding
import com.kopidannyemil.app.model.Hidangan
import com.kopidannyemil.app.model.ItemMenuUi
import com.kopidannyemil.app.model.KategoriMenu
import com.kopidannyemil.app.util.formatRupiah

class HidanganAdapter(
    private val onTambah: (Hidangan) -> Unit,
    private val onKurang: (Hidangan) -> Unit
) : ListAdapter<ItemMenuUi, HidanganAdapter.HidanganViewHolder>(HidanganDiffCallback) {

    inner class HidanganViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUi: ItemMenuUi) {
            val hidangan = itemUi.hidangan
            binding.tvMenuName.text = hidangan.nama
            binding.tvMenuDescription.text = hidangan.deskripsi
            binding.tvMenuCategory.text = hidangan.kategori.label
            binding.tvMenuPrice.text = formatRupiah(hidangan.harga)
            binding.ivMenuIcon.setImageResource(hidangan.iconResId)

            val warnaLatar = when (hidangan.kategori) {
                KategoriMenu.KOPI -> R.color.category_kopi_bg
                KategoriMenu.NON_KOPI -> R.color.category_non_kopi_bg
                KategoriMenu.CAMILAN -> R.color.category_camilan_bg
                KategoriMenu.ROTI -> R.color.category_roti_bg
                KategoriMenu.SEMUA -> R.color.accent_warm_light
            }
            binding.ivIconContainer.setBackgroundColor(
                ContextCompat.getColor(binding.root.context, warnaLatar)
            )

            if (itemUi.kuantitas > 0) {
                binding.btnPesan.visibility = View.GONE
                binding.layoutKuantitas.visibility = View.VISIBLE
                binding.tvKuantitas.text = itemUi.kuantitas.toString()
            } else {
                binding.btnPesan.visibility = View.VISIBLE
                binding.layoutKuantitas.visibility = View.GONE
            }

            binding.btnPesan.setOnClickListener {
                onTambah(hidangan)
            }

            binding.btnTambah.setOnClickListener {
                onTambah(hidangan)
            }

            binding.btnKurang.setOnClickListener {
                onKurang(hidangan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HidanganViewHolder {
        val binding = ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HidanganViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HidanganViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object HidanganDiffCallback : DiffUtil.ItemCallback<ItemMenuUi>() {
        override fun areItemsTheSame(oldItem: ItemMenuUi, newItem: ItemMenuUi): Boolean {
            return oldItem.hidangan.id == newItem.hidangan.id
        }

        override fun areContentsTheSame(oldItem: ItemMenuUi, newItem: ItemMenuUi): Boolean {
            return oldItem == newItem
        }
    }
}
