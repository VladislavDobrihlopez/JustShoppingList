package com.voitov.justshoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffUtilCallback()) {
    companion object {
        const val SHOP_ITEM_FOCUSED = 1000
        const val SHOP_ITEM_UNFOCUSED = 1001

        const val MAX_POOL_SIZE_FOR_FOCUSED = 15
        const val MAX_POOL_SIZE_FOR_UNFOCUSED = 10
    }

    var onShopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)

        return if (shopItem.isEnabled) {
            SHOP_ITEM_FOCUSED
        } else {
            SHOP_ITEM_UNFOCUSED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            SHOP_ITEM_FOCUSED -> R.layout.shop_item_focused
            SHOP_ITEM_UNFOCUSED -> R.layout.shop_item_unfocused
            else -> throw RuntimeException("Unknown viewType $viewType is used")
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(
                layout,
                parent,
                false
            )

        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)

        viewHolder.textViewName.text = shopItem.name
        viewHolder.textViewCount.text = shopItem.count.toString()

        viewHolder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        viewHolder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }
}