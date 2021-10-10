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
    var gameOverLive = MutableLiveData<Boolean>()


    fun updateUI() {
        CoroutineScope(Dispatchers.IO).launch {
            shipLiveData.postValue(db.shipDao().getShip())
            stationList.postValue(db.stationListDao().getAll())
        }
    }

    fun isAlreadyFav(station: StationModel): Boolean {
        var s = false
        CoroutineScope(Dispatchers.IO).launch { s = db.stationListDao().isAlreadyFav(station.name) }
        return s
    }

    fun favoriteItem(s: StationModel) {
        CoroutineScope(Dispatchers.IO).launch {
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
    }

    fun unFavoriateItem(s: StationModel) {
        CoroutineScope(Dispatchers.IO).launch {
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
    }

    fun travel(stationModel: StationModel) {
        var gameOver = false
        CoroutineScope(Dispatchers.IO).launch {
            val ship = db.shipDao().getShip() // make sure all operations are done on db data.
            val station = db.stationListDao().get(stationModel.name)
            // region do math then update db
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
            // endregion
            // update db with latest data.
            db.shipDao().update(ship)
            db.stationListDao().update(station)
            // trigger UI observers.
            shipLiveData.postValue(ship)
            stationList.postValue(db.stationListDao().getAll())
            // region GAME OVER
            if (ship.spaceSuitCountUGS <= 0) {
                ship.spaceSuitCountUGS = 0
                gameOver = true
            }

            if (ship.spaceTimeDurationEUS <= 0) {
                ship.spaceTimeDurationEUS = 0
                gameOver = true
            }

            if (ship.durabilityTimeDS <= 0) {
                ship.durabilityTimeDS = 0
                gameOver = true
            }

            if (ship.remainingTime <= 0) {
                ship.remainingTime = 0
                gameOver = true
            }

            if (ship.damageCapacity <= 0) {
                ship.damageCapacity = 0
                gameOver = true
            }

            if (gameOver) gameOverLive.postValue(true)
            // endregion
        }
    }


}