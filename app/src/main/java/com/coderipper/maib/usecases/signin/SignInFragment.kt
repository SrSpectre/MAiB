package com.coderipper.maib.usecases.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentLoginBinding
import com.coderipper.maib.databinding.FragmentSignInBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.login.LoginFragmentDirections
import com.coderipper.maib.utils.setIntValue
import com.coderipper.maib.utils.setStringValue
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val imgIds = arrayOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6)
    private var avatarId = imgIds[0]

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

            }

            loginFab.setOnClickListener {
                registerUser()
                root.findNavController().navigate(SignInFragmentDirections.toHome())
            }
        }
    }

    private fun registerUser() {
        binding.run {
            val name = nameInput.text.toString().trim()
            val last = nameInput.text.toString().trim()
            val phone = nameInput.text.toString().trim()
            val email = nameInput.text.toString().trim()
            val pswd = nameInput.text.toString().trim()
            val rpswd = nameInput.text.toString().trim()

            if(name.isNotEmpty() && last.isNotEmpty() && phone.isNotEmpty() &&
                email.isNotEmpty() && pswd.isNotEmpty() && rpswd.isNotEmpty()) {
                if(pswd == rpswd) {
                    val user = User(
                        name = name,
                        last = last,
                        phone = phone,
                        email = email,
                        password = pswd,
                        avatar = avatarId
                    )

                    setStringValue(requireActivity(), "email", email)
                    setStringValue(requireActivity(), "name", "$name $last")
                    setIntValue(requireActivity(), "avatar", avatarId)
                } else
                    Snackbar.make(root, "Las contraseñas son diferentes", Snackbar.LENGTH_SHORT).show()
            } else
                Snackbar.make(root, "Ingresa todos los datos", Snackbar.LENGTH_SHORT).show()
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