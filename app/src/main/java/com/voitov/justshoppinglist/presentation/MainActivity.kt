package com.voitov.justshoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.voitov.justshoppinglist.R

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupButtonClickListener()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this) { it ->
            adapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        val recyclerViewShopList = findViewById<RecyclerView>(R.id.recyclerViewShopList)
        adapter = ShopListAdapter()
        recyclerViewShopList.adapter = adapter

        recyclerViewShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.SHOP_ITEM_FOCUSED,
            ShopListAdapter.MAX_POOL_SIZE_FOR_FOCUSED
        )
        recyclerViewShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.SHOP_ITEM_UNFOCUSED,
            ShopListAdapter.MAX_POOL_SIZE_FOR_UNFOCUSED
        )

        setupShopItemLongClickListener()
        setupShopItemClickListener()
        setupShopListTouchHelper(recyclerViewShopList)
    }

    private fun setupShopListTouchHelper(recyclerViewShopList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val shopItem = adapter.currentList[itemPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewShopList)
    }

    private fun setupShopItemClickListener() {
        adapter.onShopItemClickListener = {
            Log.d(TAG, it.toString())
            startActivity(ShopItemInfoActivity.newIntentModeEdit(this, it.id))
        }
    }

    private fun setupShopItemLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupButtonClickListener() {
        val buttonAddShopItem = findViewById<FloatingActionButton>(R.id.buttonAddShopItem)

        buttonAddShopItem.setOnClickListener {
            startActivity(ShopItemInfoActivity.newIntentModeAdd(this))
        }
    }
}