package com.example.a963103033239757ba10504dc3857ddc7.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */

@Entity(tableName = "ship_table")
data class ShipModel(
    @PrimaryKey val name: String,
    var capacity: Int,
    var speed: Int,
    var durability: Int,
    var damageCapacity: Int,
    var remainingTime: Int,
    var currentLocation: String
) {
}