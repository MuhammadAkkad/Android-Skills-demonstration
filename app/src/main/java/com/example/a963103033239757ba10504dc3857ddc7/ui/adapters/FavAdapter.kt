package com.example.a963103033239757ba10504dc3857ddc7.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a963103033239757ba10504dc3857ddc7.data.model.Point
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.FavoriteListLayoutBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.station.favoriteStation.OnFavClicked
import com.example.a963103033239757ba10504dc3857ddc7.util.TravelHelper


/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
class FavAdapter(private var listener: OnFavClicked) :
    RecyclerView.Adapter<FavAdapter.ViewHolder>() {
    private var favStations: List<StationModel> = listOf()
    private lateinit var context: Context

    inner class ViewHolder(itemView: FavoriteListLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val _itemView = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapter.ViewHolder {
        context = parent.context
        val itemBinding: FavoriteListLayoutBinding = FavoriteListLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: FavAdapter.ViewHolder, position: Int) {
        viewHolder._itemView.nameFavFragment.text = favStations[position].name
        viewHolder._itemView.stockCapacityFavFragment.text =
            favStations[position].capacity.toString() + "/" + favStations[position].stock.toString()
        viewHolder._itemView.distanceFavFragment.text = TravelHelper.distanceCalculator(
            Point(favStations[position].coordinateX, favStations[position].coordinateY),
            Point(0.0, 0.0) // distance from earth
        ).toString()
        viewHolder._itemView.favBtnFavFragment.setOnClickListener {
            listener.onFavClick(favStations[position])
        }
    }

    fun setFavListData(favs: List<StationModel>) {
        this.favStations = favs
        notifyDataSetChanged()
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return favStations.size
    }
}