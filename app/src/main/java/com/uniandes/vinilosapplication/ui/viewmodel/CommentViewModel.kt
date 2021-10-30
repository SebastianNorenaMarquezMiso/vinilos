package com.uniandes.vinilosapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.uniandes.vinilosapplication.data.model.CommentModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService
import com.uniandes.vinilosapplication.data.network.broker.NetworkServiceAdapter

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val _comments = MutableLiveData<List<CommentModel>>()

    val comments: LiveData<List<CommentModel>>
        get() = _comments

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
        /*NetworkService.getInstance(getApplication()).getComments({
            val list = listOf<CommentModel>()
            _comments.postValue(list)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })*/
        /*NetworkServiceAdapter.getCollectors(onResponse = {
            val list = listOf<CommentModel>()
            _comments.postValue(list)
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
            if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CommentViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}