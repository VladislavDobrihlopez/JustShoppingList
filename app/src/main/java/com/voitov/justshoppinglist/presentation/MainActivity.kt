package com.voitov.justshoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this, object : Observer<List<ShopItem>> {
            override fun onChanged(t: List<ShopItem>) {
                Log.d(TAG, t.toString())
            }
        })

        viewModel.getShopList()
    }
}