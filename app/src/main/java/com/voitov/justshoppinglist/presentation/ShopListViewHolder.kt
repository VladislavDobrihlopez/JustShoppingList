package com.voitov.justshoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voitov.justshoppinglist.R

class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
    val textViewCount = itemView.findViewById<TextView>(R.id.textViewCount)
}