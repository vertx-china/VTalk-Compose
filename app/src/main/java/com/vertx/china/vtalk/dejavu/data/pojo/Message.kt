package com.vertx.china.vtalk.dejavu.data.pojo

data class Message(
	val id: String? = null,
	val author: String,
	val content: String,
	val timestamp: String? = null,
	val image: String? = null,
	val authorImage: String? = null
)
