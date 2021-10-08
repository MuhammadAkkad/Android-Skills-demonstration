package com.example.a963103033239757ba10504dc3857ddc7.main.data.db

import androidx.room.*
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel


/**
 * Created by Muhammad AKKAD on 10/7/21.
 */
@Dao
interface StationListDao {

    @Query("SELECT * FROM station_list_table WHERE name=:name ")
    suspend fun get(name: String): StationModel

    @Query("SELECT * FROM station_list_table WHERE isFav=:isFav ")
    suspend fun getFav(isFav: Boolean): List<StationModel>

    @Query("SELECT * FROM station_list_table")
    suspend fun getAll(): List<StationModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(station: StationModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<StationModel?>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(station: StationModel)

    @Delete
    suspend fun delete(station: StationModel)

    @Query("DELETE FROM station_list_table")
    suspend fun nukeTable()

    @Query("SELECT EXISTS(SELECT * FROM station_list_table WHERE name = :name)")
    suspend fun isAlreadyFav(name: String): Boolean
}