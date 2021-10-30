package com.uniandes.vinilosapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.uniandes.vinilosapplication.data.model.CollectorModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService
import com.uniandes.vinilosapplication.data.network.broker.NetworkServiceAdapter

class CollectorViewModel(application: Application) : AndroidViewModel(application) {

    private val _collectors = MutableLiveData<List<CollectorModel>>()

    val collectors: LiveData<List<CollectorModel>>
        get() = _collectors

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {

        Log.d("Response", "item".toString())

        NetworkService.getInstance(getApplication()).getCollectors(onComplete = {

            val list = listOf<CollectorModel>()
            _collectors.postValue(it)

            Log.d("Response", it.toString())
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
        /*NetworkServiceAdapter.getCollectors(onResponse = {
            val list = listOf<CollectorModel>()
            _collectors.postValue(list)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, onFailure = {
            _eventNetworkError.value = true
        })*/
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}