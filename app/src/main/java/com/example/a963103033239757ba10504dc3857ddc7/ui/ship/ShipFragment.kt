package com.example.a963103033239757ba10504dc3857ddc7.main.ui.ship

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.ShipFragmentBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.ship.ShipViewModel
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar

class ShipFragment : Fragment() {

    private lateinit var viewModel: ShipViewModel
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
        viewModel = ViewModelProvider(this).get(ShipViewModel::class.java)
        viewModel.setDb(AppDatabase.getDatabase(context))
        setOnCountinueBtnClick()

        viewModel._isLoading.observe(viewLifecycleOwner, Observer {
            if (it) showLoading() else hideLoading()
        })
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
        val damageCapacity = binding.damageCapacitySliderShipFragment.value.toInt()
        val speed = binding.speedSliderShipFragment.value.toInt()
        val capacity = binding.capacitySliderShipFragment.value.toInt()
        viewModel.saveShipData(
            ShipModel(
                name = shipName.toString(),
                damageCapacity = damageCapacity,
                speed = speed,
                capacity = capacity,
                durability = 100,
                remainingTime = 0,
                currentLocation = "DUNYA" // TODO get real data
            )
        )
        openStationsFragment()
    }

    private fun openStationsFragment() {
        findNavController().navigate(R.id.action_shipFragment_to_navigation_stations)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData()
        viewModel.sum.observe(viewLifecycleOwner, Observer {
            binding.totalPointsValueShipFragment.text = it.toString()
        })

        binding.damageCapacitySliderShipFragment.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            run {
                viewModel.listenToValue(1, value.toInt())
            }
        })

        binding.speedSliderShipFragment.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            run {
                viewModel.listenToValue(2, value.toInt())
            }
        })

        binding.capacitySliderShipFragment.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            run {
                viewModel.listenToValue(3, value.toInt())
            }
        })
    }

    private fun hideLoading() {
        binding.contentLoadingProgressBar.visibility = View.INVISIBLE
    }

    private fun showLoading() {
        binding.contentLoadingProgressBar.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}