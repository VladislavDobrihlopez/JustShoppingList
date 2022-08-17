package com.voitov.justshoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class ShopItemInfoActivity : AppCompatActivity(), ShopItemInfoFragment.OnEditingFinishedListener {
    private var viewModeFromIntent = VIEW_MODE_UNKNOWN
    private var shopItemIdFromIntent = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_info)

        parseIntent()

        if (savedInstanceState == null) {
            startAppropriateViewMode()
        }
    }

    private fun startAppropriateViewMode() {
        val fragment = when (viewModeFromIntent) {
            VIEW_MODE_ADD -> ShopItemInfoFragment.newInstanceModeAdd()
            VIEW_MODE_EDIT -> ShopItemInfoFragment.newInstanceModeEdit(shopItemIdFromIntent)
            else -> throw RuntimeException("Unknown view mode $viewModeFromIntent")
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerViewShopItemInfo, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_VIEW_MODE)) {
            throw RuntimeException("Param EXTRA_VIEW_MODE is absent")
        }

        val viewMode = intent.getStringExtra(EXTRA_VIEW_MODE)

        if (viewMode == VIEW_MODE_ADD) {
            viewModeFromIntent = VIEW_MODE_ADD
        } else if (viewMode == VIEW_MODE_EDIT) {
            viewModeFromIntent = VIEW_MODE_EDIT

            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param EXTRA_SHOP_ITEM_ID is absent")
            }

            shopItemIdFromIntent = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        } else {
            throw RuntimeException("Unknown mode $viewMode is used")
        }
    }

    companion object {
        private const val EXTRA_VIEW_MODE = "extra_mode"
        private const val VIEW_MODE_ADD = "mode_add"
        private const val VIEW_MODE_EDIT = "mode_edit"
        private const val VIEW_MODE_UNKNOWN = "unknown"

        private const val EXTRA_SHOP_ITEM_ID = "extra_id"

        fun newIntentModeEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemInfoActivity::class.java)
            intent.putExtra(EXTRA_VIEW_MODE, VIEW_MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }

        fun newIntentModeAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemInfoActivity::class.java)
            intent.putExtra(EXTRA_VIEW_MODE, VIEW_MODE_ADD)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}