package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FragmentStationsBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.SharedPref
import com.example.a963103033239757ba10504dc3857ddc7.ui.adapters.StationAdapter


class StationsFragment : Fragment(), OnListClickListener {

    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var stationsViewModel: StationsViewModel
    private lateinit var adapter: StationAdapter
    private lateinit var shipObject: ShipModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
        val view = binding.root
        stationsViewModel =
            ViewModelProvider(this).get(StationsViewModel::class.java)
        stationsViewModel.setDb(AppDatabase.getDatabase(context))

        setValues()
        setupSearchFilter()
        setUpLoadingAnimation()
        return view
    }

    private fun setValues() {
        shipObject = stationsViewModel.getShip()

        binding.stationNameTv.text = shipObject.name
        binding.ugsValTv.text = String.format(
            this.getString(R.string.ugs),
            shipObject.durability * 10000
        )
        binding.eusValTv.text = String.format(
            this.getString(R.string.eus),
            shipObject.speed * 20
        )
        binding.dsValTv.text = String.format(
            this.getString(R.string.ds),
            shipObject.capacity * 10000
        )

        binding.damageCapacityTv.text = "100" // default value.
        binding.currentLocationTv.text = shipObject.currentLocation
    }

    private fun setUpLoadingAnimation() {
        stationsViewModel._isLoading.observe(viewLifecycleOwner, Observer {
            if (it) showLoading() else hideLoading()
        })
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

    private fun setupStationList() {
        adapter = StationAdapter(this)
        binding.stationListRv.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(binding.stationListRv)
        binding.stationListRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        stationsViewModel.stationList.observe(viewLifecycleOwner, {
            for (s in it) {
                if (stationsViewModel.isAlreadyFav(s)) {
                    s.isFav = true
                }
            }
            adapter.setStationListData(it)
            SharedPref(this.requireActivity()).setBoolean()
        })
    }

    override fun onStart() {
        super.onStart()
        setupStationList()
        if (!SharedPref(this.requireActivity()).getBoolean())
            stationsViewModel.getStationListFromApi()
        else
            adapter.setStationListData(stationsViewModel.getStationListFromDb())
    }

    override fun next(position: Int) {
        binding.stationListRv.smoothScrollToPosition(position)
    }

    override fun previous(position: Int) {
        binding.stationListRv.smoothScrollToPosition(position)
    }

    override fun addToFav(station: StationModel) {
        if (!stationsViewModel.isAlreadyFav(station))
            stationsViewModel.addToFavDbList(station)
        else
            stationsViewModel.deleteFromFavDbList(station)

    }

    private fun hideLoading() {
        binding.contentLoadingProgressBar.visibility = View.INVISIBLE
    }

    private fun showLoading() {
        binding.contentLoadingProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

