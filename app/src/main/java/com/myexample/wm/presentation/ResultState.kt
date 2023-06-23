package com.myexample.wm.presentation

import com.myexample.wm.domain.model.Country

data class ResultState(

    val isLoading: Boolean = true,
    val itemsList: List<Country> = emptyList(),
    val error: String = "",
)
