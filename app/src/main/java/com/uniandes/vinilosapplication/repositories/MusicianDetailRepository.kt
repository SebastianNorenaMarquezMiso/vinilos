package com.uniandes.vinilosapplication.repositories

import android.app.Application
import com.uniandes.vinilosapplication.data.model.MusicianModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService


class MusicianDetailRepository(val application: Application) {
    suspend fun refreshData(
        musicianId: Int
    ): MusicianModel {
//        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
//        NetworkService.getInstance(application).getMusicianDetail(
//            musicianId, {
//                //Guardar los musicos de la variable it en un almacén de datos local para uso futuro
//                callback(it)
//            },
//            onError
//        )
        return NetworkService.getInstance(application).getMusicianDetail(musicianId)
    }

}