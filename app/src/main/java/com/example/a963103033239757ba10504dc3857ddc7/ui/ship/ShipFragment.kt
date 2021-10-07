package com.example.a963103033239757ba10504dc3857ddc7.ui.ship

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.databinding.ShipFragmentBinding
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
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueBtnShipFragment.setOnClickListener {
            if (!binding.totalPointsValueShipFragment.text.equals("15"))
                Snackbar.make(view, "Dagitilacak puan 15 olmalidir.", Snackbar.LENGTH_SHORT)
                    .show()
            else
                findNavController().navigate(R.id.action_shipFragment_to_navigation_stations)
        }

        viewModel = ViewModelProvider(this).get(ShipViewModel::class.java)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

}