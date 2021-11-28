package com.uniandes.vinilosapplication.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.uniandes.vinilosapplication.data.model.TrackModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService
import org.json.JSONObject


class AssociateTrackRepository(val application: Application) {
    private val gson = Gson()

    fun postData(
        albumId: Int,
        trackBody: TrackModel,
        callback: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkService.getInstance(application).associateTrack(
            albumId,
            gson.toJson(trackBody), {
                callback(it)
            },
            onError
        )
    }
}