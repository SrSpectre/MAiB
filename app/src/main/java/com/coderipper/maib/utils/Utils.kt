package com.coderipper.maib.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*


fun dpToPixels(context: Context, dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

fun pixelsToDp(context: Context, pixels: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.resources.displayMetrics)

fun displayDimens(context: Context): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    val windowsManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowsManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}