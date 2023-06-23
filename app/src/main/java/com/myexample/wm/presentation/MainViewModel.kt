package com.myexample.wm.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myexample.wm.Resource
import com.myexample.wm.domain.uc.GetCountries
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel constructor(
    private val getCountries: GetCountries = GetCountries()
) : ViewModel() {

    private val countriesEmitter = MutableLiveData<ResultState>()
    val countries: LiveData<ResultState> = countriesEmitter


    init {
        fetch()
    }

    private fun fetch() {
        getCountries().onEach { result ->
            val list = result.data ?: emptyList()

            when (result) {
                is Resource.Success -> {
                    countriesEmitter.value = ResultState(
                        isLoading = false,
                        itemsList = list
                    )
                    Log.d("Resource", "Success")
                }

                is Resource.Loading -> {
                    countriesEmitter.value = ResultState(
                        isLoading = true,
                        itemsList = list
                    )
                    Log.d("Resource", "Loading")
                }

                is Resource.Error -> {
                    countriesEmitter.value = ResultState(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                    Log.d("Resource", "Error \n ${countriesEmitter.value?.error}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reload() {
        countriesEmitter.value = ResultState(true, emptyList(), "")
        fetch()
    }
}


@Suppress("UNCHECKED_CAST")
class MyViewModelFactory constructor(private val getCountries: GetCountries) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.getCountries) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}