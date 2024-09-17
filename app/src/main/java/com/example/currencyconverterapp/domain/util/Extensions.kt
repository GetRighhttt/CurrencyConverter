package com.example.currencyconverterapp.domain.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Extensions {

    fun materialDialog(
        context: Context,
        title: String,
        message: String
    ) = object : MaterialAlertDialogBuilder(context) {
        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }
}