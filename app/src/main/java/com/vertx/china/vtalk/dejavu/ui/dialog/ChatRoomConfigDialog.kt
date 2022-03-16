package com.vertx.china.vtalk.dejavu.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vertx.china.vtalk.dejavu.R
import com.vertx.china.vtalk.dejavu.data.datastore.DeviceDataStore
import com.vertx.china.vtalk.dejavu.data.network.ScarletConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun ChatRoomConfigDialog(
	onConfigComplete: () -> Unit,
	deviceDataStore: DeviceDataStore = get()
) {
	val scope = rememberCoroutineScope()
	var showState by remember { mutableStateOf(true) }

	if (!showState) return

	var currentChatRoomConfig by remember { mutableStateOf<ScarletConfig?>(null) }
	var currentChatRoomNickname by remember { mutableStateOf("") }

	SideEffect {
		scope.launch {
			deviceDataStore.tempChatRoomScarletConfig.first()?.let {
				currentChatRoomConfig = it
			}

			deviceDataStore.tempChatRoomName.first()?.let {
				currentChatRoomNickname = it
			}
		}
	}


	var scheme by remember { mutableStateOf(currentChatRoomConfig?.scheme ?: "ws") }
	var host by remember { mutableStateOf(currentChatRoomConfig?.host ?: "") }
	var port by remember { mutableStateOf(currentChatRoomConfig?.port ?: "") }


	val placeholderContentColor =
		MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f)

	AlertDialog(
		onDismissRequest = { },
		title = {
			Text(text = stringResource(id = R.string.config_temp_chat_room_info))
		},

		text = {
			Column(Modifier.fillMaxWidth()) {
				OutlinedTextField(
					colors = TextFieldDefaults.outlinedTextFieldColors(
						focusedBorderColor = MaterialTheme.colorScheme.primary
					),
					value = scheme,
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Ascii,
						imeAction = ImeAction.Done,
					),
					placeholder = { Text(text = "scheme", color = placeholderContentColor) },
					maxLines = 1,
					onValueChange = { content -> scheme = content },
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 4.dp),
					textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
				)

				OutlinedTextField(
					colors = TextFieldDefaults.outlinedTextFieldColors(
						focusedBorderColor = MaterialTheme.colorScheme.primary
					),
					value = host,
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Ascii,
						imeAction = ImeAction.Done,
					),
					placeholder = { Text(text = "host", color = placeholderContentColor) },
					maxLines = 1,
					onValueChange = { content -> host = content },
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 4.dp),
					textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
				)

				OutlinedTextField(
					colors = TextFieldDefaults.outlinedTextFieldColors(
						focusedBorderColor = MaterialTheme.colorScheme.primary
					),
					value = port,
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Number,
						imeAction = ImeAction.Done,
					),
					placeholder = { Text(text = "port", color = placeholderContentColor) },
					maxLines = 1,
					onValueChange = { content -> port = content },
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 4.dp),
					textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
				)

				OutlinedTextField(
					colors = TextFieldDefaults.outlinedTextFieldColors(
						focusedBorderColor = MaterialTheme.colorScheme.primary
					),
					value = currentChatRoomNickname,
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Done,
					),
					placeholder = { Text(text = "nickname", color = placeholderContentColor) },
					maxLines = 1,
					onValueChange = { content -> currentChatRoomNickname = content },
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 4.dp),
					textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
				)
			}
		},
		confirmButton = {
			TextButton(onClick = {
				scope.launch {
					if (scheme.trim().isEmpty()
						|| host.trim().isEmpty()
						|| currentChatRoomNickname.trim().isEmpty()
					) return@launch

					deviceDataStore.storeTempChatRoomScarletConfig(ScarletConfig(scheme, host, port))
					deviceDataStore.storeTempChatRoomName(currentChatRoomNickname)
					showState = false
					onConfigComplete()
				}
			}) {
				Text(text = "Confirm")
			}
		}
	)

}