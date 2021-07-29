package com.mattkula.template

import android.content.Context
import com.mattkula.template.data.remote.GeckoCoinApi
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

/**
 * A very simple global singleton dependency graph.
 *
 * For a real app, you would use something like Hilt/Dagger instead.
 */
object Graph {
    lateinit var okHttpClient: OkHttpClient

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val api: GeckoCoinApi by lazy {
        retrofit.create(GeckoCoinApi::class.java)
    }

    private val mainDispatcher: CoroutineDispatcher get() = Dispatchers.Main
    private val ioDispatcher: CoroutineDispatcher get() = Dispatchers.IO

    fun provide(context: Context) {
        okHttpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http_cache"), 20 * 1024 * 1024))
            .apply {
                if (BuildConfig.DEBUG) eventListenerFactory(LoggingEventListener.Factory())
            }
            .build()
    }
}
