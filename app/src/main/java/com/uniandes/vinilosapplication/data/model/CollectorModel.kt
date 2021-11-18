package com.uniandes.vinilosapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectors_table")
data class CollectorModel(
    @PrimaryKey val collectorId: Int,
    val name: String,
    val telephone: String,
    val email: String
)