package com.uniandes.vinilosapplication.data.network.broker

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.uniandes.vinilosapplication.data.model.AlbumModel
import com.uniandes.vinilosapplication.data.model.CollectorModel
import com.uniandes.vinilosapplication.data.model.CommentModel

import com.uniandes.vinilosapplication.data.model.TrackModel

import org.json.JSONArray
import org.json.JSONObject
import java.awt.font.NumericShaper

class NetworkService constructor(context: Context) {

    companion object{
        const val BASE_URL= "https://backvynils-javier.herokuapp.com/"
        var instance: NetworkService? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkService(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete:(resp:List<AlbumModel>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<AlbumModel>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, AlbumModel(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    fun getAlbumDetail(albumId: Int, onComplete:(resp:AlbumModel)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String> { response ->
                val item = JSONObject (response)
                val tracks = item.getJSONArray("tracks")
                val list = mutableListOf<TrackModel>()
                for (i in 0 until tracks.length()) {
                    val item = tracks.getJSONObject(i)
                    list.add(i, TrackModel(trackId = item.getInt("id"),name = item.getString("name"), duration = item.getString("duration")))
                }
                val album =  AlbumModel(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"),tracks=list)
                onComplete(album)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    fun getCollectors(onComplete:(resp:List<CollectorModel>)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                Log.d("tagb", response)
                val resp = JSONArray(response)
                val list = mutableListOf<CollectorModel>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, CollectorModel(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
                Log.d("", it.message.toString())
            }))
    }
    fun getComments(albumId:Int, onComplete:(resp:List<CommentModel>)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("albums/$albumId/comments",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<CommentModel>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    Log.d("Response", item.toString())
                    list.add(i, CommentModel(albumId = albumId, rating = item.getInt("rating").toString(), description = item.getString("description")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    fun postComment(body: JSONObject, albumId: Int,  onComplete:(resp:JSONObject)->Unit , onError: (error:VolleyError)->Unit){
        requestQueue.add(postRequest("albums/$albumId/comments",
            body,
            Response.Listener<JSONObject> { response ->
                onComplete(response)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)

    }
}