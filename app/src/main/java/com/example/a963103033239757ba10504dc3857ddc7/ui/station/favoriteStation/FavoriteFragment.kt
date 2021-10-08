package com.example.a963103033239757ba10504dc3857ddc7.ui.station.favoriteStation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FragmentFavoriteBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.adapters.FavAdapter


class FavoriteFragment : Fragment(), OnFavClicked {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var viewModelFactory: ViewModelFactory
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
        /*favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel(AppDatabase.getDatabase(context))::class.java)*/

        viewModelFactory = ViewModelFactory(AppDatabase.getDatabase(context))
        favoriteViewModel = ViewModelProvider(this, viewModelFactory)
            .get(FavoriteViewModel::class.java)

        return view
    }

    override fun onStart() {
        super.onStart()
        favoriteViewModel.getAllFavs()
        favoriteViewModel.favStationList.observe(viewLifecycleOwner, Observer {
            adapter.setFavListData(it)
            setUpEmptyListLayout()
        })
    }

    private fun setUpEmptyListLayout() {
        favoriteViewModel.isEmptyList.observe(viewLifecycleOwner, Observer {
            when (favoriteViewModel.favStationList.value?.isEmpty()) {
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

    override fun onFavClick(favStation: StationModel) {
        favoriteViewModel.deleteFromFavDbList(favStation)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}