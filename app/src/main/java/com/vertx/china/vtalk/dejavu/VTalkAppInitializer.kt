package com.vertx.china.vtalk.dejavu

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@Suppress("unused")
class VTalkAppInitializer : Initializer<Unit> {
	override fun create(context: Context) {
		configureDi(context)
		configureTimber()
	}

	override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

}

private fun configureDi(context: Context) {
	startKoin {
		androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE) // 暂时解决 koin 在 kt1.6 崩溃的问题
		androidContext(context)
		modules(injectModules)
	}
}

private fun configureTimber() {
	if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
}