package com.voitov.justshoppinglist.data

import com.voitov.justshoppinglist.domain.ShopItem

class ShopItemsMapper {
    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            name = shopItem.name,
            count = shopItem.count,
            isEnabled = shopItem.isEnabled,
            id = shopItem.id
        )
    }

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShopItem {
        return ShopItem(
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            isEnabled = shopItemDbModel.isEnabled,
            id = shopItemDbModel.id
        )
    }

    fun mapListOfDbModelsToListOfEntities(shopItemDbModels: List<ShopItemDbModel>): List<ShopItem> {
        return shopItemDbModels.map {
            mapDbModelToEntity(it)
        }
    }
}