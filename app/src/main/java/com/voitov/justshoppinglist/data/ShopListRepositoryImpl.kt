package com.voitov.justshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voitov.justshoppinglist.domain.ShopItem
import com.voitov.justshoppinglist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopItems = sortedSetOf(object : Comparator<ShopItem> {
        override fun compare(p0: ShopItem, p1: ShopItem): Int {
            return p0.id.compareTo(p1.id)
        }
    })
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    init {
        for (i in 1..1000) {
            addShopItem(ShopItem("thing $i", i.toFloat(), Random.nextBoolean()))
        }
    }

    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopItems.add(shopItem)
        updateLiveData()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopItems.remove(shopItem)
        updateLiveData()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shopItems.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopItems.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id is not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateLiveData() {
        shopListLD.value = shopItems.toList()
    }
}