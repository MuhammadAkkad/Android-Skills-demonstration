package com.example.a963103033239757ba10504dc3857ddc7.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel

/**
 * Created by Muhammad AKKAD on 10/6/21.
 */
@Database(
    entities = [StationModel::class, ShipModel::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationListDao(): StationListDao
    abstract fun shipDao(): ShipDao
}

