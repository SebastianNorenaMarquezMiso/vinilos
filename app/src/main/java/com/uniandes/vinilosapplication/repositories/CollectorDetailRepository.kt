package com.uniandes.vinilosapplication.repositories

import android.app.Application
import com.uniandes.vinilosapplication.data.model.CollectorModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService


class CollectorDetailRepository(val application: Application) {
    suspend fun refreshData(
        collectorId: Int
    ): CollectorModel {
//        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
//        NetworkService.getInstance(application).getMusicianDetail(
//            clllectorId, {
//                //Guardar los coleccionistas de la variable it en un almacén de datos local para uso futuro
//                callback(it)
//            },
//            onError
//        )

        val collectorDetail =
            NetworkService.getInstance(application).getCollectorDetail(collectorId)


        /*Log.d("33333", tasks.toString())
        val collectorAlbums =
            NetworkService.getInstance(application).getCollectorAlbums(collectorId)
        collectorDetail.collectorAlbums = collectorAlbums
        Log.d("3333322", collectorAlbums.toString())*/


        return collectorDetail
    }

}