package com.uniandes.vinilosapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.uniandes.vinilosapplication.data.model.AlbumModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService
import com.uniandes.vinilosapplication.data.network.broker.NetworkServiceAdapter

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<AlbumModel>>()

    val albums: LiveData<List<AlbumModel>>
        get() = _albums

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
        NetworkService.getInstance(getApplication()).getAlbums({
            val list = listOf<AlbumModel>()
            _albums.postValue(list)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
        /*NetworkServiceAdapter.getCollectors(onResponse = {
            val list = listOf<AlbumModel>()
            _albums.postValue(list)
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
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}