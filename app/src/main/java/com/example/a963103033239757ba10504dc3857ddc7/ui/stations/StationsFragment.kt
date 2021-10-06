package com.example.a963103033239757ba10504dc3857ddc7.ui.stations

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
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FragmentStationsBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.adapters.StationAdapter


class StationsFragment : Fragment(), OnListClickListener {

    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var stationsViewModel: StationsViewModel
    private lateinit var adapter: StationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
        val view = binding.root
        stationsViewModel =
            ViewModelProvider(this).get(StationsViewModel::class.java)


        setupStationList()
        setupSearchFilter()
        setupTextObserver()
        setUpLoadingAnimation()
        return view
    }

    private fun setUpLoadingAnimation() {
        stationsViewModel._isLoading.observe(viewLifecycleOwner, Observer {
            if (it) showLoading() else hideLoading()
        })
    }

    private fun setupTextObserver() {
        stationsViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textStationsValue.text = it
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
        stationsViewModel.getStationList()
        LinearSnapHelper().attachToRecyclerView(binding.stationListRv)
        binding.stationListRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        stationsViewModel.stationList.observe(viewLifecycleOwner, {
            adapter.setStationListData(it)
        })
    }

    override fun next(position: Int) {
        binding.stationListRv.smoothScrollToPosition(position)
    }

    override fun previous(position: Int) {
        binding.stationListRv.smoothScrollToPosition(position)
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

