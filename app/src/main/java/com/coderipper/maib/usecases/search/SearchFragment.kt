package com.coderipper.maib.usecases.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.coderipper.maib.databinding.FragmentSearchBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.search.adapter.SearchProductsAdapter
import com.coderipper.maib.utils.getStringValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    private lateinit var searchProductsAdapter: SearchProductsAdapter
    private val productsFound = arrayListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = getStringValue(requireActivity(), "id")!!
        searchProductsAdapter = SearchProductsAdapter(userId, productsFound)

        val products = ArrayList<Product>()

        db.collection("users").get().addOnSuccessListener { data ->
            if (!data.isEmpty) {
                val docs = data.filter { it.id != userId }

                docs.forEach { doc ->
                    val user = doc.toObject<User>()
                    products.addAll(user.products)
                }
            }
        }

        binding.run {
            searchInput.requestFocus()
            val inputMethod = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.showSoftInput(searchInput, 0)

            backButton.setOnClickListener {
                root.findNavController().popBackStack()
            }

            productsList.apply {
                setHasFixedSize(false)
                adapter = searchProductsAdapter
            }

            searchInput.addTextChangedListener {
                productsFound.clear()
                if(it.toString().isNotEmpty()) {
                    productsFound.addAll(products.filter { product ->
                        product.name.lowercase().contains(it.toString().lowercase().trim())
                    })
                }
                searchProductsAdapter.notifyDataSetChanged()
            }
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
        fun newInstance() = SearchFragment()
    }
}