package com.uniandes.vinilosapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.uniandes.vinilosapplication.data.model.CollectorModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService
import com.uniandes.vinilosapplication.data.network.broker.NetworkServiceAdapter
import com.uniandes.vinilosapplication.repositories.CollectorsRepository

class CollectorViewModel(application: Application) : AndroidViewModel(application) {

    private val collectorsRepository = CollectorsRepository(application)

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
        collectorsRepository.refreshData({
            _collectors.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            Log.d("Error", it.toString())
            _eventNetworkError.value = true
        })
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