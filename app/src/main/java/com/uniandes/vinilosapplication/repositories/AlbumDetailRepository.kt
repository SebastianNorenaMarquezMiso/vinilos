package com.uniandes.vinilosapplication.repositories

import android.app.Application
import com.uniandes.vinilosapplication.data.model.AlbumModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService


class AlbumDetailRepository(val application: Application) {
    suspend fun refreshData(albumId: Int): AlbumModel {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
//        NetworkService.getInstance(application).getAlbums(
//            {
//                //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
//                callback(it)
//            },
//            onError
//        )
        return NetworkService.getInstance(application).getAlbumDetail(albumId);
    }
}