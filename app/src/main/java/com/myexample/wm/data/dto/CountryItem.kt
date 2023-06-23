package com.myexample.wm.data.dto

import com.myexample.wm.domain.model.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryItem(
    @SerialName("capital")
    val capital: String,
    @SerialName("code")
    val code: String,
    @SerialName("currency")
    val currency: Currency,
    @SerialName("flag")
    val flag: String,
    @SerialName("language")
    val language: Language,
    @SerialName("name")
    val name: String,
    @SerialName("region")
    val region: String
)

fun CountryItem.toCountry(): Country {
    return Country(capital, code, currency, flag, language, name, region)
}