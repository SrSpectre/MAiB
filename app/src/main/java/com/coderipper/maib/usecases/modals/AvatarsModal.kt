package com.coderipper.maib.usecases.modals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.usecases.signin.adapter.AvatarAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


private val avatarsIds = arrayOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6)

fun createAvatarsModal(context: Context, selectedAvatar: (imgId: Int) -> Unit): AlertDialog {
    val dialogBuilder = MaterialAlertDialogBuilder(context)
    val dialogView = LayoutInflater.from(context)
        .inflate(R.layout.avatar_dialog, null, false)
    return launchAvatarDialog(dialogBuilder, dialogView, selectedAvatar)
}

private fun launchAvatarDialog(
    dialogBuilder: MaterialAlertDialogBuilder,
    dialogView: View,
    selectedAvatar: (imgId: Int) -> Unit
): AlertDialog {
    val avatarList = dialogView.findViewById<RecyclerView>(R.id.avatar_list)

    avatarList.apply {
        setHasFixedSize(false)
        adapter = AvatarAdapter(avatarsIds, selectedAvatar)
    }

    return dialogBuilder.setView(dialogView)
        .setTitle("Avatar")
        .setMessage("Selecciona un avatar")
        .show()
}