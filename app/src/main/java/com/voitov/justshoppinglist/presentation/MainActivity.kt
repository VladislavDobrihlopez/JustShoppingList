package com.voitov.justshoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var linearLayoutShopList: LinearLayout
    private lateinit var textViewName: TextView
    private lateinit var textViewCount: TextView

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutShopList = findViewById(R.id.linearLayoutShopList)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this, object : Observer<List<ShopItem>> {
            override fun onChanged(it: List<ShopItem>) {
                Log.d(TAG, it.toString())
                showShopList(it)
            }
        })

        //viewModel.editShopItem(ShopItem("dadada", 7.7f, true, 0))
    }

    private fun showShopList(items: List<ShopItem>) {
        linearLayoutShopList.removeAllViews()

        for (item in items) {
            val resIdShopItem = if (item.isEnabled) {
                R.layout.shop_item_focused
            } else {
                R.layout.shop_item_unfocused
            }

            val view = LayoutInflater.from(this)
                .inflate(
                    resIdShopItem,
                    linearLayoutShopList,
                    false
                )

            textViewName = view.findViewById(R.id.textViewName)
            textViewCount = view.findViewById(R.id.textViewCount)

            textViewName.text = item.name
            textViewCount.text = item.count.toString()

            view.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(p0: View?): Boolean {
                    viewModel.changeEnableState(item)
                    return true
                }
            })

            linearLayoutShopList.addView(view)
        }
    }
}