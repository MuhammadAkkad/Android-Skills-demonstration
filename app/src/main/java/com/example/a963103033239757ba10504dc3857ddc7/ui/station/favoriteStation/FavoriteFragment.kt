package com.example.a963103033239757ba10504dc3857ddc7.ui.station.favoriteStation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a963103033239757ba10504dc3857ddc7.data.db.FavStationDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FragmentFavoriteBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.adapters.FavAdapter


class FavoriteFragment : Fragment(), OnFavClicked {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        setupFavList()
        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel()::class.java)
        favoriteViewModel.setDb(FavStationDatabase.getDatabase(context))
        favoriteViewModel.getAllFavs()
        favoriteViewModel.stationList.observe(viewLifecycleOwner, Observer {
            adapter.setFavListData(it)
            setUpEmptyListLayout()
        })
        return view
    }

    private fun setUpEmptyListLayout() {
        favoriteViewModel.isEmptyList.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> binding.favListIsEmptyEt.visibility = View.VISIBLE
                false -> binding.favListIsEmptyEt.visibility = View.INVISIBLE
            }
        })
    }

    private fun setupFavList() {
        val favListRv = binding.favListRv
        adapter = FavAdapter(this)
        favListRv.adapter = adapter
        favListRv.layoutManager = LinearLayoutManager(context)
    }

    override fun onFavClick(station: StationModel) {
        favoriteViewModel.deleteFromFavDbList(station)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}