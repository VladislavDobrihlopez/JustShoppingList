package com.voitov.justshoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    private val shopItems: List<ShopItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.activity_main,
                parent,
                false
            )

        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = shopItems[position]
        holder.textViewName.text = shopItem.name
        holder.textViewCount.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

    class ShopListViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
        val textViewCount = itemView.findViewById<TextView>(R.id.textViewCount)
    }
}