package com.voitov.justshoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity(), ShopItemInfoFragment.OnEditingFinishedListener {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private var fragmentContainerViewOptional: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ShopItemInfoFragment", "Main activity method onCreate is called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentContainerViewOptional = findViewById(R.id.fragmentContainerViewOptional)
        setupRecyclerView()
        setupButtonClickListener()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this) { it ->
            adapter.submitList(it)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ShopItemInfoFragment", "Main activity method onStart is called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ShopItemInfoFragment", "Main activity method onResume is called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ShopItemInfoFragment", "Main activity method onPause is called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ShopItemInfoFragment", "Main activity method onStop is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ShopItemInfoFragment", "Main activity method onDestroy is called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("ShopItemInfoFragment", "Main activity method onRestart is called")
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
        adapter.onShopItemClickListener = getAppropriateOnShopItemClickListener()
    }

    private fun setupShopItemLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupButtonClickListener() {
        val buttonAddShopItem = findViewById<FloatingActionButton>(R.id.buttonAddShopItem)
        buttonAddShopItem.setOnClickListener(getAppropriateOnButtonClickListener())
    }

    private fun getAppropriateOnShopItemClickListener(): (ShopItem) -> Unit =
        if (isBookOrientationMode()) {
            {
                startActivity(ShopItemInfoActivity.newIntentModeEdit(this, it.id))
            }
        } else {
            {
                val fragment = ShopItemInfoFragment.newInstanceModeEdit(it.id)
                startAppropriateFragment(fragment)
            }
        }

    private fun getAppropriateOnButtonClickListener() =
        View.OnClickListener {
            if (isBookOrientationMode()) {
                startActivity(ShopItemInfoActivity.newIntentModeAdd(this))
            } else {
                startAppropriateFragment(ShopItemInfoFragment.newInstanceModeAdd())
            }
        }

    private fun isBookOrientationMode() = fragmentContainerViewOptional == null

    private fun startAppropriateFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerViewOptional, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onEditingFinished() {
        Toast.makeText(
            this@MainActivity,
            "Editing has finished successfully",
            Toast.LENGTH_SHORT
        ).show()
        supportFragmentManager.popBackStack()
    }
}