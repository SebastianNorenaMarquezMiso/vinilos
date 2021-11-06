package com.uniandes.vinilosapplication.interfaces

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://vinyl-miso.herokuapp.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface RetrofitApiService {
    @GET("collectors")
    fun getCollectors():
            Call<String>

    @FormUrlEncoded
    @POST("collectors")
    fun postCollectors(
        @Field("name") name: String,
        @Field("telephone") telephone: String,
        @Field("email") email: String
    ):
            Call<String>

    @FormUrlEncoded
    @PUT("collectors")
    fun putCollectors(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("telephone") telephone: String,
        @Field("email") email: String
    ):
            Call<String>
}

object RetrofitApi {
    val retrofitService: RetrofitApiService by lazy {
        retrofit.create(RetrofitApiService::class.java)
    }
}