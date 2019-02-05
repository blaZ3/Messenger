package me.tellvivk.messenger.helpers.networkHelper

import android.content.Context
import android.net.ConnectivityManager
import me.tellvivk.messenger.helpers.networkHelper.NetworkHelperI


class NetworkHelper(private val context: Context): NetworkHelperI {

    override fun isConncected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}