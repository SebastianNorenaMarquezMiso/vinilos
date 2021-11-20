package com.uniandes.vinilosapplication.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.uniandes.vinilosapplication.data.dao.CollectorModelDao
import com.uniandes.vinilosapplication.data.model.CollectorModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService

class CollectorsRepository(
    val application: Application,
    private val collectorsDao: CollectorModelDao
) {
    suspend fun refreshData(): List<CollectorModel> {
        val cached = collectorsDao.getCollectors()
        return if (cached.isNullOrEmpty()) {
            val cm =
                application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                emptyList()
            } else NetworkService.getInstance(application).getCollectors()
        } else cached
    }

}