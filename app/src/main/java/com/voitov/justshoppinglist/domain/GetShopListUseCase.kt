package com.voitov.justshoppinglist.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList() : List<ShopItem> {
        return shopListRepository.getShopList()
    }
}