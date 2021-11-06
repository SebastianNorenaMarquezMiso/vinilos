package com.uniandes.vinilosapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.uniandes.vinilosapplication.data.model.CommentModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService
import com.uniandes.vinilosapplication.data.network.broker.NetworkServiceAdapter
import com.uniandes.vinilosapplication.repositories.CommentsRepository

class CommentViewModel(application: Application, albumId: Int) :  AndroidViewModel(application) {

    private val commentsRepository = CommentsRepository(application)

    private val _comments = MutableLiveData<List<CommentModel>>()

    val comments: LiveData<List<CommentModel>>
        get() = _comments

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = albumId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        commentsRepository.refreshData(id, {
            _comments.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            Log.d("Error", it.toString())
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CommentViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}