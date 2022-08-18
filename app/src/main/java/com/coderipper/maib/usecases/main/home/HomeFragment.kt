package com.coderipper.maib.usecases.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coderipper.maib.MainNavGraphDirections
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.usecases.main.home.adapter.RecommendationsAdapter
import com.coderipper.maib.utils.getBooleanValue
import com.coderipper.maib.utils.getStringValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.squareup.picasso.Picasso
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

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
        val userId = getStringValue(requireActivity(), "id")!!
        val productsList = ArrayList<Product>()

        db.collection("users").get().addOnSuccessListener { data ->
            if (!data.isEmpty) {
                val docs = data.filter { it.id != userId }

                val products = arrayListOf<Product>()
                docs.forEach { doc ->
                    val user = doc.toObject<User>()
                    products.addAll(user.products)
                }

                if (products.isNotEmpty()) {
                    if (products.size > 1) {
                        val promotional = products[Random.nextInt(0, products.size -1)]
                        products.remove(promotional)
                        setPromotionalCard(userId, promotional)

                        val times = if (products.size > 10) 10 else products.size
                        repeat(times) {
                            productsList.add(products[it])
                        }
                    } else
                        setPromotionalCard(userId, products[0])
                } else
                    binding.promotionLayout.promotionalCard.isVisible = false
            }
            binding.recommendationsList.adapter = RecommendationsAdapter(userId, productsList)
        }

        binding.run {
            recommendationsList.apply {
                setHasFixedSize(false)
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

    private fun setPromotionalCard(userId: String, product: Product): String {
        binding.promotionLayout.run {
            promotionalCard.isVisible = getBooleanValue(requireContext(), "promotional")
            Picasso.with(binding.root.context).load(product.images[0].uri.toUri()).into(promotionImage)
            titleText.text = "¡Hay una nueva creación!"
            priceText.text = "$${product.price}"

            addCartButton.addOnCheckedChangeListener { _, isChecked ->
                /*if(isChecked)
                    DataBase.setToCart(userId, product.id)
                else DataBase.removeFromCart(userId, product.id)*/
            }

            addWishlistButton.addOnCheckedChangeListener { _, isChecked ->
                /*if(isChecked)
                    DataBase.setToWishlist(userId, product.id)
                else DataBase.removeFromWishlist(userId, product.id)*/
            }
        }
        return product.id
        return ""
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