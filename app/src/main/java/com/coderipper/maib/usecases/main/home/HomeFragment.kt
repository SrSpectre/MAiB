package com.coderipper.maib.usecases.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.coderipper.maib.MainNavGraphDirections
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.usecases.main.MainFragmentDirections

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
        binding.run {
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