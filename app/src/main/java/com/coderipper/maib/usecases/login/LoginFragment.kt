package com.coderipper.maib.usecases.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coderipper.maib.databinding.FragmentLoginBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.utils.getStringValue
import com.coderipper.maib.utils.setIntValue
import com.coderipper.maib.utils.setStringValue
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            if(!getStringValue(requireActivity(), "email").isNullOrEmpty())
                root.findNavController().navigate(LoginFragmentDirections.toHome())

            signInText.setOnClickListener {
                root.findNavController().navigate(LoginFragmentDirections.toSignIn())
            }

            loginFab.setOnClickListener {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        binding.run {
            val email = emailInput.text.toString().trim()
            val pswd = passwordInput.text.toString().trim()

            if(email.isNotEmpty() && pswd.isNotEmpty()) {
                //val user = DataBase.loginUser(email, pswd)
                val auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, pswd).addOnSuccessListener { authIt ->
                    val uid = authIt.user!!.uid

                    db.collection("users").document(uid).get().addOnSuccessListener { data ->
                        if(data.exists()) {
                            val user = data.toObject<User>()
                            if (user != null) {
                                setStringValue(requireActivity(), "id", uid)
                                setStringValue(requireActivity(), "email", user.email)
                                setStringValue(requireActivity(), "uname", user.name)
                                setStringValue(requireActivity(), "name", "${user.name} ${user.last}")
                                setIntValue(requireActivity(), "avatar", user.avatar)
                                root.findNavController().navigate(LoginFragmentDirections.toHome())
                            } else
                                Snackbar.make(root, "Error con cuenta", Snackbar.LENGTH_SHORT).show()
                        } else
                            Snackbar.make(root, "Usuario no registrado", Snackbar.LENGTH_SHORT)
                                .setAction("¡Unete!") {
                                    root.findNavController().navigate(LoginFragmentDirections.toSignIn())
                                }.show()
                    }
                }.addOnFailureListener {
                    Snackbar.make(root, "Información erronea", Snackbar.LENGTH_SHORT).show()
                }
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}