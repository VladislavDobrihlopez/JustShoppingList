package com.voitov.justshoppinglist.data

import com.voitov.justshoppinglist.domain.ShopItem
import com.voitov.justshoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {

    private val shopItems = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopItems.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopItems.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        deleteShopItem(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopItems.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id is not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopItems.toList()
    }
}