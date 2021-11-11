package com.uniandes.vinilosapplication.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.uniandes.vinilosapplication.data.model.MusicianModel
import com.uniandes.vinilosapplication.data.network.broker.NetworkService


class MusicianRepository(val application: Application) {
    fun refreshData(callback: (List<MusicianModel>) -> Unit, onError: (VolleyError) -> Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkService.getInstance(application).getMusicians(
            {
                // Guardar los musicos de la variable it en un almacén de datos local para uso futuro
                callback(it)
            },
            onError
        )
    }
}