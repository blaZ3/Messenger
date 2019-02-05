package me.tellvivk.messenger.helpers.fileHelper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Single


class FileHelper(context: Context): FileHelperI {

    override fun getBitmapFromFile(filePath: String, screenW: Int, screenH: Int): Single<Pair<String, Bitmap>> {
        return Single.create { emitter ->
            val bmOptions = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
                BitmapFactory.decodeFile(filePath, this)
                val photoW: Int = outWidth
                val photoH: Int = outHeight
                val scaleFactor: Int = Math.min(photoW / screenW, photoH / screenH)
                inJustDecodeBounds = false
                inSampleSize = scaleFactor
                inPurgeable = true
            }
            BitmapFactory.decodeFile(filePath, bmOptions)?.also { bitmap ->
                emitter.onSuccess(Pair(filePath, bitmap))
            }
        }
    }

}