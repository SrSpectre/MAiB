package com.coderipper.maib.usecases.main.stories

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FragmentVideoBinding
import com.coderipper.maib.models.domain.Story
import com.coderipper.maib.models.session.User
import com.coderipper.maib.utils.getStringValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

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
                if (controls)
                    setMediaController(mediaCollection)
                else
                    setOnCompletionListener {
                        findNavController().popBackStack()
                    }
                setVideoURI(uri)
                start()
            }

            if (!controls && !fromUser) editFab.hide()
            if (fromUser) {
                editFab.text = "Eliminar"
            }
            editFab.setOnClickListener {
                val userId = getStringValue(requireActivity(), "id")!!
                val doc = db.collection("users").document(userId)
                doc.get().addOnSuccessListener { data ->
                    val user = data.toObject<User>()
                    if (user != null) {
                        if (!fromUser) {
                            val progressDialog = ProgressDialog(requireContext())
                            progressDialog.setMessage("Subiendo video...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            val location = "videos/${System.nanoTime()}"
                            val ref = storage.getReference(location)
                            ref.putFile(uri).addOnSuccessListener { task ->
                                task.storage.downloadUrl.addOnSuccessListener { uploadedUri ->
                                    doc.update("story", Story(location, uploadedUri.toString()))
                                }
                                if (progressDialog.isShowing)
                                    progressDialog.dismiss()
                                val channel = NotificationChannel("channel1", "Notf channel", NotificationManager.IMPORTANCE_DEFAULT)
                                channel.description = "description"
                                val notificationManager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                                notificationManager.createNotificationChannel(channel)

                                val builder = NotificationCompat.Builder(requireContext(), "channel1")
                                    .setSmallIcon(R.drawable.notification_icon)
                                    .setContentTitle("Wooow!")
                                    .setContentText("Seguro amaran tu historia!")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                                with(NotificationManagerCompat.from(requireContext())) {
                                    notify(1, builder.build())
                                }
                                findNavController().popBackStack()
                            }.addOnFailureListener {
                                println(it.message)
                                if (progressDialog.isShowing)
                                    progressDialog.dismiss()
                            }
                        } else {
                            val story = user.story
                            if(story != null) {
                                val storageRef = storage.reference
                                storageRef.child(story.id).delete()
                                db.collection("users").document(userId).update("story", null)
                            }
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoFragment()
    }
}