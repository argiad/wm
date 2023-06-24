package com.myexample.wm.domain.uc


import com.myexample.wm.TestHelper
import com.myexample.wm.data.WalmartAPI
import com.myexample.wm.data.dto.CountryItem
import com.myexample.wm.data.dto.toCountry
import com.myexample.wm.di.DependencyProvider
import com.myexample.wm.domain.model.Country
import com.myexample.wm.domain.repository.WalmartRepo
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test


class GetCountriesTest {

    private var dependencyProvider: DependencyProvider = object : DependencyProvider {
        override val api: WalmartAPI by lazy {
            val jsonString = TestHelper.loadJsonAsString("countries.json")
            object : WalmartAPI {
                override suspend fun getCountries(): List<CountryItem> {
                    val objects: List<CountryItem>? = TestHelper.deserialize(jsonString)
                    return objects ?: emptyList()
                }
            }
        }
        override val repo: WalmartRepo by lazy {
            object : WalmartRepo {
                override suspend fun getCountries(): List<Country> {
                    return api.getCountries().map { it.toCountry() }
                }

            }
        }
    }

    private val getCountries: GetCountries by lazy {
        GetCountries(repository = dependencyProvider.repo)
    }

    @Test
    operator fun invoke() = runTest {
        val resources = getCountries.invoke().drop(1).first()
        assert(resources.data?.isNotEmpty() == true)
    }
}