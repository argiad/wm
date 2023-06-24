package com.myexample.wm

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter

object TestHelper {

    fun loadJsonAsString(fileName: String): String {
        val inputStream = javaClass.getResourceAsStream("/$fileName")
        return getStringFromInputStream(inputStream)
    }

    @Throws(IOException::class)
    private fun getStringFromInputStream(stream: InputStream?): String {
        var n = 0
        val buffer = CharArray(1024 * 4)
        val reader = InputStreamReader(stream, "UTF8")
        val writer = StringWriter()
        while (-1 != reader.read(buffer).also { n = it }) writer.write(buffer, 0, n)
        return writer.toString()
    }

    inline fun <reified T : Any> deserialize(jsonString: String): T? {
        return try {
            val json = Json {
                coerceInputValues = true
                ignoreUnknownKeys = true
            }
            return json.decodeFromString(jsonString)
        } catch (e: SerializationException) {
            println(e.message)
            null // if fails to deserialize
        }
    }
}