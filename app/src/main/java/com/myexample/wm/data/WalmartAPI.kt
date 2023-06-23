package com.myexample.wm.data

import com.myexample.wm.Constants
import com.myexample.wm.data.dto.CountryItem
import retrofit2.http.GET

interface WalmartAPI {

    @GET(Constants.FILE_PATH)
    suspend fun getCounties(): List<CountryItem>
}