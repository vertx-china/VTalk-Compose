@file:Suppress("ObjectPropertyName")

package com.vertx.china.vtalk.dejavu.data.network

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import com.vertx.china.vtalk.dejavu.base.globalfunc.debugLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.Duration

private val _okHttpClient = OkHttpClient.Builder()
//	.callTimeout(Duration.ofSeconds(60))
	.addInterceptor(HttpLoggingInterceptor {
		debugLog("Okhttp: $it")
	}.apply { level = HttpLoggingInterceptor.Level.BODY })
	.build()

//private val _scarlet =
//	Scarlet.Builder()
//		.webSocketFactory(_okHttpClient.newWebSocketFactory("wss://123.123.123.123:1234"))
//		.addMessageAdapterFactory(GsonMessageAdapter.Factory())
//		.addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
//		.addStreamAdapterFactory(RxJava2StreamAdapterFactory())
//		.build()

fun buildScarlet(scarletConfig: ScarletConfig) = Scarlet.Builder()
	.webSocketFactory(_okHttpClient.newWebSocketFactory(scarletConfig.url))
	.addMessageAdapterFactory(GsonMessageAdapter.Factory())
	.addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
	.addStreamAdapterFactory(RxJava2StreamAdapterFactory())
	.build()


data class ScarletConfig(
	val scheme: String,
	val host: String,
	val port: String? = null
) {
	val url: String
		get() = "$scheme://$host" + if (!port.isNullOrBlank()) ":$port" else ""
}