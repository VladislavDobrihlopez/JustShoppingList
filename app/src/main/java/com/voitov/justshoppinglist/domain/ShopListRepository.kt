package com.voitov.justshoppinglist.domain

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(id: Int)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): List<ShopItem>
}