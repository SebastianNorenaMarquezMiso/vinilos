package com.uniandes.vinilosapplication.data.network.broker

import android.content.Context
import android.content.SharedPreferences
import com.uniandes.vinilosapplication.data.model.CommentModel


class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var comments: HashMap<Int, List<CommentModel>> = hashMapOf()
    fun addComments(albumId: Int, comment: List<CommentModel>){
        if (!comments.containsKey(albumId)){
            comments[albumId] = comment
        }
    }
    fun getComments(albumId: Int) : List<CommentModel>{
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf<CommentModel>()
    }

}