package com.coderipper.maib.usecases.main.stories

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentStoryOptionsBinding
import com.coderipper.maib.databinding.StoryOptionsItemBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.utils.getStringValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class StoryOptions : BottomSheetDialogFragment() {

    private var _binding: FragmentStoryOptionsBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = getStringValue(requireActivity(), "id")!!
        val doc = db.collection("users").document(userId)
        doc.get().addOnSuccessListener { data ->
            val user = data.toObject<User>()
            if (user != null) {
                val story = user.story
                binding.list.layoutManager = LinearLayoutManager(context)
                binding.list.adapter = ItemAdapter(if (story == null) 1 else 2)
            }
        }
    }

    private inner class ViewHolder(binding: StoryOptionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val text: TextView = binding.text
    }

    private inner class ItemAdapter internal constructor(private val mItemCount: Int) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                StoryOptionsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position == 0) {
                holder.text.text = "Crear historia"
                holder.text.setOnClickListener {
                    val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    if (intent.resolveActivity(requireActivity().packageManager) != null)
                        startActivityForResult(intent, 123)
                }
            }

            if (position == 1) {
                holder.text.text = "Ver historia"
                holder.text.setOnClickListener {
                    val userId = getStringValue(requireActivity(), "id")!!
                    val doc = db.collection("users").document(userId)
                    doc.get().addOnSuccessListener { data ->
                        val user = data.toObject<User>()
                        if (user != null) {
                            val story = user.story
                            story?.let { toVideo(it.uri, controls = false, fromUser = true) }
                        }
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return mItemCount
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK)
            toVideo(data?.data.toString(), true, false)
    }

    private fun toVideo(videoUri: String, controls: Boolean, fromUser: Boolean) {
        val nav = requireActivity().findNavController(R.id.nav_host_fragment)
        nav.navigate(MainFragmentDirections.toVideo(videoUri, controls, fromUser))
        dismiss()
    }

    companion object {
        fun newInstance(): StoryOptions = StoryOptions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}