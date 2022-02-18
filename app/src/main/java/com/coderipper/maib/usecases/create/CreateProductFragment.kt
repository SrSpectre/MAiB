package com.coderipper.maib.usecases.create

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.coderipper.maib.databinding.FragmentCreateProductBinding
import com.coderipper.maib.models.domain.Categories
import com.coderipper.maib.models.domain.ColorEntity
import com.coderipper.maib.models.domain.Size
import com.coderipper.maib.usecases.create.adapter.ImageAdapter
import com.coderipper.maib.usecases.create.colors.CreateColorsFragment
import com.coderipper.maib.usecases.create.colors.adapter.ColorsAdapter
import com.coderipper.maib.usecases.create.sizes.CreateSizesFragment
import com.coderipper.maib.usecases.create.sizes.adapter.SizesAdapter
import com.coderipper.maib.usecases.profile.ProfileFragmentArgs
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getLongValue
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 * Use the [CreateProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProductFragment : Fragment() {
    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!

    private val args: CreateProductFragmentArgs by navArgs()

    private val imageList = arrayListOf<Uri?>(null)
    private val sizes = ArrayList<Int>()
    private val colors = ArrayList<Triple<Int, Int, Int>>()

    private lateinit var imagesAdapter: ImageAdapter
    private var currentCategory by Delegates.notNull<Int>()

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
        imagesAdapter = ImageAdapter(this, imageList, ::removeImage)
        val sizesAdapter = SizesAdapter(sizes)
        val colorsAdapter = ColorsAdapter(colors)
        currentCategory = args.category

        DataBase.sizes.observe(viewLifecycleOwner) {
            sizes.addAll(it)
            sizesAdapter.notifyDataSetChanged()
        }

        DataBase.colors.observe(viewLifecycleOwner) {
            colors.addAll(it)
            colorsAdapter.notifyDataSetChanged()
        }

        binding.run {
            if(currentCategory != Categories.CLOSET.ordinal) {
                sizesText.isVisible = false
                sizesList.isVisible = false
                addSizeButton.isVisible = false
                colorsText.isVisible = false
                colorsList.isVisible = false
                addColorButton.isVisible = false
            }

            closeButton.setOnClickListener {
                root.findNavController().popBackStack()
            }

            imagesList.apply {
                setHasFixedSize(false)
                adapter = imagesAdapter
            }

            sizesList.apply {
                setHasFixedSize(false)
                adapter = sizesAdapter
            }

            colorsList.apply {
                setHasFixedSize(false)
                adapter = colorsAdapter
            }

            addSizeButton.setOnClickListener {
                val sizesFragment = CreateSizesFragment.newInstance()
                sizesFragment.show(parentFragmentManager, "Sizes Fragment")
            }

            addColorButton.setOnClickListener {
                val colorsFragment = CreateColorsFragment.newInstance()
                colorsFragment.show(parentFragmentManager, "Colors Fragment")
            }

            createProductFab.setOnClickListener {
                createProduct()
            }
        }
    }

    private fun removeImage(uri: Uri) {
        val index = imageList.indexOf(uri)
        imageList.remove(uri)
        imagesAdapter.notifyItemRemoved(index)
    }

    private fun createProduct() {
        val userId = getLongValue(requireActivity(), "id")
        binding.run {
            val name = nameText.text.toString().trim()
            val price = priceText.text.toString().trim()
            val description = descriptionText.text.toString().trim()

            if(name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && imageList.size > 1) {
                val images = arrayListOf<Uri>()
                imageList.forEach {
                    it?.let { images.add(it) }
                }

                if(currentCategory == Categories.CLOSET.ordinal && sizesList.isEmpty() && colorsList.isEmpty()) {
                    Snackbar.make(root, "Faltan tamaños o colores", Snackbar.LENGTH_SHORT).show()
                    return
                }

                if(currentCategory == Categories.CLOSET.ordinal) {
                    val sizesList = ArrayList<Size>()
                    sizes.forEach { sizesList.add(Size(size = it)) }

                    val colorsList = ArrayList<ColorEntity>()
                    colors.forEach { colorsList.add(ColorEntity(color = Color.rgb(it.first, it.second, it.third))) }
                    DataBase.setProduct(
                        userId,
                        name,
                        price,
                        description,
                        currentCategory,
                        images,
                        sizesList,
                        colorsList
                    )
                } else {
                    DataBase.setProduct(
                        userId,
                        name,
                        price,
                        description,
                        currentCategory,
                        images
                    )
                }
                root.findNavController().popBackStack()
            } else {
                Snackbar.make(root, "Falta información", Snackbar.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(requireContext(), "Si", Toast.LENGTH_SHORT).show()
        if(resultCode == Activity.RESULT_OK && requestCode == 0) {
            imageList.add(data?.data)
            if (imageList.size > 4) {
                imageList.removeAt(0)
                imagesAdapter.notifyItemRemoved(0)
            }
            imagesAdapter.notifyItemInserted(imageList.size - 1)
        }
    }
}