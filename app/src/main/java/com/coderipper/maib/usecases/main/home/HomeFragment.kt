package com.coderipper.maib.usecases.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coderipper.maib.MainNavGraphDirections
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.usecases.categories.CategoriesFragmentDirections
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.usecases.main.home.adapter.RecommendationsAdapter
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getBooleanValue
import com.coderipper.maib.utils.getLongValue

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = getLongValue(requireActivity(), "id")
        val promotionalId = setPromotionalCard(userId)
        val productsList = DataBase.getRecommendations(userId, promotionalId)

        binding.run {
            recommendationsList.apply {
                setHasFixedSize(false)
                adapter = RecommendationsAdapter(userId, productsList)
                isVisible = getBooleanValue(requireContext(), "home_recomm")
            }

            searchCard.setOnClickListener {
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(MainFragmentDirections.toSearch())
            }

            categoriesFab.setOnClickListener {
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(MainFragmentDirections.toCategories())
            }

            helpFab.setOnClickListener {
                root.findNavController().navigate(MainNavGraphDirections.toHelp())
            }

            settingsFab.setOnClickListener {
                root.findNavController().navigate(MainNavGraphDirections.toSettings())
            }

            myProductsFab.setOnClickListener {
                root.findNavController().navigate(MainNavGraphDirections.toDashboard())
            }
        }
    }

    private fun setPromotionalCard(userId: Long): Long {
        val product = DataBase.getPromotional(userId)
        val user = DataBase.getUserById(product.userId)
        binding.promotionLayout.run {
            promotionalCard.isVisible = getBooleanValue(requireContext(), "promotional")
            promotionImage.setImageURI(product.images[0])
            titleText.text = "¡${user?.name} tiene una nueva creación!"
            priceText.text = "$${product.price}"

            addCartButton.addOnCheckedChangeListener { button, isChecked ->
                if(isChecked)
                    DataBase.setToCart(userId, product.id)
                else DataBase.removeFromCart(userId, product.id)
            }

            addWishlistButton.addOnCheckedChangeListener { button, isChecked ->
                if(isChecked)
                    DataBase.setToWishlist(userId, product.id)
                else DataBase.removeFromWishlist(userId, product.id)
            }
        }
        return product.id
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
        fun newInstance() = HomeFragment()
    }
}