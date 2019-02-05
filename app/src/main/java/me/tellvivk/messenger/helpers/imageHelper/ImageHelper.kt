package me.tellvivk.messenger.helpers.imageHelper

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File
import android.annotation.SuppressLint
import com.bumptech.glide.request.RequestOptions
import me.tellvivk.messenger.R
import me.tellvivk.messenger.helpers.imageHelper.ImageHelperI


class ImageHelper: ImageHelperI {
    private val thumbNailScale = 0.5f
    private val fileThumbNailScale = 0.2f

    @SuppressLint("CheckResult")
    override fun loadFromUrl(context: Context, url: String, iv: ImageView) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.place_holder)
        requestOptions.error(R.drawable.place_holder)

        Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .thumbnail(thumbNailScale)
            .into(iv)
    }

    @SuppressLint("CheckResult")
    override fun loadFromFile(context: Context, file: File, iv: ImageView) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.place_holder)
        requestOptions.error(R.drawable.place_holder)

        Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
            .load(Uri.fromFile(file))
            .thumbnail(fileThumbNailScale)
            .into(iv)
    }
}