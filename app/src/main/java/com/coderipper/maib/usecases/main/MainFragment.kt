package com.coderipper.maib.usecases.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coderipper.maib.MainNavGraphDirections
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.usecases.main.dashboard.DashboardFragmentDirections
import com.coderipper.maib.usecases.main.home.HomeFragmentDirections

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            homeToolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }

            drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    homeLayout.translationX = slideOffset * menuNavigation.width
                }

                override fun onDrawerOpened(drawerView: View) {}
                override fun onDrawerClosed(drawerView: View) {}
                override fun onDrawerStateChanged(newState: Int) {}
            })

            menuNavigation.setNavigationItemSelectedListener { menuItem ->
                menuItem.isChecked = true
                drawerLayout.close()

                when (menuItem.itemId) {
                    R.id.home -> {
                        sectionText.text = "¡Bienvenida de nuevo Anny!"
                        navMainFragment.findNavController().navigate(DashboardFragmentDirections.toHome())
                    }
                    R.id.dashboard -> {
                        sectionText.text = "Tu información"
                        navMainFragment.findNavController().navigate(HomeFragmentDirections.toDashboard())
                    }
                    R.id.settings -> {
                        sectionText.text = "Configuración"
                        navMainFragment.findNavController().navigate(HomeFragmentDirections.toDashboard())
                    }
                    R.id.help -> {
                        sectionText.text = "Ayuda"
                        navMainFragment.findNavController().navigate(MainNavGraphDirections.toHelp())
                    }
                    R.id.about_us -> {
                        sectionText.text = "Sobre nosotros"
                        navMainFragment.findNavController().navigate(MainNavGraphDirections.toAbout())
                    }
                    R.id.logout -> {
                        root.findNavController().navigate(MainFragmentDirections.toLogin())
                    }
                }

                true
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
        fun newInstance() = MainFragment()
    }
}