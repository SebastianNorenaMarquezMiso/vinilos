package com.uniandes.vinilosapplication

import android.app.Application
import com.uniandes.vinilosapplication.data.dao.VinylRoomDatabase

class VinylsApplication : Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}