package me.tellvivk.messenger

import android.util.DisplayMetrics
import android.view.WindowManager
import me.tellvivk.messenger.helpers.fileHelper.FileHelper
import me.tellvivk.messenger.helpers.fileHelper.FileHelperI
import me.tellvivk.messenger.helpers.imageHelper.ImageHelper
import me.tellvivk.messenger.helpers.imageHelper.ImageHelperI
import me.tellvivk.messenger.helpers.logger.AppLogger
import me.tellvivk.messenger.helpers.logger.LoggerI
import me.tellvivk.messenger.helpers.networkHelper.NetworkHelper
import me.tellvivk.messenger.helpers.networkHelper.NetworkHelperI
import me.tellvivk.messenger.helpers.stringFetcher.AppStringFetcher
import me.tellvivk.messenger.helpers.stringFetcher.StringFetcherI
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

class AppModule {

    companion object {

        private val appModule = module {
            single<LoggerI> { AppLogger(BuildConfig.DEBUG) }
            single<StringFetcherI> { AppStringFetcher(androidContext()) }
            single<NetworkHelperI> { NetworkHelper(androidContext()) }
            single<FileHelperI> { FileHelper(androidContext()) }
            single<ImageHelperI> { ImageHelper() }
            single(name = "screenSize") { (windowManager: WindowManager) ->
                val displayMetrics = DisplayMetrics()

                windowManager.defaultDisplay.getMetrics(displayMetrics)
                Pair(displayMetrics.heightPixels, displayMetrics.widthPixels)
            }



        }



        val modules = listOf(
            appModule
        )
    }

}