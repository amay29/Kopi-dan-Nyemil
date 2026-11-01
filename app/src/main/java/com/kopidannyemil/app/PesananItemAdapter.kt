package com.kopidannyemil.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kopidannyemil.app.databinding.ItemRingkasanPesananBinding
import com.kopidannyemil.app.model.ItemPesanan
import com.kopidannyemil.app.util.formatRupiah

class PesananItemAdapter(
    private val items: List<ItemPesanan>
) : RecyclerView.Adapter<PesananItemAdapter.PesananItemViewHolder>() {

    inner class PesananItemViewHolder(private val binding: ItemRingkasanPesananBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemPesanan) {
            binding.tvNamaItem.text = item.hidangan.nama
            binding.tvKuantitasItem.text = "x${item.kuantitas}"
            binding.tvSubtotalItem.text = formatRupiah(item.subtotal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananItemViewHolder {
        val binding = ItemRingkasanPesananBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PesananItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PesananItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
