package com.myexample.wm.domain.uc

import coil.network.HttpException
import com.myexample.wm.Resource
import com.myexample.wm.di.DependencyProvider
import com.myexample.wm.domain.model.Country
import com.myexample.wm.domain.repository.WalmartRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetCountries constructor(
    private val repository: WalmartRepo = DependencyProvider.instance.repo
) {
    operator fun invoke(): Flow<Resource<List<Country>>> = flow {
        try {
            emit(Resource.Loading())
            val list = repository.getCountries()
            emit(Resource.Success(list))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}