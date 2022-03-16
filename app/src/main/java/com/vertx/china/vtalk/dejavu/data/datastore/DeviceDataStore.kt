package com.vertx.china.vtalk.dejavu.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vertx.china.vtalk.dejavu.base.ext.toJsonString
import com.vertx.china.vtalk.dejavu.base.ext.toObj
import com.vertx.china.vtalk.dejavu.data.network.ScarletConfig
import kotlinx.coroutines.flow.map

private const val TAG = "DeviceDataStore"

private val Context.deviceDataStore: DataStore<Preferences> by preferencesDataStore(name = TAG)

@Suppress("PrivatePropertyName")
class DeviceDataStore(
	private val context: Context,
) {

	private val TEMP_CHATROOM_SCARLET_CONFIG =
		stringPreferencesKey(TAG + "TEMP_CHATROOM_SCARLET_CONFIG")

	val tempChatRoomScarletConfig = context.deviceDataStore.data.map { pref ->
		pref[TEMP_CHATROOM_SCARLET_CONFIG]?.toObj<ScarletConfig?>()
	}
	suspend fun storeTempChatRoomScarletConfig(scarletConfig: ScarletConfig) {
		context.deviceDataStore.edit { pref ->
			pref[TEMP_CHATROOM_SCARLET_CONFIG] = scarletConfig.toJsonString() ?: return@edit
		}
	}

	private val TEMP_CHATROOM_NICKNAME =
		stringPreferencesKey(TAG + "TEMP_CHATROOM_NICKNAME")

	val tempChatRoomName = context.deviceDataStore.data.map { pref ->
		pref[TEMP_CHATROOM_NICKNAME]
	}
	suspend fun storeTempChatRoomName(tempNickname: String) {
		context.deviceDataStore.edit { pref ->
			pref[TEMP_CHATROOM_NICKNAME] = tempNickname
		}
	}

}
