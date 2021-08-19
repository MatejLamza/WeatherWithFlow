package com.example.weatherappflow.utils.helpers

import android.util.Log
import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.nullString
import com.github.salomonbrys.kotson.obj
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.HttpException

private const val TAG = "ErrorParser"


class ErrorParser(private val gson: Gson) {

    private val genericError by lazy { "Unknown error occurred" }

    fun parse(error: Throwable?): String {
        try {
            if (error is HttpException) {
                return error.parseMessage()!!
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Failed to parse error message", e)
        }
        return genericError
    }

    private fun HttpException.parseMessage(): String? {
        val body = response()?.errorBody()
        val payload = body?.string()
        val obj = kotlin.runCatching { gson.fromJson<JsonElement>(payload.orEmpty()).obj }
            .getOrNull()
        return obj?.let(::parseJsonMessage)
    }

    private fun parseJsonMessage(obj: JsonObject): String? {
        val element = obj
        return element["message"].nullString
    }
}
