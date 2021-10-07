package com.example.a963103033239757ba10504dc3857ddc7.data.db

import androidx.room.*
import androidx.room.FtsOptions.Order
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel


/**
 * Created by Muhammad AKKAD on 10/7/21.
 */
@Dao
interface StationListDao {
    @Query("SELECT * FROM station_list_table")
    fun getAll(): List<StationModel>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(station: StationModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<StationModel?>?)

    @Delete
    fun delete(station: StationModel)

    @Query("DELETE FROM station_list_table")
    fun nukeTable()
}