package com.myexample.wm.data

import com.myexample.wm.TestHelper
import com.myexample.wm.data.dto.CountryItem
import kotlinx.coroutines.test.runTest
import org.junit.Test


class WalmartAPITest {

    @Test
    fun `API get country list`() = runTest {
        println("******* API get country list *******")
        val jsonString = TestHelper.loadJsonAsString("countries.json")
        val api = object : WalmartAPI {
            override suspend fun getCountries(): List<CountryItem> {
                return TestHelper.deserialize(jsonString) ?: emptyList()
            }
        }
        val countries = api.getCountries()
        assert(countries.isNotEmpty())
    }
}