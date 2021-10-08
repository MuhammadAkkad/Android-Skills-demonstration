package com.example.a963103033239757ba10504dc3857ddc7.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import com.example.a963103033239757ba10504dc3857ddc7.databinding.SpaceStationListLayoutBinding
import com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations.OnListClickListener
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
class StationAdapter(
    private var listener: OnListClickListener,
) :
    RecyclerView.Adapter<StationAdapter.ViewHolder>(), Filterable {
    private var stations: List<StationModel> = listOf()
    private var originalList = listOf<StationModel>()
    private lateinit var context: Context

    inner class ViewHolder(itemView: SpaceStationListLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val _itemView = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationAdapter.ViewHolder {
        context = parent.context
        val itemBinding: SpaceStationListLayoutBinding = SpaceStationListLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: StationAdapter.ViewHolder, position: Int) {
        viewHolder._itemView.stationListCapacityTv.text = String.format(
            context.getString(R.string.capacity_and_stock),
            stations[position].capacity,
            stations[position].stock
        )
        viewHolder._itemView.universalSpaceTimeTv.text =
            String.format(
                context.getString(R.string.item_eus),
                stations[position].need,
            ) // TODO correct value needed

        viewHolder._itemView.spaceStationName.text = stations[position].name
        setupFavBtn(position, viewHolder)
        setupTravelBtn(position, viewHolder)
        setupPositioningButtons(position, viewHolder)
    }

    private fun setupTravelBtn(position: Int, viewHolder: StationAdapter.ViewHolder) {
        viewHolder._itemView.travelBtn.setOnClickListener {
            listener.onTravelClick(stations[position])
        }
    }

    private fun setupFavBtn(position: Int, viewHolder: StationAdapter.ViewHolder) {
        // fav button style.
        if (stations[position].isFav) {
            viewHolder._itemView.favBtn.setBackgroundResource(R.drawable.ic_star)
        } else {
            viewHolder._itemView.favBtn.setBackgroundResource(R.drawable.ic_star_outline)
        }

        // fav button click logic.
        viewHolder._itemView.favBtn.setOnClickListener {
            stations[position].isFav = !stations[position].isFav // reverse state.
            listener.addToFav(stations[position])
            setStationListData(stations)// refresh UI.
        }
    }

    private fun setupPositioningButtons(position: Int, viewHolder: ViewHolder) {
        // arrow buttons visibility.
        if (position == 0)
            viewHolder._itemView.listBackBtn.visibility = View.INVISIBLE
        else
            viewHolder._itemView.listBackBtn.visibility = View.VISIBLE

        if (position == stations.size - 1)
            viewHolder._itemView.listForwardBtn.visibility = View.INVISIBLE
        else
            viewHolder._itemView.listForwardBtn.visibility = View.VISIBLE

        // arrow buttons click logic.
        viewHolder._itemView.listBackBtn.setOnClickListener {
            listener.previous(if (position != 0) position - 1 else position)
        }

        viewHolder._itemView.listForwardBtn.setOnClickListener {
            listener.next(if (position != stations.size) position + 1 else position)
        }
    }

    fun setStationListData(stations: List<StationModel>) {
        this.stations = stations
        originalList = stations // copy of the original for getFilter().
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    override fun getFilter(): Filter {
        var filteredList = ArrayList<StationModel>()
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredList =
                    if (charString.isEmpty()) originalList as ArrayList<StationModel> else {
                        originalList
                            .filter {
                                (it.name.toLowerCase(Locale.ROOT)
                                    .contains(
                                        constraint.toString().toLowerCase(Locale.ROOT)
                                    ))
                            }
                            .forEach { filteredList.add(it) }
                        filteredList
                    }
                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                stations = if (results?.values == null) {
                    originalList
                } else {
                    results.values as List<StationModel>
                }
                notifyDataSetChanged()
            }
        }
    }
}