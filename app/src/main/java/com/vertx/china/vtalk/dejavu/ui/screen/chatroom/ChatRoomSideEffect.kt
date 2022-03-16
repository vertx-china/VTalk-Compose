package com.vertx.china.vtalk.dejavu.ui.screen.chatroom

import androidx.compose.runtime.Composable
import com.vertx.china.vtalk.dejavu.ui.dialog.ChatRoomConfigDialog

sealed class ChatRoomSideEffect {
	object FillInChatRoomInfoByManually : ChatRoomSideEffect()
}


@Composable
fun ChatRoomSideEffectHandler(
	it: ChatRoomSideEffect?,
	onConfigComplete: () -> Unit
) {
	when (it) {
		ChatRoomSideEffect.FillInChatRoomInfoByManually -> {
			ChatRoomConfigDialog(
				onConfigComplete = onConfigComplete
			)
		}
		null -> {}
	}
}