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
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FragmentStationsBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.adapters.StationAdapter
import com.example.a963103033239757ba10504dc3857ddc7.ui.station.favoriteStation.ViewModelFactory
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
            for (s in it) {
                if (viewModel.isAlreadyFav(s)) {
                    s.isFav = true
                }
            }
            adapter.setStationListData(it)  // refresh adapter accordingly.
        })

        viewModel.shipLiveData.observe(viewLifecycleOwner, {
            shipObject = it
            binding.ugsValTv.text = String.format(
                this.getString(R.string.ugs), shipObject.spaceSuitCountUGS
            )
            binding.eusValTv.text = String.format(
                this.getString(R.string.eus), shipObject.spaceTimeDurationEUS
            )
            binding.dsValTv.text = String.format(
                this.getString(R.string.ds), shipObject.durabilityTimeDS
            )
            binding.damageCapacityTv.text = shipObject.damageCapacity.toString()
            binding.currentLocationTv.text = shipObject.currentLocation
            binding.stationNameTv.text = shipObject.name
        })
        viewModel.getShip()
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
        viewModel.getStationListFromDb()
        setObservers()
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
        if (shipObject.currentLocation.toLowerCase(Locale.ROOT) != station.name.toLowerCase(Locale.ROOT))
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

