package me.tellvivk.messenger.helpers.imageHelper

import android.content.Context
import android.widget.ImageView
import java.io.File

interface ImageHelperI {

    fun loadFromUrl(context: Context, url: String, iv: ImageView)
    fun loadFromFile(context: Context, file: File, iv: ImageView)

}