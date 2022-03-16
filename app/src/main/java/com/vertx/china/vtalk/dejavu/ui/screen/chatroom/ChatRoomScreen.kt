package com.vertx.china.vtalk.dejavu.ui.screen.chatroom

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.vertx.china.vtalk.dejavu.base.globalfunc.rememberFlowWithLifecycle
import com.vertx.china.vtalk.dejavu.base.globalfunc.rememberStateWithLifecycle
import com.vertx.china.vtalk.dejavu.ui.screen.chatroom.conversation.ConversationContent
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ChatRoomScreen(
	args: Bundle?,
	openProfile: (userId: String) -> Unit,
) {
	ChatRoomScreen(
		chatRoomViewModel = getViewModel(parameters = { parametersOf(args) }),
		openProfile = openProfile
	)
}

@Composable
private fun ChatRoomScreen(
	chatRoomViewModel: ChatRoomViewModel,
	openProfile: (userId: String) -> Unit,
) {

	val chatRoomViewState by rememberStateWithLifecycle(chatRoomViewModel.chatRoomViewStateFlow)

	val chatRoomSideEffect by rememberFlowWithLifecycle(chatRoomViewModel.chatRoomSideEffectFlow)
		.collectAsState(initial = null)

	ConversationContent(
		chatRoomViewState = chatRoomViewState,
		openProfile = openProfile,
		onSendNewMessage = chatRoomViewModel::onSendMessage,
		modifier = Modifier.navigationBarsPadding(bottom = false)
	)

	val chatRoomConfigComplete = { chatRoomViewModel.tryToConfigureTempChatRoom() }
	ChatRoomSideEffectHandler(
		it = chatRoomSideEffect,
		onConfigComplete = chatRoomConfigComplete
	)
}
