package com.example.weatherappflow.utils

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.Interceptor

class FlipperInitializer(private val application: Application) {

    private val inspectorPlugin: InspectorFlipperPlugin by lazy {
        InspectorFlipperPlugin(
            application,
            DescriptorMapping.withDefaults()
        )
    }
    private val networkPlugin: NetworkFlipperPlugin by lazy { NetworkFlipperPlugin() }
    val networkInterceptor: Interceptor? by lazy { FlipperOkhttpInterceptor(networkPlugin) }

    fun initialize() {
        SoLoader.init(application, false)
        AndroidFlipperClient.getInstance(application).apply {
            addPlugin(inspectorPlugin)
            addPlugin(networkPlugin)
        }.start()

    }
}

