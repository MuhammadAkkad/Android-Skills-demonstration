package com.example.a963103033239757ba10504dc3857ddc7.data.db

import androidx.room.*
import com.example.a963103033239757ba10504dc3857ddc7.data.model.FavStationModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel

/**
 * Created by Muhammad AKKAD on 10/6/21.
 */
@Dao
interface FavStationDao {
    @Query("SELECT * FROM fav_station_table")
    fun getAll(): List<FavStationModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(station: FavStationModel)

    @Query("SELECT EXISTS(SELECT * FROM fav_station_table WHERE name = :name)")
    fun isAlreadyFav(name: String): Boolean

    @Delete
    fun delete(station: FavStationModel)

    @Query("DELETE FROM fav_station_table")
    fun nukeTable()
}