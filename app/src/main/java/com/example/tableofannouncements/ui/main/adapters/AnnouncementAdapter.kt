package com.example.tableofannouncements.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tableofannouncements.databinding.ItemAnnouncementBinding
import com.example.tableofannouncements.models.announcement.Announcement

class AnnouncementAdapter(
    private val clickEvent: (Announcement) -> Unit,
) : RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>() {

    private val items: ArrayList<Announcement> = arrayListOf()

    class AnnouncementViewHolder(
        private val binding: ItemAnnouncementBinding,
        private val clickEvent: (Announcement) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(announcement: Announcement) {
            with(binding) {
                tvTitleAnn.text = announcement.title.toString()
                tvPriceAnn.text = announcement.price.toString()
                tvPriceCurrencyAnn.text = announcement.priceCurrency.toString()
                tvLocationAnn.text = announcement.city.toString()
                clMain.setOnClickListener {
                    clickEvent(announcement)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(announcementList: List<Announcement>) {
        items.clear()
        items.addAll(announcementList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val binding =
            ItemAnnouncementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnnouncementViewHolder(binding, clickEvent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(items[position])
    }
}