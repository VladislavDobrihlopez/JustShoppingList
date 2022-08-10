package com.voitov.justshoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(id: Int) {
        shopListRepository.editShopItem(id)
    }
}