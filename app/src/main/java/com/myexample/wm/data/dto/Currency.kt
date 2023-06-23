package com.myexample.wm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String,
    @SerialName("symbol")
    val symbol: String
)