package com.voitov.justshoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voitov.justshoppinglist.data.ShopListRepositoryImpl
import com.voitov.justshoppinglist.domain.AddShopItemUseCase
import com.voitov.justshoppinglist.domain.EditShopItemUseCase
import com.voitov.justshoppinglist.domain.GetShopItemUseCase
import com.voitov.justshoppinglist.domain.ShopItem

class ShopItemInfoViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() {
            return _shopItemLD
        }

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() {
            return _errorInputName
        }

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() {
            return _errorInputCount
        }

    private val _haveToCloseView = MutableLiveData<Unit>()
    val haveToCloseView: LiveData<Unit>
        get() {
            return _haveToCloseView
        }

    fun getShopItem(shopItemId: Int) {
        _shopItemLD.value = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)

        val areFieldsValid = validateInput(name, count)

        if (areFieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            _haveToCloseView.value = Unit
        }
    }

    fun editShopItem(shopItemId: Int, editedName: String?, editedCount: String?) {
        val name = parseInputName(editedName)
        val count = parseInputCount(editedCount)

        val areFieldValid = validateInput(name, count)

        if (areFieldValid) {
            val shopItem = getShopItemUseCase.getShopItem(shopItemId)
            val newShopItem = shopItem.copy(name = name, count = count)

            editShopItemUseCase.editShopItem(newShopItem)
            _haveToCloseView.value = Unit
        }
    }

    private fun validateInput(name: String, count: Float): Boolean {
        var isInputValid = true

        if (name.isBlank()) {
            // TODO: display toast message with error (incorrect name value)
            _errorInputName.value = true
            isInputValid = false
        }

        if (count <= 0) {
            // TODO: display toast message with error (incorrect count value)
            _errorInputCount.value = true
            isInputValid = false
        }

        return isInputValid
    }

    private fun parseInputName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseInputCount(inputCount: String?): Float {
        return try {
            inputCount?.trim()?.toFloat() ?: 0f
        } catch (ex: Exception) {
            0f
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }
}