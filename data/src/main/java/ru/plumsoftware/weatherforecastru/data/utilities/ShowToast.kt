package ru.plumsoftware.weatherforecastru.data.utilities

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, message: String) {
    if (message.length >= 30)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    else
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
