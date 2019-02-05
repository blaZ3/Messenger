package me.tellvivk.messenger.helpers.fileHelper

import android.graphics.Bitmap
import io.reactivex.Single

interface FileHelperI {
    fun getBitmapFromFile(filePath: String, screenW: Int, screenH: Int): Single<Pair<String, Bitmap>>
}