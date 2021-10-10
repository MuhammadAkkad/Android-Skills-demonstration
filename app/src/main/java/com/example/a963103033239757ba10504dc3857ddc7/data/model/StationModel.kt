package com.example.a963103033239757ba10504dc3857ddc7.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
@Entity(tableName = "station_list_table")
data class StationModel(
    @PrimaryKey val name: String,
    var coordinateX: Double,
    var coordinateY: Double,
    var capacity: Int,
    var stock: Int,
    var need: Int,
    var distance: Int,
    var isFav: Boolean = false
)