package com.vertx.china.vtalk.dejavu.data.api

import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.vertx.china.vtalk.dejavu.data.pojo.TNBMessage
import io.reactivex.Flowable

interface TreeNewBeeServer {

	@Receive
	fun observeTNBMessage(): Flowable<TNBMessage>

	@Send
	fun sendTNBMessage(message: TNBMessage): Boolean

}