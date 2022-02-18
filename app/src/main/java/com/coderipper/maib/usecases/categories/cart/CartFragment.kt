package com.coderipper.maib.usecases.categories.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentCartBinding
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.models.domain.Cart
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.usecases.categories.cart.adapter.CartAdapter
import com.coderipper.maib.usecases.categories.cart.adapter.WishlistAdapter
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter
    private lateinit var wishlistAdapter: WishlistAdapter
    private var cartList = mutableListOf<Product>()
    private var wishlist = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = getLongValue(requireActivity(), "id")

        binding.run {
            val bottomSheet = BottomSheetBehavior.from(cartCard)
            val visibleHeight = displayDimens(requireContext()).widthPixels - pixelsToDp(requireContext(), 130F)
            cartCard.doOnLayout {
                it.translationX = visibleHeight
            }
            cartAdapter = CartAdapter(requireContext(), cartList, ::removeCartProduct)
            wishlistAdapter = WishlistAdapter(wishlist, ::removeWishlistProduct)

            bottomSheet.addBottomSheetCallback(CartSheetCallback(visibleHeight))

            actionButton.setOnClickListener {
                bottomSheet.state =
                    if(bottomSheet.state != BottomSheetBehavior.STATE_EXPANDED){
                        loadCart(userId)
                        loadWishlist(userId)
                        BottomSheetBehavior.STATE_EXPANDED
                    }
                    else BottomSheetBehavior.STATE_COLLAPSED
            }

            productsList.apply {
                setHasFixedSize(false)
                adapter = cartAdapter
            }

            wishlistList.apply {
                setHasFixedSize(false)
                adapter = wishlistAdapter
            }
        }
    }

    private fun clearFields() {
        binding.run {
            productsPriceText.text = "$0.00"
            sendPriceText.text = "$0.00"
            ivaPriceText.text = "$0.00"
            totalPriceText.text = "$0.00"
        }
    }

    private fun calculateCosts() {
        if(cartList.isNotEmpty()) {
            binding.run {
                var price = 0.0F
                cartList.forEach { price += it.price.toFloat() }
                val send = price * 0.15F
                val iva = price * 0.10F

                productsPriceText.text = "$$price"
                sendPriceText.text = "$$send"
                ivaPriceText.text = "$$iva"
                totalPriceText.text = "$${price + send + iva}"
            }
        } else clearFields()
    }

    private fun removeCartProduct(productId: Long) {
        val userId = getLongValue(requireActivity(), "id")
        DataBase.removeFromCart(userId, productId)
        loadCart(userId)
    }

    private fun loadCart(userId: Long) {
        cartList.clear()
        cartList.addAll(DataBase.getCartByUserId(userId))
        cartAdapter.notifyDataSetChanged()
        calculateCosts()
    }

    private fun removeWishlistProduct(productId: Long) {
        val userId = getLongValue(requireActivity(), "id")
        DataBase.removeFromWishlist(userId, productId)
        loadWishlist(userId)
    }

    private fun loadWishlist(userId: Long) {
        wishlist.clear()
        wishlist.addAll(DataBase.getWishlistByUserId(userId))
        wishlistAdapter.notifyDataSetChanged()
    }

    inner class CartSheetCallback(private val visibleHeight: Float): BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when(newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {}
                BottomSheetBehavior.STATE_COLLAPSED ->
                    binding.actionButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_cart))
                BottomSheetBehavior.STATE_DRAGGING -> {}
                BottomSheetBehavior.STATE_EXPANDED ->
                    binding.actionButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_close))
                else -> {}
            }
        }
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            bottomSheet.translationX = linearInterpolationCurve(visibleHeight, 0F, 0F, 0.2F, slideOffset)
        }
    }


    /*class ExpandingBottomSheetCallback(private val maxWidth: Float) : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(sheet: View, slideOffset: Float) {
            sheet.translationX = linearInterpolationCurve(maxWidth, 0F, 0F, 0.2F, slideOffset)
            background.interpolation = linearInterpolationCurve(1f, 0f, 0f, 0.2f, slideOffset)
            if (slideOffset < 1F) {
                binding.createElementFab.translationY = 200F
            }
        }

        override fun onStateChanged(sheet: View, newState: Int) {
            currentAnimation = when (newState) {
                BACK -> sheet.springAnimation(SpringAnimation.X, sheet.x, 0.5F, 1500F, 1000F)
                SHOW -> {
                    binding.createElementFab.springAnimation(SpringAnimation.TRANSLATION_Y, 0F, 0.4F)
                    sheet.springAnimation(SpringAnimation.Y, 0F, 0.5F, 1500F, 1000F)
                }
                else -> null
            }
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}