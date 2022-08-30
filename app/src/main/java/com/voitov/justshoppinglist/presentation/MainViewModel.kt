package com.voitov.justshoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.voitov.justshoppinglist.data.ShopListRepositoryImpl
import com.voitov.justshoppinglist.domain.DeleteShopItemUseCase
import com.voitov.justshoppinglist.domain.EditShopItemUseCase
import com.voitov.justshoppinglist.domain.GetShopListUseCase
import com.voitov.justshoppinglist.domain.ShopItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun editShopItem(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
        editShopItem(newShopItem)
    }
}