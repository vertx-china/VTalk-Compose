package com.vertx.china.vtalk.dejavu.data.datastore

import org.koin.dsl.module

val datastoreModule = module {
	single { DeviceDataStore(get()) }
}