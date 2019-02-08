package me.tellvivk.messenger.app.screens.home.adapter

import androidx.recyclerview.widget.DiffUtil

class SmsItemListDiffCallback(
    private val oldItems: List<SMSListItem>,
    private val newItems: List<SMSListItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}