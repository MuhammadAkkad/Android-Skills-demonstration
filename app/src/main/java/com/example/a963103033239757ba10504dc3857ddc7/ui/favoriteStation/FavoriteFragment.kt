package com.example.a963103033239757ba10504dc3857ddc7.ui.favoriteStation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root


        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.text.observe(viewLifecycleOwner, Observer {
          binding.textFavorite.text = it
        })
        return view
    }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}