package com.voitov.justshoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.voitov.justshoppinglist.domain.ShopItem

class ShopListDiffUtilCallback(
    private val oldItems: List<ShopItem>,
    private val newItems: List<ShopItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem == newItem
    }
}