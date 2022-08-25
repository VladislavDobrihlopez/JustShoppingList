package com.voitov.justshoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.voitov.justshoppinglist.databinding.FragmentShopInfoItemBinding
import com.voitov.justshoppinglist.domain.ShopItem

class ShopItemInfoFragment : Fragment() {
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var viewModel: ShopItemInfoViewModel
    private var viewModeFromBundle: String = VIEW_MODE_UNKNOWN
    private var shopItemIdFromBundle: Int = ShopItem.UNDEFINED_ID

    private var _viewBinding: FragmentShopInfoItemBinding? = null
    private val viewBinding: FragmentShopInfoItemBinding
        get() = _viewBinding
            ?: throw RuntimeException("viewBinding in ShopItemInfoFragment is null")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d(TAG, "onAttach")

        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("It seems like activity doesn't implement the OnEditingFinishedListener interface")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        _viewBinding = FragmentShopInfoItemBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        viewModel = ViewModelProvider(this).get(ShopItemInfoViewModel::class.java)
        setupViewBinding()

        setupClickListeners()
        observeViewModel()
        startAppropriateViewMode()
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()

        Log.d(TAG, "onDetach")
    }

    private fun setupViewBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun startAppropriateViewMode() {
        when (viewModeFromBundle) {
            VIEW_MODE_ADD -> startAddViewMode()
            VIEW_MODE_EDIT -> startEditViewMode()
        }
    }

    private fun startAddViewMode() {
        viewBinding.buttonSave.setOnClickListener {
            val name = viewBinding.textInputEditTextName.text?.toString()
            val count = viewBinding.textInputEditTextCount.text?.toString()

            viewModel.addShopItem(name, count)
        }
    }

    private fun startEditViewMode() {
        viewModel.getShopItem(shopItemIdFromBundle)
        viewBinding.buttonSave.setOnClickListener {
            val name = viewBinding.textInputEditTextName.text?.toString()
            val count = viewBinding.textInputEditTextCount.text?.toString()

            viewModel.editShopItem(shopItemIdFromBundle, name, count)
        }
    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(PARAM_VIEW_MODE)) {
            throw RuntimeException("Param PARAM_VIEW_MODE is absent")
        }

        val viewMode = args.get(PARAM_VIEW_MODE)

        if (viewMode == VIEW_MODE_ADD) {
            viewModeFromBundle = VIEW_MODE_ADD
        } else if (viewMode == VIEW_MODE_EDIT) {
            viewModeFromBundle = VIEW_MODE_EDIT

            shopItemIdFromBundle = args.getInt(PARAM_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
            if (shopItemIdFromBundle == ShopItem.UNDEFINED_ID) {
                throw RuntimeException("Param EXTRA_SHOP_ITEM_ID is absent")
            }
        } else {
            throw RuntimeException("Unknown mode $viewMode is used")
        }
    }

    private fun setupClickListeners() {
        viewBinding.textInputEditTextCount.addTextChangedListener(object : TextWatcher {
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

        viewBinding.textInputEditTextName.addTextChangedListener(object : TextWatcher {
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
            //activity?.onBackPressed()
            onEditingFinishedListener.onEditingFinished()
        }
    }

    companion object {
        private const val TAG = "ShopItemInfoFragment"
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

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }
}