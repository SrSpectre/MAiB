package com.coderipper.maib.usecases.main.dashboard

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentDashboardBinding
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.usecases.main.dashboard.adapter.SectionsPagerAdapter
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getLongValue
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = getLongValue(requireActivity(), "id")
        val products = DataBase.getProductsByUserId(userId) as MutableList<Any>
        val following = DataBase.getFollowing(userId) as MutableList<Any>
        val navController = activity?.findNavController(R.id.nav_host_fragment)
        binding.run {
            if(navController == null)
                root.findNavController().popBackStack()

            followingText.text = DataBase.getFollowersCount(userId).toString()
            val rate = DataBase.getUserById(userId)?.rate.toString()
            rateText.text = rate.ifEmpty { "1.0" }

            createProductFab.setOnClickListener {
                val items = arrayOf("Closet", "Arte Digital", "Pinturas", "Interiores", "Esculturas", "Libros", "Sin categoria")
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Categoria")
                    .setItems(items) { dialog, which ->
                        navController?.navigate(MainFragmentDirections.toCreate(which))
                    }
                    .show()
            }


            sectionsPager.apply {
                adapter = SectionsPagerAdapter(arrayListOf(products, following), navController!!)
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 2
                getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            }

            TabLayoutMediator(sectionsTab, sectionsPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Mis productos"
                    1 -> tab.text = "Siguiendo"
                }
            }.attach()
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
        fun newInstance() = DashboardFragment()
    }
}