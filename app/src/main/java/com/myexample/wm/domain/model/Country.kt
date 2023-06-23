package com.myexample.wm.domain.model

import com.myexample.wm.data.dto.Currency
import com.myexample.wm.data.dto.Language

data class Country(
    val capital: String,
    val code: String,
    val currency: Currency,
    val flag: String,
    val language: Language,
    val name: String,
    val region: String
)