package com.example.a963103033239757ba10504dc3857ddc7.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.api.Constants
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Muhammad AKKAD on 10/6/21.
 */
@Database(
    entities = [StationModel::class,  ShipModel::class],
    version = 2,
    exportSchema = false
)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun stationListDao(): StationListDao
    abstract fun shipDao(): ShipDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context?): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context?.applicationContext!!,
                    AppDatabase::class.java,
                    Constants.DatabaseName
                ).build() // TODO coronties
                INSTANCE = instance
                instance
            }
        }
    }
     fun nukeDb(){
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
        stationListDao().nukeTable()
        shipDao().nukeTable()}
    }
}

