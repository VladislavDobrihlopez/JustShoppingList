package com.voitov.justshoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voitov.justshoppinglist.data.ShopListRepositoryImpl
import com.voitov.justshoppinglist.domain.AddShopItemUseCase
import com.voitov.justshoppinglist.domain.EditShopItemUseCase
import com.voitov.justshoppinglist.domain.GetShopItemUseCase
import com.voitov.justshoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() {
            return _shopItem
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
        viewModelScope.launch {
            _shopItem.postValue(getShopItemUseCase.getShopItem(shopItemId))
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)

        val areFieldsValid = validateInput(name, count)

        if (areFieldsValid) {
            val shopItem = ShopItem(name, count, true)
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(shopItem)
                _haveToCloseView.postValue(Unit)
            }
        }
    }

    fun editShopItem(shopItemId: Int, editedName: String?, editedCount: String?) {
        val name = parseInputName(editedName)
        val count = parseInputCount(editedCount)

        val areFieldsValid = validateInput(name, count)

        if (areFieldsValid) {
            viewModelScope.launch {
                val shopItem = getShopItemUseCase.getShopItem(shopItemId)
                val newShopItem = shopItem.copy(name = name, count = count)

                editShopItemUseCase.editShopItem(newShopItem)
                _haveToCloseView.postValue(Unit)
            }
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