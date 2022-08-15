package com.voitov.justshoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.voitov.justshoppinglist.R
import com.voitov.justshoppinglist.domain.ShopItem

class ShopItemInfoFragment : Fragment() {
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var buttonSave: Button

    private lateinit var viewModel: ShopItemInfoViewModel
    private var viewModeFromIntent: String = VIEW_MODE_UNKNOWN
    private var shopItemIdFromIntent: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_info_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ShopItemInfoViewModel::class.java)
        initViews(view)

        setupClickListeners()
        observeViewModel()
        startAppropriateViewMode()
    }

    private fun startAppropriateViewMode() {
        when (viewModeFromIntent) {
            VIEW_MODE_ADD -> startAddViewMode()
            VIEW_MODE_EDIT -> startEditViewMode()
        }
    }

    private fun startAddViewMode() {
        buttonSave.setOnClickListener {
            val name = textInputEditTextName.text?.toString()
            val count = textInputEditTextCount.text?.toString()

            viewModel.addShopItem(name, count)
        }
    }

    private fun startEditViewMode() {
        viewModel.getShopItem(shopItemIdFromIntent)

        viewModel.shopItemLD.observe(viewLifecycleOwner) {
            textInputEditTextName.setText(it.name)
            textInputEditTextCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener {
            val name = textInputEditTextName.text?.toString()
            val count = textInputEditTextCount.text?.toString()

            viewModel.editShopItem(shopItemIdFromIntent, name, count)
        }
    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(PARAM_VIEW_MODE)) {
            throw RuntimeException("Param PARAM_VIEW_MODE is absent")
        }

        val viewMode = args.get(PARAM_VIEW_MODE)

        if (viewMode == VIEW_MODE_ADD) {
            viewModeFromIntent = VIEW_MODE_ADD
        } else if (viewMode == VIEW_MODE_EDIT) {
            viewModeFromIntent = VIEW_MODE_EDIT

            shopItemIdFromIntent = args.getInt(PARAM_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
            if (shopItemIdFromIntent == ShopItem.UNDEFINED_ID) {
                throw RuntimeException("Param EXTRA_SHOP_ITEM_ID is absent")
            }
        } else {
            throw RuntimeException("Unknown mode $viewMode is used")
        }
    }

    private fun initViews(view: View) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputEditTextName = view.findViewById(R.id.textInputEditTextName)
        textInputLayoutCount = view.findViewById(R.id.textInputLayoutCount)
        textInputEditTextCount = view.findViewById(R.id.textInputEditTextCount)
        buttonSave = view.findViewById(R.id.buttonSave)
    }

    private fun setupClickListeners() {
        textInputEditTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
                //TODO("Not yet implemented")
            }
        })

        textInputEditTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun observeViewModel() {
        viewModel.haveToCloseView.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            textInputLayoutName.error = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            textInputLayoutCount.error = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
        }
    }

    companion object {
        private const val PARAM_VIEW_MODE = "extra_mode"
        private const val VIEW_MODE_ADD = "mode_add"
        private const val VIEW_MODE_EDIT = "mode_edit"
        private const val VIEW_MODE_UNKNOWN = "unknown"

        private const val PARAM_SHOP_ITEM_ID = "extra_id"

        fun newInstanceModeEdit(shopItemInfoId: Int): ShopItemInfoFragment {
            return ShopItemInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_VIEW_MODE, VIEW_MODE_EDIT)
                    putInt(PARAM_SHOP_ITEM_ID, shopItemInfoId)
                }
            }
        }

        fun newInstanceModeAdd(): ShopItemInfoFragment {
            return ShopItemInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_VIEW_MODE, VIEW_MODE_ADD)
                }
            }
        }
    }

}