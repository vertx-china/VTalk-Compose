package com.vertx.china.vtalk.dejavu

import com.vertx.china.vtalk.dejavu.data.datastore.datastoreModule
import com.vertx.china.vtalk.dejavu.ui.screen.screenViewModelModule
import org.koin.core.module.Module

val injectModules: List<Module> = listOf(
	screenViewModelModule,
	datastoreModule,
)