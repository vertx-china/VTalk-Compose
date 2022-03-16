package com.vertx.china.vtalk.dejavu.base.ext

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

val gson: Gson = GsonBuilder().setLenient().create()

inline fun <reified T> fromJson(json: String): T {
	val type = object : TypeToken<T>() {}.type
	return gson.fromJson(json, type)
}

inline fun <reified T> fromJson(jsonElement: JsonElement): T {
	val type = object : TypeToken<T>() {}.type
	return gson.fromJson(jsonElement, type)
}

fun JsonElement.toJsonString() = gson.toJson(this) ?: null

inline fun <reified T> String.toObj(): T {
	return fromJson(this)
}

fun Any?.toJsonString(): String? = gson.toJson(this) ?: null