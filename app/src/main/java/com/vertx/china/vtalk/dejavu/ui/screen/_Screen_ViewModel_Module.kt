package com.vertx.china.vtalk.dejavu.ui.screen

import android.os.Bundle
import com.vertx.china.vtalk.dejavu.ui.screen.chatroom.ChatRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val screenViewModelModule = module {
	viewModel { (bundle: Bundle?) -> ChatRoomViewModel(bundle, get()) }
}