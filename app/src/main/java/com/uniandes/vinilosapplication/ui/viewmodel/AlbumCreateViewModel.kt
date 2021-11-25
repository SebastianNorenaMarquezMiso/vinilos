package com.uniandes.vinilosapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.uniandes.vinilosapplication.data.model.AlbumCreateModel
import com.uniandes.vinilosapplication.repositories.AlbumCreateRepository
import org.json.JSONObject


class AlbumCreateViewModel(application: Application) : AndroidViewModel(application) {

    private val albumCreateRepository = AlbumCreateRepository(application)

    private var _eventNetworkError = MutableLiveData<String>("")

    val eventNetworkError: LiveData<String>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    fun postDataToNetwork(albumBody: AlbumCreateModel, callback: () -> Unit) {
        return albumCreateRepository.postData(albumBody, {
            _eventNetworkError.value = ""
            _isNetworkErrorShown.value = false
            callback()

        }, {
            _isNetworkErrorShown.value = false
            _eventNetworkError.value =
                JSONObject(String(it.networkResponse.data)).getString("message")
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
