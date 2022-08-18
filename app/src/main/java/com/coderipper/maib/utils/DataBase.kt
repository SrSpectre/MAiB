package com.coderipper.maib.utils

import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.coderipper.maib.R
import com.coderipper.maib.models.domain.*
import com.coderipper.maib.models.session.User

object DataBase {
    // Fake viewmodel
    val sizes = MutableLiveData<ArrayList<Int>>()
    val colors = MutableLiveData<ArrayList<Triple<Int, Int, Int>>>()
}