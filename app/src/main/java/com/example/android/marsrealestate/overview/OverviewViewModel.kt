/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.ApiService
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private lateinit var disposable: Disposable

    var counterFirst = 0
    var counterSecond = 0

    val first = Observable.interval(2, 2, TimeUnit.MILLISECONDS)
        .map { counterFirst++ }
        .filter { it % 2 != 0 }
        .take(5).publish().connect()
    val second = Observable.interval(3, 3, TimeUnit.MILLISECONDS)
        .map { counterSecond++ }
        .filter { it % 2 == 0 }
        .take(5)

    fun firstFlow() = listOf(1, 3, 5, 7, 9, 9).asFlow()

    val secondFlow = flowOf(0, 2, 4, 6, 8)

    val flow = flow {
        val values = listOf(1, 3, 5, 7, 9, 9)
        values.forEach { emit(it.toString()) }
    }


    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableStateFlow("Empty!!!")

    // The external immutable LiveData for the response String
    val response: SharedFlow<String>
        get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
        try {
            viewModelScope.launch {
                val def = async {
                    "Success: ${ApiService.service.loadData().size} Mars properties retrieved"
                }
                _response.emit(def.await())
            }
        } catch (e: Exception) {
            _response.value = "Failure: ${e.message}"
        }

        try {
            with(viewModelScope) {
                val def = async {
                    "Success: ${ApiService.service.loadData().size} Mars properties retrieved"
                }
                launch { _response.emit(def.await()) }
            }
        } catch (e: Exception) {
            _response.value = "Failure: ${e.message}"
        }

        try {
            viewModelScope.launch {
                val deferred = viewModelScope.async {
                    firstFlow().zip(secondFlow) { first, second -> Pair(first, second) }
                        .reduce { a, b -> Pair(a.first, b.second) }

                }
                _response.value = deferred.await().toString()
            }
        } catch (e: Exception) {
            _response.value = "Failure: ${e.message}"
        }


//        disposable =
//            first
//                .groupBy { it % 2 == 0 }
//                .buffer(10)
//                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
//                .subscribe { _response.value = it.toString() }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
