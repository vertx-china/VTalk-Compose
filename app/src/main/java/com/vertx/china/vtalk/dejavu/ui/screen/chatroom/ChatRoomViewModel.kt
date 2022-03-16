package com.vertx.china.vtalk.dejavu.ui.screen.chatroom

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertx.china.vtalk.dejavu.base.globalfunc.SideEffectChannel
import com.vertx.china.vtalk.dejavu.base.globalfunc.debugLog
import com.vertx.china.vtalk.dejavu.data.api.TreeNewBeeServer
import com.vertx.china.vtalk.dejavu.data.datastore.DeviceDataStore
import com.vertx.china.vtalk.dejavu.data.network.buildScarlet
import com.vertx.china.vtalk.dejavu.data.pojo.Message
import com.vertx.china.vtalk.dejavu.data.pojo.TNBMessage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.runBlocking
import java.util.*

class ChatRoomViewModel(
	val args: Bundle?,
	private val deviceDataStore: DeviceDataStore,
) : ViewModel() {

	private val _chatRoomViewStateFlow = MutableStateFlow(ChatRoomViewState.Empty)
	val chatRoomViewStateFlow: StateFlow<ChatRoomViewState> = _chatRoomViewStateFlow

	private val _chatRoomSideEffectChannel = SideEffectChannel<ChatRoomSideEffect>()
	val chatRoomSideEffectFlow = _chatRoomSideEffectChannel.receiveAsFlow()
	private fun sendSideEffect(it: ChatRoomSideEffect) {
		viewModelScope.launch { _chatRoomSideEffectChannel.send(it) }
	}

	private val isActive = MutableStateFlow(false)
	var currentActiveChatRoomServer: TreeNewBeeServer? = null

	init {
		tryToConfigureTempChatRoom()
	}

	@Suppress("MemberVisibilityCanBePrivate")
	fun tryToConfigureTempChatRoom() {
		viewModelScope.launch {
			val tempScarletConfig = deviceDataStore.tempChatRoomScarletConfig.first()
			if (tempScarletConfig == null) {
				_chatRoomSideEffectChannel.send(ChatRoomSideEffect.FillInChatRoomInfoByManually)
			} else {
				currentActiveChatRoomServer =
					buildScarlet(tempScarletConfig).create(TreeNewBeeServer::class.java)
				activeScarletServerObserve()
			}
		}
	}

	private fun activeScarletServerObserve() {
		val scarlet = currentActiveChatRoomServer ?: return
		if (runBlocking { isActive.first() }) return

		scarlet.observeTNBMessage().asFlow().onEach { each ->
			isActive.update { true }
			debugLog(each.toString())

			each?.toForGUIMessage()?.let { message ->
				_chatRoomViewStateFlow.update { viewState ->
					viewState.copy(messages = listOf(message) + (viewState.messages ?: emptyList()))
				}
			}
		}.launchIn(viewModelScope)
	}

	fun onSendMessage(content: String) {
		viewModelScope.launch {
			val userNickname = deviceDataStore.tempChatRoomName.first() ?: return@launch

			val newMessage = Message(
				author = userNickname,
				content = content,
			)
			val sendSuccess = currentActiveChatRoomServer?.sendTNBMessage(newMessage.toTNBMessage())
			if (sendSuccess == true) {
				_chatRoomViewStateFlow.update { viewState ->
					viewState.copy(
						messages = listOf(
							newMessage.copy(
								id = UUID.randomUUID().toString()
							)
						) + (viewState.messages ?: emptyList())
					)
				}
			}
		}
	}

	private fun TNBMessage.toForGUIMessage(): Message? {
		return Message(
			id = this.id,
			author = this.nickname ?: return null,
			content = this.content,
			timestamp = this.time ?: return null
		)
	}

	private fun Message.toTNBMessage(): TNBMessage {
		return TNBMessage(
			nickname = this.author,
			content = this.content,
		)
	}

}