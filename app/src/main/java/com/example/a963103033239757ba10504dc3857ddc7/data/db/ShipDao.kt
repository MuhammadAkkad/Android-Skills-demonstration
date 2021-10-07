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
    fun getShip(): ShipModel

    @Update
    fun update(order: ShipModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ship: ShipModel)

    @Delete
    fun delete(ship: ShipModel)

    @Query("DELETE FROM ship_table")
    fun nukeTable()
}