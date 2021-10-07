package com.example.a963103033239757ba10504dc3857ddc7.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.api.Constants
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel

/**
 * Created by Muhammad AKKAD on 10/6/21.
 */
@Database(entities = [StationModel::class], version = 2, exportSchema = false)
public abstract class FavStationDatabase : RoomDatabase() {

    abstract fun stationDao(): FavStationDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FavStationDatabase? = null

        fun getDatabase(context: Context?): FavStationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context?.applicationContext!!,
                    FavStationDatabase::class.java,
                    Constants.DatabaseName
                ).allowMainThreadQueries().build() // TODO coronties
                INSTANCE = instance
                instance
            }
        }
    }
}

