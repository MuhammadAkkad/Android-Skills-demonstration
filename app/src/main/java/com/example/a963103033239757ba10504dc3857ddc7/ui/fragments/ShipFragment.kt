package com.example.a963103033239757ba10504dc3857ddc7.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.ShipFragmentBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.viewmodels.ShipViewModel
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response

@AndroidEntryPoint
class ShipFragment : Fragment() {
    private val viewModel: ShipViewModel by viewModels()
    private var _binding: ShipFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShipFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnCountinueBtnClick()
    }


    private fun setOnCountinueBtnClick() {
        binding.continueBtnShipFragment.setOnClickListener {
            var valid = true

            if (!binding.totalPointsValueShipFragment.text.equals("15")) {
                view?.let { it1 ->
                    Snackbar.make(it1, "Dagitilacak puan 15 olmalidir.", Snackbar.LENGTH_SHORT)
                        .show()
                }
                valid = false
                binding.shipNameEtShipFragment.requestFocus()
            }

            if (binding.shipNameEtShipFragment.text.isEmpty()) {
                binding.shipNameEtShipFragment.error = getString(R.string.cant_be_empty)
                valid = false
            }

            if (valid)
                saveShipToDb()

        }

        binding.shipNameEtShipFragment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.shipNameEtShipFragment.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun saveShipToDb() {
        val shipName = binding.shipNameEtShipFragment.text.toString()
        val durailioty = binding.durabilitySliderShipFragment.value.toInt()
        val speed = binding.speedSliderShipFragment.value.toInt()
        val capacity = binding.capacitySliderShipFragment.value.toInt()
        viewModel.saveShipData(
            ShipModel(
                name = shipName.toString(),
                speed = speed,
                capacity = capacity,
                durability = durailioty,
            )
        )
        openStationsFragment()
    }

    private fun openStationsFragment() {
        findNavController().navigate(R.id.action_shipFragment_to_navigation_stations)
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkForDataAvailability()

        viewModel.getStationListFromApi().observe(viewLifecycleOwner, Observer {
            it.enqueue(object : retrofit2.Callback<List<StationModel>> {
                override fun onResponse(
                    call: Call<List<StationModel>>,
                    response: Response<List<StationModel>>
                ) {
                    viewModel.saveStationListToDb(response.body())
                    viewModel.list = response.body()!!
                }

                override fun onFailure(call: Call<List<StationModel>>, t: Throwable) {
                }
            })
        })
        viewModel.sum.observe(viewLifecycleOwner, Observer {
            binding.totalPointsValueShipFragment.text = it.toString()
        })

        binding.durabilitySliderShipFragment.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            run {
                viewModel.listenToValue(1, value.toInt())
            }
        })

        binding.speedSliderShipFragment.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            run {
                viewModel.listenToValue(2, value.toInt())
            }
        })

        binding.capacitySliderShipFragment.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            run {
                viewModel.listenToValue(3, value.toInt())
            }
        })

        viewModel._isLoading.observe(viewLifecycleOwner, Observer {
            if (it) showLoading() else hideLoading()
        })
    }

    private fun hideLoading() {
        binding.contentLoadingProgressBar.visibility = View.INVISIBLE
        binding.continueBtnShipFragment.isEnabled = true
        binding.continueBtnShipFragment.text = getString(R.string.devam_et)
    }

    private fun showLoading() {
        binding.contentLoadingProgressBar.visibility = View.VISIBLE
        binding.continueBtnShipFragment.isEnabled = false
        binding.continueBtnShipFragment.text = getString(R.string.loading)
    }
}