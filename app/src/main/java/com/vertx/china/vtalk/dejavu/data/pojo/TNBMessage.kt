package com.vertx.china.vtalk.dejavu.data.pojo

import com.google.gson.annotations.SerializedName

// message for tree new bee
data class TNBMessage(
	val id: String? = null,
	@SerializedName("message") val content: String,
	val time: String? = null,
	val nickname: String? = null,
)
