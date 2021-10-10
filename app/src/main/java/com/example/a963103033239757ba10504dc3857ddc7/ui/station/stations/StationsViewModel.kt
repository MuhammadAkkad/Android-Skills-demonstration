package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.Point
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.util.TravelHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationsViewModel(database: AppDatabase) : ViewModel() {

    private var db = database
    val stationList = MutableLiveData<List<StationModel>>()
    val shipLiveData = MutableLiveData<ShipModel>()

    fun getStationListFromDb() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            stationList.postValue(db.stationListDao().getAll())
        }
    }

    fun saveStationListToDb(list: List<StationModel>?) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { db.stationListDao().insertAll(list) }
    }


    fun isAlreadyFav(station: StationModel): Boolean {
        var s = false
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { s = db.stationListDao().isAlreadyFav(station.name) }
        return s
    }

    fun favoriteItem(s: StationModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            db.stationListDao().update(
                StationModel(
                    s.name,
                    s.coordinateX,
                    s.coordinateY,
                    s.capacity,
                    s.stock,
                    s.need,
                    s.distance,
                    s.isFav
                )
            )
        }
        // TODO find a better solution
    }

    fun unFavoriateItem(s: StationModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            db.stationListDao().update(
                StationModel(
                    s.name,
                    s.coordinateX,
                    s.coordinateY,
                    s.capacity,
                    s.stock,
                    s.need,
                    s.distance,
                    s.isFav
                )
            )
        }
        // TODO find a better solution
    }

    fun getShip() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { shipLiveData.postValue(db.shipDao().getShip()) }
    }

    fun saveShipData(shipModel: ShipModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            db.shipDao().insert(shipModel)
        }
    }

    fun travel(stationModel: StationModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val ship = db.shipDao().getShip() // make sure all operations are done on db data.
            val station = db.stationListDao().get(stationModel.name)

            // do math then update db
            // UGS kalan malzemeler
            if (ship.spaceSuitCountUGS != 0) {
                val givenAmount =
                    if (ship.spaceSuitCountUGS >= station.need) station.need else ship.spaceSuitCountUGS
                station.stock += givenAmount
                station.need -= givenAmount
                ship.spaceSuitCountUGS -= givenAmount
            }

            // Current location
            ship.currentLocation = station.name

            // mesafe
            val distance = TravelHelper.distanceCalculator(
                Point(station.coordinateX, station.coordinateY),
                Point(ship.x, ship.y)
            )

            // ship EUS
            ship.spaceTimeDurationEUS -= distance

            // ship kalan zaman
            ship.remainingTime -= distance

            // ship DS
            ship.durabilityTimeDS -= (distance * 1000)

            // ship te kalan malzeme
            ship.damageCapacity -= 10


            // ship kordinat guncelleme
            ship.x = station.coordinateX
            ship.y = station.coordinateY


            station.distance = TravelHelper.distanceCalculator(
                Point(station.coordinateX, station.coordinateY),
                Point(ship.x, ship.y)
            )



            db.shipDao().update(ship) // update db with latest data.
            db.stationListDao().update(station)

            shipLiveData.postValue(ship)
            stationList.postValue(db.stationListDao().getAll())  // trigger UI observers.
        }
    }
}