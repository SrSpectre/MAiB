package com.coderipper.maib.usecases.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentDashboardBinding
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.databinding.FragmentSearchBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.usecases.search.adapter.SearchProductsAdapter
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getLongValue
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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
        val userId = getLongValue(requireActivity(), "id")
        searchProductsAdapter = SearchProductsAdapter(userId, productsFound)

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
                productsFound.addAll(DataBase.getProductsBySearch(it.toString().trim()))
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