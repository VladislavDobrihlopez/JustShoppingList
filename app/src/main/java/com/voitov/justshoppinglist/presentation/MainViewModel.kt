package com.voitov.justshoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.voitov.justshoppinglist.data.ShopListRepositoryImpl
import com.voitov.justshoppinglist.domain.DeleteShopItemUseCase
import com.voitov.justshoppinglist.domain.EditShopItemUseCase
import com.voitov.justshoppinglist.domain.GetShopListUseCase
import com.voitov.justshoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }
}