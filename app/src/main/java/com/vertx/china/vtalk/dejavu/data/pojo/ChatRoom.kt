package com.vertx.china.vtalk.dejavu.data.pojo

data class ChatRoom(
	val id: String,
	val title: String
) {
	companion object {
		val Preview = ChatRoom("1234", "Vert.x TNB Group")
	}
}
