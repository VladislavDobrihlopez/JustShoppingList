package com.voitov.justshoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Float,
    val isEnabled: Boolean,
    var id: Int = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}