package com.example.a963103033239757ba10504dc3857ddc7.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
@Entity(tableName = "station_list_table")
data class StationModel(
    @PrimaryKey val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    var capacity: Long,
    var stock: Long,
    var need: Long,
    var isFav: Boolean
)