package com.coderipper.maib.usecases.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.coderipper.maib.databinding.FragmentCategoriesBinding
import com.coderipper.maib.models.domain.Categories
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.categories.adapter.BuyProductsAdapter
import com.coderipper.maib.utils.dpToPixels
import com.coderipper.maib.utils.getStringValue
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    private var currentCategory = Categories.CLOSET

    private lateinit var productsAdapter: BuyProductsAdapter
    private val products = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = getStringValue(requireActivity(), "id")!!
        products.clear()
        db.collection("users").get().addOnSuccessListener { data ->
            if (!data.isEmpty) {
                val docs = data.filter { it.id != userId }

                docs.forEach { doc ->
                    val user = doc.toObject<User>()
                    val allProducts = arrayListOf<Product>()
                    allProducts.addAll(user.products)
                    products.addAll(allProducts.filter { product ->
                        product.category == currentCategory.ordinal
                    })
                    productsAdapter.notifyDataSetChanged()
                }
            }
        }

        binding.run {
            productsAdapter = BuyProductsAdapter(userId, products, root.findNavController(), ::openProfile)

            backButton.setOnClickListener {
                root.findNavController().popBackStack()
            }

            searchCard.setOnClickListener {
                root.findNavController().navigate(CategoriesFragmentDirections.toSearch())
            }

            categoriesButton.setOnClickListener {
                frontScrim.isVisible = true
                frontCard.translationY = backLayer.height.toFloat() - dpToPixels(requireContext(), 78F)
            }

            frontScrim.setOnClickListener {
                hideBackDroop()
            }

            clothesButton.setOnClickListener {
                Snackbar.make(root, "Pressed", Snackbar.LENGTH_SHORT).show()
            }

            productsList.apply {
                setHasFixedSize(false)
                adapter = productsAdapter
            }

            clothesButton.setOnClickListener {
                currentCategory = Categories.CLOSET
                hideBackDroop()
            }

            digitalArtButton.setOnClickListener {
                currentCategory = Categories.DIGITAL
                hideBackDroop()
            }

            paintingsButton.setOnClickListener {
                currentCategory = Categories.PAINTING
                hideBackDroop()
            }

            indoorsButton.setOnClickListener {
                currentCategory = Categories.INDOOR
                hideBackDroop()
            }

            sculpturesButton.setOnClickListener {
                currentCategory = Categories.SCULPTURE
                hideBackDroop()
            }

            booksButton.setOnClickListener {
                currentCategory = Categories.BOOKS
                hideBackDroop()
            }

            othersButton.setOnClickListener {
                currentCategory = Categories.NO_SPECIFIED
                hideBackDroop()
            }
        }
    }


    private fun openProfile(userId: String) {
        binding.root.findNavController().navigate(CategoriesFragmentDirections.toProfile(userId))
    }

    private fun hideBackDroop() {
        val userId = getStringValue(requireActivity(), "id")!!

        products.clear()
        db.collection("users").get().addOnSuccessListener { data ->
            if (!data.isEmpty) {
                val docs = data.filter { it.id != userId }

                docs.forEach { doc ->
                    val user = doc.toObject<User>()
                    val allProducts = arrayListOf<Product>()
                    allProducts.addAll(user.products)
                    products.addAll(allProducts.filter { product ->
                        product.category == currentCategory.ordinal
                    })
                    productsAdapter.notifyDataSetChanged()
                }
            }
        }
        binding.run {
            frontCard.translationY = 0F
            frontScrim.isVisible = false
        }
    }

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
        fun newInstance() = CategoriesFragment()
    }
}