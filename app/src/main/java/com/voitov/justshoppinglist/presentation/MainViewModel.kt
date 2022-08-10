package com.voitov.justshoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voitov.justshoppinglist.data.ShopListRepositoryImpl
import com.voitov.justshoppinglist.domain.DeleteShopItemUseCase
import com.voitov.justshoppinglist.domain.EditShopItemUseCase
import com.voitov.justshoppinglist.domain.GetShopListUseCase
import com.voitov.justshoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    private val _shopList = MutableLiveData<List<ShopItem>>()
    val shopList: LiveData<List<ShopItem>>
        get() = _shopList

    fun getShopList() {
        _shopList.value = getShopListUseCase.getShopList()
    }

    fun editShopItem(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(shopItem)
        getShopList()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
        editShopItem(newShopItem)
    }
}