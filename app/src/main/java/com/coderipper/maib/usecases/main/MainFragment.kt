package com.coderipper.maib.usecases.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coderipper.maib.MainNavGraphDirections
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.usecases.main.stories.StoryOptions
import com.coderipper.maib.usecases.modals.createAvatarsModal
import com.coderipper.maib.utils.*
import com.google.android.material.textview.MaterialTextView


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var uname: String
    private lateinit var headerImage: AppCompatImageView
    private lateinit var avatarsDialog: AlertDialog

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
        val userId = getLongValue(requireActivity(), "id")
        uname = getStringValue(requireActivity(), "uname") ?: ""
        val imgId = getIntValue(requireActivity(), "avatar")

        binding.run {
            val header = menuNavigation.getHeaderView(0)

            avatarSelected.setImageResource(imgId)
            sectionText.text = "¡Hola de nuevo $uname!"
            headerImage = header.findViewById(R.id.avatar_selected)
            headerImage.setImageResource(imgId)
            header.findViewById<MaterialTextView>(R.id.user_name_text).text = getStringValue(requireActivity(), "name")
            header.findViewById<MaterialTextView>(R.id.following_text).text = DataBase.getFollowersCount(userId).toString()
            header.findViewById<MaterialTextView>(R.id.rate_text).text = DataBase.getUserById(userId)?.rate.toString()

            avatarBtn.setOnClickListener {
                avatarsDialog = createAvatarsModal(requireContext(), ::selectedAvatar)
            }

            avatarBtn.setOnLongClickListener {
                storyOptions()
                true
            }

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
                        sectionText.text = "¡Hola de nuevo $uname!"
                        navMainFragment.findNavController().navigate(MainNavGraphDirections.toHome())
                    }
                    R.id.dashboard -> {
                        sectionText.text = "Tu información"
                        navMainFragment.findNavController().navigate(MainNavGraphDirections.toDashboard())
                    }
                    R.id.account -> {
                        sectionText.text = "Tu cuenta"
                        navMainFragment.findNavController().navigate(MainNavGraphDirections.toAccount())
                    }
                    R.id.settings -> {
                        sectionText.text = "Configuración"
                        navMainFragment.findNavController().navigate(MainNavGraphDirections.toSettings())
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
                        logout(requireActivity())
                        root.findNavController().navigate(MainFragmentDirections.toLogin())
                    }
                }

                true
            }
        }
    }

    private fun selectedAvatar(imgId: Int) {
        binding.avatarSelected.setImageResource(imgId)
        headerImage.setImageResource(imgId)
        setIntValue(requireActivity(), "avatar", imgId)
        avatarsDialog.dismiss()
    }

    private fun storyOptions() {
        val storyOptions = StoryOptions.newInstance()
        storyOptions.show(parentFragmentManager, "Options")
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