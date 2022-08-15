package com.voitov.justshoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class ShopItemInfoActivity : AppCompatActivity() {
    //    private lateinit var textInputLayoutName: TextInputLayout
//    private lateinit var textInputEditTextName: TextInputEditText
//    private lateinit var textInputLayoutCount: TextInputLayout
//    private lateinit var textInputEditTextCount: TextInputEditText
//    private lateinit var buttonSave: Button
//
//    private lateinit var viewModel: ShopItemInfoViewModel
//
    private var viewModeFromIntent = VIEW_MODE_UNKNOWN
    private var shopItemIdFromIntent = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_info)
        parseIntent()
//
//        initViews()
        //viewModel = ViewModelProvider(this).get(ShopItemInfoViewModel::class.java)

//        setupClickListeners()
//        observeViewModel()
        startAppropriateViewMode()
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

    //
//    private fun startAddViewMode() {
//        buttonSave.setOnClickListener {
//            val name = textInputEditTextName.text?.toString()
//            val count = textInputEditTextCount.text?.toString()
//
//            viewModel.addShopItem(name, count)
//        }
//    }
//
//    private fun startEditViewMode() {
//        viewModel.getShopItem(shopItemIdFromIntent)
//
//        viewModel.shopItemLD.observe(this) {
//            textInputEditTextName.setText(it.name)
//            textInputEditTextCount.setText(it.count.toString())
//        }
//
//        buttonSave.setOnClickListener {
//            val name = textInputEditTextName.text?.toString()
//            val count = textInputEditTextCount.text?.toString()
//
//            viewModel.editShopItem(shopItemIdFromIntent, name, count)
//        }
//    }
//
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
//
//    private fun initViews() {
//        textInputLayoutName = findViewById(R.id.textInputLayoutName)
//        textInputEditTextName = findViewById(R.id.textInputEditTextName)
//        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
//        textInputEditTextCount = findViewById(R.id.textInputEditTextCount)
//        buttonSave = findViewById(R.id.buttonSave)
//    }
//
//    private fun setupClickListeners() {
//        textInputEditTextCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                //TODO("Not yet implemented")
//            }
//        })
//
//        textInputEditTextName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                //TODO("Not yet implemented")
//            }
//        })
//    }
//
//    private fun observeViewModel() {
//        viewModel.haveToCloseView.observe(this) {
//            finish()
//        }
//
//        viewModel.errorInputName.observe(this) {
//            textInputLayoutName.error = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//        }
//
//        viewModel.errorInputCount.observe(this) {
//            textInputLayoutCount.error = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//        }
//    }

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
}