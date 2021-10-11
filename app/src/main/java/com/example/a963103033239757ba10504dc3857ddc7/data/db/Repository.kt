package com.example.a963103033239757ba10504dc3857ddc7.data.db

import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import javax.inject.Inject

/**
 * Created by Muhammad AKKAD on 10/11/21.
 */
class Repository @Inject constructor(var shipDAO: ShipDao, var stationDAO: StationListDao) {

    // Ship Repo
    suspend fun getShip() = shipDAO.getShip()
    suspend fun updateShip(ship: ShipModel) = shipDAO.update(ship)
    suspend fun insertShip(ship: ShipModel) = shipDAO.insert(ship)
    suspend fun deleteShip(ship: ShipModel) = shipDAO.delete(ship)
    suspend fun nukeShip() = shipDAO.nukeTable()

    // Station Repo
    suspend fun getStation(name: String) = stationDAO.get(name)
    suspend fun getFavStation(isFav: Boolean) = stationDAO.getFav(isFav)
    suspend fun getAllStations() = stationDAO.getAll()
    suspend fun insertStation(station: StationModel) = stationDAO.insert(station)
    suspend fun insertAllStation(list: List<StationModel?>?) = stationDAO.insertAll(list)
    suspend fun updateStation(station: StationModel) = stationDAO.update(station)
    suspend fun deleteStation(station: StationModel) = stationDAO.delete(station)
    suspend fun nukeStation() = stationDAO.nukeTable()
    suspend fun isAlreadyFavStation(name: String) = stationDAO.isAlreadyFav(name)
}