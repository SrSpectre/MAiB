package com.coderipper.maib.usecases.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.databinding.FragmentProfileBinding
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.usecases.profile.adapter.MoreArtistsAdapter
import com.coderipper.maib.usecases.profile.adapter.ProductsAdapter
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getBooleanValue
import com.coderipper.maib.utils.getLongValue
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activeId = getLongValue(requireActivity(), "id")
        val userId = args.userId
        val user = DataBase.getUserById(userId)
        val visible = getBooleanValue(requireContext(), "accounts_recomm")

        binding.run {
            if(user != null) {
                avatarSelected.setImageResource(user.avatar)
                nameText.text = user.name
                descriptionText.text = user.description
                contactText.text = "${user.phone}\n${user.email}"
                followingText.text = DataBase.getFollowersCount(userId).toString()
                rateText.text = user.rate.toString()

                productsList.apply {
                    setHasFixedSize(false)
                    adapter = ProductsAdapter(activeId, DataBase.getProductsByUserId(userId), root.findNavController())
                }

                moreArtistText.isVisible = visible

                moreArtistList.apply {
                    setHasFixedSize(false)
                    adapter = MoreArtistsAdapter(DataBase.getMoreArtists(activeId, userId), root.findNavController())
                    isVisible = visible
                }

                profileToolbar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.follow -> {
                            if(DataBase.getIsFollowing(userId, activeId)) {
                                DataBase.removeFollowing(userId, activeId)
                                Snackbar.make(root, "Ya no sigues a ${user.name}", Snackbar.LENGTH_SHORT).show()
                            } else {
                                DataBase.setFollowing(userId, activeId)
                                Snackbar.make(root, "Ahora sigues a ${user.name}", Snackbar.LENGTH_SHORT).show()
                            }
                            true
                        }
                        else -> false
                    }
                }
            } else
                root.findNavController().popBackStack()

            profileToolbar.setNavigationOnClickListener {
                root.findNavController().popBackStack()
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
        fun newInstance() = ProfileFragment()
    }
}