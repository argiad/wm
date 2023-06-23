package com.myexample.wm.domain.repository

import com.myexample.wm.domain.model.Country


interface WalmartRepo {
    suspend fun getCountries(): List<Country>
}