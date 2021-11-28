package com.uniandes.vinilosapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.uniandes.vinilosapplication.data.model.TrackModel
import com.uniandes.vinilosapplication.repositories.AssociateTrackRepository
import org.json.JSONObject


class AssociateTrackViewModel(application: Application) : AndroidViewModel(application) {

    private val associateTrackRepository = AssociateTrackRepository(application)

    private var _eventNetworkError = MutableLiveData<String>("")

    val eventNetworkError: LiveData<String>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    fun postDataToNetwork(albumId: Int, trackBody: TrackModel, callback: () -> Unit) {
        return associateTrackRepository.postData(albumId, trackBody, {
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
            if (modelClass.isAssignableFrom(AssociateTrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AssociateTrackViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
