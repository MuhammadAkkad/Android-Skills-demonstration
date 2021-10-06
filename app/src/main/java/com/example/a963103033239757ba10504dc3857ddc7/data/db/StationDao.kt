package com.example.a963103033239757ba10504dc3857ddc7.data.db

import androidx.room.*
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel

/**
 * Created by Muhammad AKKAD on 10/6/21.
 */
@Dao
interface StationDao {
    @Query("SELECT * FROM station_table")
    fun getAll(): List<StationModel>

    @Query("SELECT EXISTS(SELECT * FROM station_table WHERE name = :name)")
    fun isAlreadyFav(name: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(station: StationModel)

    @Delete
    fun delete(station: StationModel)
}