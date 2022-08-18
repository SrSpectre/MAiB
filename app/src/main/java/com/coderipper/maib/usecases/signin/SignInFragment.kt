package com.coderipper.maib.usecases.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentSignInBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.modals.createAvatarsModal
import com.coderipper.maib.utils.setIntValue
import com.coderipper.maib.utils.setStringValue
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    private var avatarId = R.drawable.avatar1

    private lateinit var avatarsDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            avatarBtn.setOnClickListener {
                avatarsDialog = createAvatarsModal(requireContext(), ::selectedAvatar)
            }

            loginFab.setOnClickListener {
                registerUser()
            }
        }
    }

    private fun selectedAvatar(imgId: Int) {
        avatarId = imgId
        binding.avatarSelected.setImageResource(imgId)
        avatarsDialog.dismiss()
    }

    private fun registerUser() {
        binding.run {
            val name = nameInput.text.toString().trim()
            val last = lastInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val pswd = passwordInput.text.toString().trim()
            val rpswd = repeatInput.text.toString().trim()

            if(name.isNotEmpty() && last.isNotEmpty() && phone.isNotEmpty() &&
                email.isNotEmpty() && pswd.isNotEmpty() && rpswd.isNotEmpty()) {
                if(pswd == rpswd) {
                    val auth = FirebaseAuth.getInstance()

                    auth.createUserWithEmailAndPassword(email, pswd).addOnSuccessListener {
                        val uid = it.user!!.uid
                        val user = User(
                            name = name,
                            last = last,
                            phone = phone,
                            email = email,
                            password = pswd,
                            avatar = avatarId
                        )

                        db.collection("users").document(uid).set(user).addOnSuccessListener {
                            setStringValue(requireActivity(), "id", uid)
                            setStringValue(requireActivity(), "email", email)
                            setStringValue(requireActivity(), "uname", name)
                            setStringValue(requireActivity(), "name", "$name $last")
                            setIntValue(requireActivity(), "avatar", avatarId)
                            root.findNavController().popBackStack()
                        }.addOnFailureListener {
                            Snackbar.make(root, "Error al registrar usuario", Snackbar.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Snackbar.make(root, "Error al registrar usuario", Snackbar.LENGTH_SHORT).show()
                    }

                    //DataBase.setUser(user)
                } else
                    Snackbar.make(root, "Las contraseñas son diferentes", Snackbar.LENGTH_SHORT).show()
            } else
                Snackbar.make(root, "Campos vacios", Snackbar.LENGTH_SHORT).show()
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
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}