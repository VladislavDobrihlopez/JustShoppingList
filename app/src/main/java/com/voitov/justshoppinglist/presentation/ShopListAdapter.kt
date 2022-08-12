package com.voitov.justshoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {
    companion object {
        const val SHOP_ITEM_FOCUSED = 1000
        const val SHOP_ITEM_UNFOCUSED = 1001

        const val MAX_POOL_SIZE_FOR_FOCUSED = 15
        const val MAX_POOL_SIZE_FOR_UNFOCUSED = 10
    }

    var methodCallsCount = 0
    var shopItems: List<ShopItem> = listOf()
        set(value) {
            val callback = ShopListDiffUtilCallback(shopItems, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value

            //notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopItems[position]

        return if (shopItem.isEnabled) {
            SHOP_ITEM_FOCUSED
        } else {
            SHOP_ITEM_UNFOCUSED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        //Log.d("ShopListAdapter", "${++methodCallsCount}")
        val layout = when (viewType) {
            SHOP_ITEM_FOCUSED -> R.layout.shop_item_focused
            SHOP_ITEM_UNFOCUSED -> R.layout.shop_item_unfocused
            else -> throw RuntimeException("Unknown view type is used $viewType")
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
        Log.d("ShopListAdapter", "${++methodCallsCount}")

        val shopItem = shopItems[position]

        viewHolder.textViewName.text = shopItem.name
        viewHolder.textViewCount.text = shopItem.count.toString()

        viewHolder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        viewHolder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        if (shopItem.isEnabled) {
            viewHolder.textViewName.setTextColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    android.R.color.holo_green_light
                )
            )
        }
    }

//    override fun onViewRecycled(holder: ShopListViewHolder) {
//        holder.textViewName.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
//    }

//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

    class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
        val textViewCount = itemView.findViewById<TextView>(R.id.textViewCount)
    }
}