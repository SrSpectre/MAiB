package com.coderipper.maib.usecases.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentDashboardBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.usecases.main.dashboard.adapter.SectionsPagerAdapter
import com.coderipper.maib.utils.getStringValue
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

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
        val userId = getStringValue(requireActivity(), "id")!!
        val navController = activity?.findNavController(R.id.nav_host_fragment)
        binding.run {
            if(navController == null)
                root.findNavController().popBackStack()

            createProductFab.setOnClickListener {
                val items = arrayOf("Closet", "Arte Digital", "Pinturas", "Interiores", "Esculturas", "Libros", "Sin categoria")
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Categoria")
                    .setItems(items) { _, which ->
                        navController?.navigate(MainFragmentDirections.toCreate(which))
                    }
                    .show()
            }


            sectionsPager.apply {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 2
                getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            }

            db.collection("users").document(userId).get().addOnSuccessListener { data ->
                if (data.exists()) {
                    val user = data.toObject<User>()
                    if (user != null) {
                        followingText.text = user.followers.size.toString()
                        rateText.text = user.rate.toString()
                        sectionsPager.adapter = SectionsPagerAdapter(userId, arrayListOf(user.products.toMutableList(), user.following.toMutableList()), navController!!)
                        sectionsPager.adapter?.notifyDataSetChanged()
                        TabLayoutMediator(sectionsTab, sectionsPager) { tab, position ->
                            when (position) {
                                0 -> tab.text = "Mis productos"
                                1 -> tab.text = "Siguiendo: " + user.following.size
                            }
                        }.attach()
                    }
                    else
                        Snackbar.make(root, "Error con cuenta", Snackbar.LENGTH_SHORT).show()
                }
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
        fun newInstance() = DashboardFragment()
    }
}