package com.example.a963103033239757ba10504dc3857ddc7.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */

@Entity(tableName = "ship_table")
data class ShipModel(
    @PrimaryKey val name: String,
    val capacity: Int,
    val speed: Int,
    val durability: Int,
    val damageCapacity: Int,
    val remainingTime: Int,
    val currentLocation: String
) {
}