package com.example.a963103033239757ba10504dc3857ddc7.ui.activities.ui.stations

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.ui.adapters.StationAdapter


class StationsFragment : Fragment(), OnListClickListener {

    private lateinit var stationsViewModel: StationsViewModel
    private lateinit var stationListRv: RecyclerView
    private lateinit var search_et: EditText
    private lateinit var adapter: StationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        stationsViewModel =
            ViewModelProvider(this).get(StationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stations, container, false)
        val textView: TextView = root.findViewById(R.id.text_stations)
        stationListRv = root.findViewById(R.id.station_list_rv)
        search_et = root.findViewById(R.id.station_search_et)
        adapter = StationAdapter(this)
        stationsViewModel.getStationList()
        setupStationList()
        setupSearchFilter()
        stationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    private fun setupSearchFilter() {
        search_et.addTextChangedListener(object : TextWatcher {
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

        stationListRv.setAdapter(adapter)
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(stationListRv)
        //stationListRv.setLayoutManager(LinearLayoutManager(context))
        stationListRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        stationsViewModel.stationList.observe(viewLifecycleOwner, {
            adapter.setStationListData(it)
        })
    }

    override fun next(position: Int) {
        stationListRv.smoothScrollToPosition(position)
    }

    override fun previous(position: Int) {
        stationListRv.smoothScrollToPosition(position)
    }


}

private fun EditText.addTextChangedListener(function: () -> Unit) {

}
