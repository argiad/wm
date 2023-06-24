package com.myexample.wm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("code")
    val code: String = "N/D",
    @SerialName("name")
    val name: String
)