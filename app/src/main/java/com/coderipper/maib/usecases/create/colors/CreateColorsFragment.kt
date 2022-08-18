package com.coderipper.maib.usecases.create.colors

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coderipper.maib.databinding.FragmentCreateColorsBinding
import com.coderipper.maib.usecases.create.colors.adapter.ColorsAdapter
import com.coderipper.maib.utils.DataBase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * A simple [Fragment] subclass.
 * Use the [CreateColorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateColorsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCreateColorsBinding? = null
    private val binding get() = _binding!!
    private val colors = ArrayList<Triple<Int, Int, Int>>()

    private lateinit var colorsAdapter: ColorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        // Inflate the layout for this fragment
        _binding = FragmentCreateColorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorsAdapter = ColorsAdapter(colors)

        binding.run {
            redValueSlider.addOnChangeListener { slider, value, fromUser ->
                redValueInput.setText("${value.toInt()}")
                setColor()
            }

            greenValueSlider.addOnChangeListener { slider, value, fromUser ->
                greenValueInput.setText("${value.toInt()}")
                setColor()
            }

            blueValueSlider.addOnChangeListener { slider, value, fromUser ->
                blueValueInput.setText("${value.toInt()}")
                setColor()
            }

            colorsList.apply {
                setHasFixedSize(false)
                adapter = colorsAdapter
            }

            addColorButton.setOnClickListener {
                colors.add(getRGB())
                colorsAdapter.notifyItemInserted(colors.size - 1)
            }

            addColorsButton.setOnClickListener {
                DataBase.colors.value = colors
                dismiss()
            }
        }
    }

    private fun getRGB(): Triple<Int, Int, Int> {
        binding.run {
            val red = redValueSlider.value.toInt()
            val green = greenValueSlider.value.toInt()
            val blue = blueValueSlider.value.toInt()
            return Triple(red, green, blue)
        }
    }

    private fun setColor() {
        val (red, green, blue) = getRGB()
        binding.colorValueImage.setBackgroundColor(Color.rgb(red, green, blue))
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
        fun newInstance() = CreateColorsFragment()
    }
}