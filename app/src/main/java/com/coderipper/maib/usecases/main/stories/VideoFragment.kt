package com.coderipper.maib.usecases.main.stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coderipper.maib.databinding.FragmentVideoBinding
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getLongValue

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    private val args: VideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri = args.uri.toUri()
        val controls = args.controls
        val fromUser = args.fromUser
        val mediaCollection = MediaController(requireContext())

        binding.run {
            videoContainer.run {
                mediaCollection.setAnchorView(this)
                if (controls) setMediaController(mediaCollection)
                setVideoURI(uri)
                start()
            }

            if (!controls && !fromUser) editFab.hide()
            if (fromUser) {
                editFab.text = "Eliminar"
            }
            editFab.setOnClickListener {
                if (!fromUser) {
                    val userId = getLongValue(requireActivity(), "id")
                    DataBase.updateStory(userId, uri.toString())
                } else {
                    val userId = getLongValue(requireActivity(), "id")
                    DataBase.updateStory(userId, "")
                }
                findNavController().popBackStack()
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
         * @return A new instance of fragment VideoFragment.
         */
        @JvmStatic
        fun newInstance() = VideoFragment()
    }
}