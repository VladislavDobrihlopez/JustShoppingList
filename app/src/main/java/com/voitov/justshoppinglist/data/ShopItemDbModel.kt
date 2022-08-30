package com.voitov.justshoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    val name: String,
    val count: Float,
    val isEnabled: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)