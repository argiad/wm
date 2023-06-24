package com.myexample.wm.data.repository

import com.myexample.wm.data.WalmartAPI
import com.myexample.wm.data.dto.toCountry
import com.myexample.wm.domain.model.Country
import com.myexample.wm.domain.repository.WalmartRepo

class WalmartRepoImpl constructor(
    private val api: WalmartAPI
) : WalmartRepo {

    override suspend fun getCountries(): List<Country> {
        return api.getCountries().map { it.toCountry() }
    }
}