package com.uniandes.vinilosapplication.repositories

import android.app.Application
import com.uniandes.vinilosapplication.data.model.CollectorModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService

class CollectorsRepository(val application: Application) {
    suspend fun refreshData(): List<CollectorModel> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
//        NetworkService.getInstance(application).getCollectors(
//            {
//                //Guardar los coleccionistas de la variable it en un almacén de datos local para uso futuro
//                callback(it)
//            },
//            onError
//        )
        return NetworkService.getInstance(application).getCollectors();
    }

}