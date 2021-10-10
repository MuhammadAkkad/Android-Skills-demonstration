package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.*
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FragmentStationsBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.adapters.StationAdapter
import com.example.a963103033239757ba10504dc3857ddc7.ui.station.favoriteStation.ViewModelFactory
import com.example.a963103033239757ba10504dc3857ddc7.util.TravelHelper
import com.google.android.material.snackbar.Snackbar
import java.util.*


class StationsFragment : Fragment(), OnListClickListener {

    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StationsViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var adapter: StationAdapter
    private lateinit var shipObject: ShipModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelFactory = ViewModelFactory(AppDatabase.getDatabase(context))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StationsViewModel::class.java)
        return view
    }

    private fun setObservers() {
        viewModel.stationList.observe(viewLifecycleOwner, {
            var distance: Int?
            for (s in it) {
                if (viewModel.isAlreadyFav(s)) {
                    s.isFav = true
                }
                distance = TravelHelper.distanceCalculator(
                    Point(s.coordinateX, s.coordinateY),
                    Point(shipObject.x, shipObject.y)
                )
                s.distance = distance
            }

            adapter.setStationListData(it)  // refresh adapter accordingly.
        })

        viewModel.shipLiveData.observe(viewLifecycleOwner, {
            shipObject = it
            binding.ugsValTv.text = String.format(
                this.getString(R.string.ugs), it.spaceSuitCountUGS
            )
            binding.eusValTv.text = String.format(
                this.getString(R.string.eus), it.spaceTimeDurationEUS
            )
            binding.dsValTv.text = String.format(
                this.getString(R.string.ds), it.durabilityTimeDS
            )
            binding.damageCapacityTv.text = it.damageCapacity.toString()
            binding.currentLocationTv.text = it.currentLocation
            binding.stationNameTv.text = it.name
            binding.remainingTimeTv.text = it.remainingTime.toString()
            checkForGameOver()
        })

        viewModel.gameOverLive.observe(viewLifecycleOwner, {
            if (it) {
                binding.listRvContainer.visibility = View.INVISIBLE
                binding.currentLocationTv.text = getString(R.string.gameOver)
                viewModel.clearStationList()
            }
        })

        viewModel.resStateLive.observe(viewLifecycleOwner, {
            if (!it.errorType.equals(ErrorType.SUCCESS.errMsg))
                Snackbar.make(binding.root, it.errorType, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun checkForGameOver() {
        if (shipObject.damageCapacity == 0 ||
            shipObject.spaceTimeDurationEUS == 0 ||
            shipObject.spaceSuitCountUGS == 0 ||
            shipObject.durabilityTimeDS == 0 ||
            shipObject.remainingTime == 0
        ) {
            viewModel.gameOverLive.value = true
        }
    }

    private fun setupSearchFilter() {
        binding.stationSearchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = StationAdapter(this)
        binding.stationListRv.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(binding.stationListRv)
        binding.stationListRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        setObservers()
        viewModel.getData()
        /*      if (!viewModel.gameOverLive.value!!)
                  binding.currentLocationTv.text = getString(R.string.gameOver)*/
        setupSearchFilter()
    }

    override fun next(position: Int) {
        binding.stationListRv.smoothScrollToPosition(position)
    }

    override fun previous(position: Int) {
        binding.stationListRv.smoothScrollToPosition(position)
    }

    override fun addToFav(station: StationModel) {
        if (!viewModel.isAlreadyFav(station))
            viewModel.favoriteItem(station)
        else
            viewModel.unFavoriateItem(station)

    }

    override fun onTravelClick(station: StationModel) {
        if (shipObject.currentLocation.toLowerCase(Locale.ROOT) != station.name.toLowerCase(Locale.ROOT) && station.capacity != station.stock)
            viewModel.travel(station)
        else if (station.stock == station.capacity) {
            Snackbar.make(binding.root, "${station.name} doesn't need stock", Snackbar.LENGTH_SHORT)
                .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

