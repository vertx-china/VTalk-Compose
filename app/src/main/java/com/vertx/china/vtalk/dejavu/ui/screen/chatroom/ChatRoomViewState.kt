package com.vertx.china.vtalk.dejavu.ui.screen.chatroom

import com.vertx.china.vtalk.dejavu.data.pojo.ChatRoom
import com.vertx.china.vtalk.dejavu.data.pojo.Message

data class ChatRoomViewState(
	val chatRoom: ChatRoom? = null,
	val messages: List<Message>? = null
) {
	companion object {
		val Empty = ChatRoomViewState(ChatRoom.Preview)
		val Preview = ChatRoomViewState(ChatRoom.Preview)
	}
}