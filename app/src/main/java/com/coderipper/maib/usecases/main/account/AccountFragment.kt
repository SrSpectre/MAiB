package com.coderipper.maib.usecases.main.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentAboutBinding
import com.coderipper.maib.databinding.FragmentAccountBinding
import com.coderipper.maib.databinding.FragmentHelpBinding
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getLongValue
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

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
        val userId = getLongValue(requireActivity(), "id")

        loadTextFields(userId)
        binding.updateFab.setOnClickListener {
            updateData(userId)
        }
    }

    private fun loadTextFields(userId: Long) {
        val user = DataBase.getUserById(userId)

        binding.run {
            aboutInput.apply {
                text?.clear()
                hint = user?.description
            }
            phoneInput.text?.clear()
            emailInput.text?.clear()
            passwordInput.text?.clear()
            phoneInputLayout.hint = user?.phone
            emailInputLayout.hint = user?.email
        }
    }

    private fun updateData(userId: Long) {
        binding.run {
            val description = aboutInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if(description.isNotEmpty() || phone.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty()) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Alerta")
                    .setMessage("¿Estas seguro de actualizar la información?")
                    .setNegativeButton("Cancelar") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Si, continuar") { dialog, which ->
                        if(description.isNotEmpty())
                            DataBase.updateDescription(userId, description)
                        if(phone.isNotEmpty())
                            DataBase.updatePhone(userId, phone)
                        if(email.isNotEmpty())
                            DataBase.updateEmail(userId, email)
                        if(password.isNotEmpty())
                            DataBase.updatePassword(userId, password)
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