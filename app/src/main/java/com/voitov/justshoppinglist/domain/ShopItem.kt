package com.voitov.justshoppinglist.domain

data class ShopItem(
    val id: Int,
    val name: String,
    val count: Float,
    val isEnabled: Boolean,
)