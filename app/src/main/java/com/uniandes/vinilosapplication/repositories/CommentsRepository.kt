package com.uniandes.vinilosapplication.repositories

import android.app.Application
import android.util.Log

import com.uniandes.vinilosapplication.data.model.CommentModel
import com.uniandes.vinilosapplication.data.network.broker.CacheManager
import com.uniandes.vinilosapplication.data.network.broker.NetworkService

class CommentsRepository(val application: Application) {
    suspend fun refreshData(
        albumId: Int
    ): List<CommentModel> {
        val potentialResp =
            CacheManager.getInstance(application.applicationContext).getComments(albumId)
        return if (potentialResp.isEmpty()) {
            Log.d("Cache decision", "get from network")
            val comments = NetworkService.getInstance(application).getComments(albumId)
            CacheManager.getInstance(application.applicationContext).addComments(albumId, comments)
            comments
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}