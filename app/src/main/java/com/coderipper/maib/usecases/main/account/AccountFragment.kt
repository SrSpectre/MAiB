package com.coderipper.maib.usecases.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coderipper.maib.databinding.FragmentAccountBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.utils.getStringValue
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = getStringValue(requireActivity(), "id")!!

        loadTextFields(userId)
        binding.updateFab.setOnClickListener {
            updateData(userId)
        }
    }

    private fun loadTextFields(userId: String) {
        binding.run {
            db.collection("users").document(userId).get().addOnSuccessListener { data ->
                if (data.exists()) {
                    val user = data.toObject<User>()
                    if (user != null) {
                        aboutInput.apply {
                            text?.clear()
                            hint = user.description
                        }
                        phoneInput.text?.clear()
                        emailInput.text?.clear()
                        passwordInput.text?.clear()
                        phoneInputLayout.hint = user.phone
                        emailInputLayout.hint = user.email
                    }
                    else
                        Snackbar.make(root, "Error con cuenta", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateData(userId: String) {
        binding.run {
            val description = aboutInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if(description.isNotEmpty() || phone.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty()) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Alerta")
                    .setMessage("¿Estas seguro de actualizar la información?")
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Si, continuar") { dialog, _ ->
                        if(description.isNotEmpty())
                            db.collection("users").document(userId).update("description", description)
                        if(phone.isNotEmpty())
                            db.collection("users").document(userId).update("phone", phone)
                        if(email.isNotEmpty())
                            db.collection("users").document(userId).update("email", email)
                        if(password.isNotEmpty())
                            db.collection("users").document(userId).update("password", password)
                        loadTextFields(userId)
                        dialog.dismiss()
                        Snackbar.make(root, "Información actualizada", Snackbar.LENGTH_SHORT).setAnchorView(updateFab).show()
                    }
                    .show()
            }
            else Snackbar.make(root, "Ningun dato ha sido cambiado", Snackbar.LENGTH_SHORT).setAnchorView(updateFab).show()
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
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}