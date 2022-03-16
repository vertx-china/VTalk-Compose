package com.vertx.china.vtalk.dejavu.data.fakedata

import com.vertx.china.vtalk.dejavu.data.pojo.Message
import com.vertx.china.vtalk.dejavu.ui.screen.chatroom.ChatRoomViewState

private val initialMessages = listOf(
    Message(
        "me",
        "lalalalala",
        "8:07 PM"
    ),
    Message(
        "me",
        "水冰月yyds!",
        "8:06 PM",
        "https://tiebapic.baidu.com/forum/pic/item/908fa0ec08fa513d91d23ab52a6d55fbb2fbd965.jpg"
    ),
    Message(
        "you",
        "You can use all the same stuff",
        "8:05 PM"
    ),
    Message(
        "you",
        "@me `while(ture)`",
        "8:05 PM"
    ),
    Message(
        "？？？",
        "Compose newbie as well, have you looked at the JetNews sample? Most blog posts end up " +
            "out of date pretty fast but this sample is always up to date and deals with async " +
            "data loading (it's faked but the same idea applies) \uD83D\uDC49" +
            "https://github.com/android/compose-samples/tree/master/JetNews",
        "8:04 PM"
    ),
    Message(
        "me",
        "Compose newbie: I’ve scourged the internet for tutorials about async data loading " +
            "but haven’t found any good ones. What’s the recommended way to load async " +
            "data and emit composable widgets?",
        "8:03 PM"
    )
)

val exampleChatRoomState = ChatRoomViewState.Preview.copy(
    messages = initialMessages
)
//
///**
// * Example colleague profile
// */
//val colleagueProfile = ProfileScreenState(
//    userId = "12345",
//    photo = R.drawable.someone_else,
//    name = "Taylor Brooks",
//    status = "Away",
//    displayName = "taylor",
//    position = "Senior Android Dev at Openlane",
//    twitter = "twitter.com/taylorbrookscodes",
//    timeZone = "12:25 AM local time (Eastern Daylight Time)",
//    commonChannels = "2"
//)
//
///**
// * Example "me" profile.
// */
//val meProfile = ProfileScreenState(
//    userId = "me",
//    photo = R.drawable.ali,
//    name = "Ali Conors",
//    status = "Online",
//    displayName = "aliconors",
//    position = "Senior Android Dev at Yearin\nGoogle Developer Expert",
//    twitter = "twitter.com/aliconors",
//    timeZone = "In your timezone",
//    commonChannels = null
//)
