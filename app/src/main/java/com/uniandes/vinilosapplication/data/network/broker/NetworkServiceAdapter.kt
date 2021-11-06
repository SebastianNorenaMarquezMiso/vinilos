package com.uniandes.vinilosapplication.data.network.broker

import com.uniandes.vinilosapplication.interfaces.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkServiceAdapter {
    companion object {
        fun getCollectors(onResponse: (resp: String) -> Unit, onFailure: (resp: String) -> Unit) {
            var r = RetrofitApi.retrofitService.getCollectors()
            var p = r.enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        onFailure(t.message!!)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        onResponse(response.body()!!)
                    }
                })
        }

        fun postCollectors(
            body: Map<String, String>,
            onResponse: (resp: String) -> Unit,
            onFailure: (resp: String) -> Unit
        ): String? {
            var resp: String? = null

            RetrofitApi.retrofitService.postCollectors(
                body["name"] ?: "",
                body["telephone"] ?: "",
                body["email"] ?: ""
            ).enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        onFailure(t.message!!)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        onResponse(response.body()!!)
                    }
                })
            return resp
        }

        fun putCollectors(
            body: Map<String, String>,
            onResponse: (resp: String) -> Unit,
            onFailure: (resp: String) -> Unit
        ): String? {
            var resp: String? = null
            RetrofitApi.retrofitService.putCollectors(
                body[""] ?: "",
                body["name"] ?: "",
                body["telephone"] ?: "",
                body["email"] ?: ""
            ).enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        onFailure(t.message!!)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        onResponse(response.body()!!)
                    }
                })
            return resp
        }
    }
}