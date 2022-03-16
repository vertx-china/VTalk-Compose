package com.vertx.china.vtalk.dejavu.ui.screen.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vertx.china.vtalk.dejavu.AppNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Home() {
	val navController = rememberAnimatedNavController()

	Scaffold {
		Row(Modifier.fillMaxWidth()) {
			AppNavigation(
				navController = navController,
				modifier = Modifier
					.weight(1f)
					.fillMaxHeight(),
			)
		}
	}
}
