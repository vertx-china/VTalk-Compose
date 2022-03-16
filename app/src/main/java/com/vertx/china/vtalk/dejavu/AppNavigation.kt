package com.vertx.china.vtalk.dejavu

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.navigation
import com.vertx.china.vtalk.dejavu.ui.screen.chatroom.ChatRoomScreen

internal sealed class Screen(val route: String) {
	object ChatRoom : Screen("chatroom")
}

private sealed class LeafScreen(
	private val route: String,
) {
	fun createRoute(root: Screen) = "${root.route}/$route"

	object ChatRoom : LeafScreen("chatroom/{chatroomId}") {
		fun createRoute(root: Screen, chatroomId: String): String {
			return "${root.route}/chatroom/$chatroomId"
		}
	}

	object Profile : LeafScreen("profile/{userId}") {
		fun createRoute(root: Screen, userId: String): String {
			return "${root.route}/profile/$userId"
		}
	}
}

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
	navController: NavHostController,
	modifier: Modifier = Modifier,
) {
	AnimatedNavHost(
		navController = navController,
		startDestination = Screen.ChatRoom.route,
		enterTransition = { defaultVTalkEnterTransition(initialState, targetState) },
		exitTransition = { defaultVTalkExitTransition(initialState, targetState) },
		popEnterTransition = { defaultVTalkPopEnterTransition() },
		popExitTransition = { defaultVTalkPopExitTransition() },
		modifier = modifier,
	) {
		addChatRoomTopLevel(navController)
	}
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addChatRoomTopLevel(
	navController: NavController,
) {
	navigation(
		route = Screen.ChatRoom.route,
		startDestination = LeafScreen.ChatRoom.createRoute(Screen.ChatRoom),
	) {
		addChatRoom(navController, Screen.ChatRoom)
		addProfile(navController, Screen.ChatRoom)
	}
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addChatRoom(
	navController: NavController,
	root: Screen,
) {
	composable(
		route = LeafScreen.ChatRoom.createRoute(root),
		debugLabel = "ChatRoom()",
		arguments = listOf(
			navArgument("userId") { type = NavType.StringType; defaultValue = "" },
		),
	) {
		ChatRoomScreen(
			args = navController.currentBackStackEntry?.arguments,
			openProfile = { userId ->
				navController.navigate(LeafScreen.Profile.createRoute(root, userId))
			}
		)
	}
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfile(
	navController: NavController,
	root: Screen,
) {
	composable(
		route = LeafScreen.Profile.createRoute(root),
		debugLabel = "Profile()",
	) {

	}
}


// ***************** EnterTransition *************************

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultVTalkEnterTransition(
	initial: NavBackStackEntry,
	target: NavBackStackEntry,
): EnterTransition {
	val initialNavGraph = initial.destination.hostNavGraph
	val targetNavGraph = target.destination.hostNavGraph
	// If we're crossing nav graphs (bottom navigation graphs), we crossfade
	if (initialNavGraph.id != targetNavGraph.id) {
		return fadeIn()
	}
	// Otherwise we're in the same nav graph, we can imply a direction
	return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultVTalkExitTransition(
	initial: NavBackStackEntry,
	target: NavBackStackEntry,
): ExitTransition {
	val initialNavGraph = initial.destination.hostNavGraph
	val targetNavGraph = target.destination.hostNavGraph
	// If we're crossing nav graphs (bottom navigation graphs), we crossfade
	if (initialNavGraph.id != targetNavGraph.id) {
		return fadeOut()
	}
	// Otherwise we're in the same nav graph, we can imply a direction
	return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

private val NavDestination.hostNavGraph: NavGraph
	get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultVTalkPopEnterTransition(): EnterTransition {
	return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultVTalkPopExitTransition(): ExitTransition {
	return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}