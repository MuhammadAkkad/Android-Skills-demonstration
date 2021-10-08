package com.example.a963103033239757ba10504dc3857ddc7.data.db

import androidx.room.*
import androidx.room.FtsOptions.Order
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel


/**
 * Created by Muhammad AKKAD on 10/7/21.
 */
@Dao
interface ShipDao {
    @Query("SELECT * FROM ship_table")
    suspend fun getShip(): ShipModel

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(ship: ShipModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ship: ShipModel)

    @Delete
    suspend fun delete(ship: ShipModel)

    @Query("DELETE FROM ship_table")
    suspend fun nukeTable()
}