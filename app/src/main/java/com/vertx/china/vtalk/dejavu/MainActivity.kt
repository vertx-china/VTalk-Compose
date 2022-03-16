package com.vertx.china.vtalk.dejavu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import com.google.accompanist.insets.ProvideWindowInsets
import com.vertx.china.vtalk.dejavu.ui.screen.home.Home
import com.vertx.china.vtalk.dejavu.ui.theme.JetchatTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)

		setContent {
			VTalkContent()
		}

		setOwners()
	}

	@Composable
	private fun VTalkContent() {
		ProvideWindowInsets(consumeWindowInsets = false) {
			JetchatTheme {
				Home()
			}
		}
	}
}

private fun ComponentActivity.setOwners() {
	val decorView = window.decorView
	if (ViewTreeLifecycleOwner.get(decorView) == null) {
		ViewTreeLifecycleOwner.set(decorView, this)
	}
	if (ViewTreeViewModelStoreOwner.get(decorView) == null) {
		ViewTreeViewModelStoreOwner.set(decorView, this)
	}
	if (ViewTreeSavedStateRegistryOwner.get(decorView) == null) {
		ViewTreeSavedStateRegistryOwner.set(decorView, this)
	}
}
