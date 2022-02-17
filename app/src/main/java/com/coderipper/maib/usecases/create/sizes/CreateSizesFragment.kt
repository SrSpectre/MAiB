package com.coderipper.maib.usecases.create.sizes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.coderipper.maib.databinding.FragmentCreateSizesBinding
import com.coderipper.maib.usecases.create.sizes.adapter.SizesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [CreateSizesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateSizesFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCreateSizesBinding? = null
    private val binding get() = _binding!!
    private val sizes = ArrayList<Int>()

    private lateinit var sizesAdapter: SizesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        // Inflate the layout for this fragment
        _binding = FragmentCreateSizesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sizesAdapter = SizesAdapter(sizes)

        binding.run {
            sizesList.apply {
                setHasFixedSize(false)
                adapter = sizesAdapter
            }

            addSizeButton.setOnClickListener {
                val value = sizeValueInput.text.toString().trim()
                if(value.isNotEmpty()) {
                    sizes.add(value.toInt())
                    sizesAdapter.notifyItemInserted(sizes.size - 1)
                    sizeValueInput.text?.clear()
                }
                else
                    //Snackbar.make(root, "Ingresa un valor", Snackbar.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(), "Ingresa un valor", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment AvatarsSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = CreateSizesFragment()
    }
}