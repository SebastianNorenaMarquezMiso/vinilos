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
import com.uniandes.vinilosapplication.data.model.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkService constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://backvynils-javier.herokuapp.com/"
        private var instance: NetworkService? = null
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

    suspend fun getAlbums() = suspendCoroutine<List<AlbumModel>> { cont ->
        requestQueue.add(
            getRequest("albums",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<AlbumModel>()
                    val list2 = mutableListOf<TrackModel>()
                    var item: JSONObject? = null
                    for (i in 0 until resp.length()) {
                        item = resp.getJSONObject(i)
                        list.add(
                            i,
                            AlbumModel(
                                albumId = item.getInt("id"),
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                recordLabel = item.getString("recordLabel"),
                                releaseDate = item.getString("releaseDate"),
                                genre = item.getString("genre"),
                                description = item.getString("description"),
                                tracks = list2
                            )
                        )
                    }
                    cont.resume(list)
                },
                Response.ErrorListener {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getAlbumDetail(
        albumId: Int
    ) = suspendCoroutine<AlbumModel> { cont ->
        requestQueue.add(
            getRequest("albums/$albumId",
                Response.Listener<String> { response ->
                    val item = JSONObject(response)
                    val tracks = item.getJSONArray("tracks")
                    val list = mutableListOf<TrackModel>()
                    var albumDetail: JSONObject? = null
                    for (i in 0 until tracks.length()) {
                        albumDetail = tracks.getJSONObject(i)
                        list.add(
                            i,
                            TrackModel(
                                trackId = albumDetail.getInt("id"),
                                name = albumDetail.getString("name"),
                                duration = albumDetail.getString("duration")
                            )
                        )
                    }
                    val album = AlbumModel(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description"),
                        tracks = list
                    )
                    cont.resume(album)
                },
                Response.ErrorListener {
                    cont.resumeWithException(it)
                })
        )
    }

    fun createAlbum(
        albumBody: String,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            postRequest("albums",
                JSONObject(albumBody),
                Response.Listener<JSONObject> { response ->
                    onComplete(response)
                },
                Response.ErrorListener {
                    onError(it)
                })
        )
    }

    fun associateTrack(
        albumId: Int,
        trackBody: String,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            postRequest("albums/$albumId/tracks",
                JSONObject(trackBody),
                Response.Listener<JSONObject> { response ->
                    onComplete(response)
                },
                Response.ErrorListener {
                    onError(it)
                })
        )
    }

    suspend fun getMusicians(

    ) = suspendCoroutine<List<MusicianModel>> { cont ->
        requestQueue.add(
            getRequest("musicians",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<MusicianModel>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)

                        // Get album list
                        val albums = item.getJSONArray("albums")
                        val albumList = mutableListOf<AlbumModel>()
                        var albumItem: JSONObject? = null
                        for (i in 0 until albums.length()) {
                            albumItem = albums.getJSONObject(i)
                            albumList.add(
                                i,
                                AlbumModel(
                                    albumId = albumItem.getInt("id"),
                                    name = albumItem.getString("name"),
                                    cover = albumItem.getString("cover"),
                                    recordLabel = albumItem.getString("recordLabel"),
                                    releaseDate = albumItem.getString("releaseDate"),
                                    genre = albumItem.getString("genre"),
                                    description = albumItem.getString("description")
                                )
                            )
                        }

                        // Get performer prizes
                        val performerPrizes = item.getJSONArray("performerPrizes")
                        val performerPrizesList = mutableListOf<PerformerPrizesModel>()
                        var performerPrizeItem: JSONObject? = null
                        for (i in 0 until performerPrizes.length()) {
                            performerPrizeItem = performerPrizes.getJSONObject(i)
                            performerPrizesList.add(
                                i,
                                PerformerPrizesModel(
                                    id = performerPrizeItem.getInt("id"),
                                    premiationDate = performerPrizeItem.getString(
                                        "premiationDate"
                                    )
                                )
                            )
                        }


                        list.add(
                            i,
                            MusicianModel(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                birthDate = item.getString("birthDate"),
                                albums = albumList,
                                performerPrizes = performerPrizesList
                            )
                        )
                    }
                    cont.resume(list)
                },
                Response.ErrorListener {
                    Log.d("", it.message.toString())
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getMusicianDetail(
        musicianId: Int
    ) = suspendCoroutine<MusicianModel> { cont ->
        requestQueue.add(
            getRequest("musicians/$musicianId",
                Response.Listener<String> { response ->
                    val item = JSONObject(response)

                    // Get album list
                    val albums = item.getJSONArray("albums")
                    val albumList = mutableListOf<AlbumModel>()
                    var albumItem: JSONObject? = null
                    for (i in 0 until albums.length()) {
                        albumItem = albums.getJSONObject(i)
                        albumList.add(
                            i,
                            AlbumModel(
                                albumId = albumItem.getInt("id"),
                                name = albumItem.getString("name"),
                                cover = albumItem.getString("cover"),
                                recordLabel = albumItem.getString("recordLabel"),
                                releaseDate = albumItem.getString("releaseDate"),
                                genre = albumItem.getString("genre"),
                                description = albumItem.getString("description")
                            )
                        )
                    }

                    // Get performer prizes
                    val performerPrizes = item.getJSONArray("performerPrizes")
                    val performerPrizesList = mutableListOf<PerformerPrizesModel>()
                    var performerPrizeItem: JSONObject? = null
                    for (i in 0 until performerPrizes.length()) {
                        performerPrizeItem = performerPrizes.getJSONObject(i)
                        performerPrizesList.add(
                            i,
                            PerformerPrizesModel(
                                id = performerPrizeItem.getInt("id"),
                                premiationDate = performerPrizeItem.getString(
                                    "premiationDate"
                                )
                            )
                        )
                    }


                    val musician = MusicianModel(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate"),
                        albums = albumList,
                        performerPrizes = performerPrizesList
                    )
                    cont.resume(musician)
                },
                Response.ErrorListener {
                    Log.d("", it.message.toString())
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getCollectors(
    ) = suspendCoroutine<List<CollectorModel>> { cont ->
        requestQueue.add(
            getRequest("collectors",
                Response.Listener<String> { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<CollectorModel>()
                    var item: JSONObject? = null
                    for (i in 0 until resp.length()) {
                        item = resp.getJSONObject(i)
                        list.add(
                            i,
                            CollectorModel(
                                collectorId = item.getInt("id"),
                                name = item.getString("name"),
                                telephone = item.getString("telephone"),
                                email = item.getString("email")
                            )
                        )
                    }
                    cont.resume(list)
                },
                Response.ErrorListener {
                    Log.d("", it.message.toString())
                    cont.resumeWithException(it)

                })
        )
    }

    suspend fun getCollectorDetail(
        collectorId: Int
    ) = suspendCoroutine<CollectorModel> { cont ->
        requestQueue.add(
            getRequest("collectors/$collectorId",
                Response.Listener<String> { response ->
                    val item = JSONObject(response)

                    val collector = CollectorModel(
                        collectorId = item.getInt("id"),
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email")
                    )
                    cont.resume(collector)
                },
                Response.ErrorListener {
                    Log.d("", it.message.toString())
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getCollectorAlbums(
        collectorId: Int
    ) = suspendCoroutine<List<AlbumModel>> { cont ->
        requestQueue.add(
            getRequest("collectors/$collectorId/albums",
                Response.Listener<String> { response ->

                    val resp = JSONArray(response)
                    val list = mutableListOf<AlbumModel>()
                    var item: JSONObject? = null

                    for (i in 0 until resp.length()) {
                        item = resp.getJSONObject(i)
                        val album = item.getJSONObject("album")
                        list.add(
                            i,
                            AlbumModel(
                                albumId = album.getInt("id"),
                                name = album.getString("name"),
                                cover = album.getString("cover"),
                                recordLabel = album.getString("recordLabel"),
                                releaseDate = album.getString("releaseDate"),
                                genre = album.getString("genre"),
                                description = album.getString("description")
                            )
                        )
                    }
                    cont.resume(list)
                },
                Response.ErrorListener {
                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getComments(
        albumId: Int
    ) = suspendCoroutine<List<CommentModel>> { cont ->
        requestQueue.add(
            getRequest("albums/$albumId/comments",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<CommentModel>()
                    var item: JSONObject? = null
                    for (i in 0 until resp.length()) {
                        item = resp.getJSONObject(i)
                        Log.d("Response", item.toString())
                        list.add(
                            i,
                            CommentModel(
                                albumId = albumId,
                                rating = item.getInt("rating").toString(),
                                description = item.getString("description")
                            )
                        )
                    }
                    cont.resume(list)
                },
                Response.ErrorListener {
                    Log.d("", it.message.toString())
                    cont.resumeWithException(it)
                })
        )
    }

    fun postComment(
        body: JSONObject,
        albumId: Int,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            postRequest("albums/$albumId/comments",
                body,
                Response.Listener<JSONObject> { response ->
                    onComplete(response)
                },
                Response.ErrorListener {
                    onError(it)
                })
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.PUT,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }
}