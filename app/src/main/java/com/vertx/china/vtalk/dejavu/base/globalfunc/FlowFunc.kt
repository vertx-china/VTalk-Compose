@file:Suppress("FunctionName")

package com.vertx.china.vtalk.dejavu.base.globalfunc

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

fun <T> SideEffectChannel() =
	Channel<T>(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)