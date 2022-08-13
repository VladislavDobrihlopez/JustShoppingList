package com.voitov.justshoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.voitov.justshoppinglist.domain.ShopItem

class ShopItemDiffUtilCallback : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}