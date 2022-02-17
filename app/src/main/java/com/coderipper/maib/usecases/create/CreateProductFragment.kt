package com.coderipper.maib.usecases.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentCreateProductBinding
import com.coderipper.maib.databinding.FragmentDashboardBinding
import com.coderipper.maib.databinding.FragmentHomeBinding
import com.coderipper.maib.databinding.FragmentMainBinding
import com.coderipper.maib.usecases.create.colors.CreateColorsFragment
import com.coderipper.maib.usecases.create.sizes.CreateSizesFragment
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [CreateProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProductFragment : Fragment() {
    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            closeButton.setOnClickListener {
                root.findNavController().popBackStack()
            }

            addSizeButton.setOnClickListener {
                val sizesFragment = CreateSizesFragment.newInstance()
                sizesFragment.show(parentFragmentManager, "Sizes Fragment")
            }

            addColorButton.setOnClickListener {
                val colorsFragment = CreateColorsFragment.newInstance()
                colorsFragment.show(parentFragmentManager, "Colors Fragment")
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
        fun newInstance() = CreateProductFragment()
    }
}