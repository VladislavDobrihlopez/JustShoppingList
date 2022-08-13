package com.voitov.justshoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.voitov.justshoppinglist.R

class ShopItemInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_info)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        private const val EXTRA_SHOP_ITEM_ID = "extra_id"

        fun newIntentModeEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemInfoActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }

        fun newIntentModeAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemInfoActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }
    }
}