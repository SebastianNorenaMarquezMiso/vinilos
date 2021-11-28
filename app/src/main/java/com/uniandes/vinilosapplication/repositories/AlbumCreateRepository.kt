package com.uniandes.vinilosapplication.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.uniandes.vinilosapplication.data.model.AlbumCreateModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService
import org.json.JSONObject


class AlbumCreateRepository(val application: Application) {
    private val gson = Gson()

    fun postData(
        albumBody: AlbumCreateModel,
        callback: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkService.getInstance(application).createAlbum(
            gson.toJson(albumBody), {
                callback(it)
            },
            onError
        )
    }
}