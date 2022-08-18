package com.coderipper.maib.usecases.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentProfileBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.usecases.profile.adapter.MoreArtistsAdapter
import com.coderipper.maib.usecases.profile.adapter.ProductsAdapter
import com.coderipper.maib.utils.getBooleanValue
import com.coderipper.maib.utils.getStringValue
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

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
        val activeId = getStringValue(requireActivity(), "id")!!
        val userId = args.userId
        val visible = getBooleanValue(requireContext(), "accounts_recomm")

        binding.run {
            db.collection("users").document(userId).get().addOnSuccessListener { doc ->
                val user = doc.toObject<User>()

                if(user != null) {
                    avatarSelected.setImageResource(user.avatar)
                    nameText.text = user.name
                    descriptionText.text = user.description
                    contactText.text = "${user.phone}\n${user.email}"
                    followingText.text = user.followers.size.toString()
                    rateText.text = user.rate.toString()

                    productsList.apply {
                        setHasFixedSize(false)
                        adapter = ProductsAdapter(activeId, user.products, root.findNavController())
                    }

                    moreArtistText.isVisible = visible

                    db.collection("users").get().addOnSuccessListener { data ->
                        if (!data.isEmpty) {
                            val users = data.toObjects<User>()
                            val currentUser = data.first { it.id == activeId }.toObject<User>()
                            val filtered = users.filter { it.email != user.email && it.email != currentUser.email }

                            moreArtistList.apply {
                                setHasFixedSize(false)
                                adapter = MoreArtistsAdapter(filtered, root.findNavController())
                                isVisible = visible
                            }
                        }
                    }

                    profileToolbar.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.follow -> {
                                db.collection("users").document(activeId).get().addOnSuccessListener {
                                    val activeUser = doc.toObject<User>()
                                    if (activeUser != null) {
                                        if (activeUser.following.contains(userId)) {
                                            activeUser.following.remove(userId)
                                            db.collection("users").document(activeId).update("following", activeUser.following)
                                            Snackbar.make(root, "Ya no sigues a ${user.name}", Snackbar.LENGTH_SHORT).show()
                                        } else {
                                            activeUser.following.add(userId)
                                            db.collection("users").document(activeId).update("following", activeUser.following)
                                            Snackbar.make(root, "Ahora sigues a ${user.name}", Snackbar.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                true
                            }
                            else -> false
                        }
                    }

                    avatarSelected.setOnClickListener {
                        db.collection("users").document(userId).get().addOnSuccessListener { data ->
                            val user = data.toObject<User>()
                            if (user != null) {
                                if(user.story != null)
                                    findNavController().navigate(MainFragmentDirections.toVideo(user.story!!.uri, false))
                            }
                        }
                    }
                } else
                    root.findNavController().popBackStack()
            }

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
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}