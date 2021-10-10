package com.example.a963103033239757ba10504dc3857ddc7.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */

@Entity(tableName = "ship_table")
data class ShipModel(
    @PrimaryKey var name: String,
    var capacity: Int, // Kapasite
    var speed: Int, // hiz
    var durability: Int, // dayaniklilik
    var damageCapacity: Int, // hasar Kapisitesi: 100
    var remainingTime: Int, // 49s
    var currentLocation: String,
    var durabilityTimeDS: Int, // DS
    var spaceTimeDurationEUS: Int, // EUS
    var spaceSuitCountUGS: Int, // UGS
    var x: Double,
    var y: Double
) {
    constructor(name: String, speed: Int, capacity: Int, durability: Int) : this(
        name,
        capacity,
        speed,
        durability,
        0,
        0,
        "",
        0,
        0,
        0,
        0.0,
        0.0
    ) {
    }
}
