package com.example.a963103033239757ba10504dc3857ddc7.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammad AKKAD on 10/7/21.
 */
@Entity(tableName = "fav_station_table")
data class FavStationModel(
    @PrimaryKey val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: Long,
    val stock: Long,
    val need: Long,
    var isFav: Boolean
) {}