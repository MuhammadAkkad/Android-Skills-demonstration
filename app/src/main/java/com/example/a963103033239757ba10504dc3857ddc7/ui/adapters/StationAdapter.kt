package com.example.a963103033239757ba10504dc3857ddc7.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.model.Station
import com.example.a963103033239757ba10504dc3857ddc7.ui.activities.ui.stations.OnListClickListener
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
class StationAdapter(private var listener: OnListClickListener) :
    RecyclerView.Adapter<StationAdapter.ViewHolder>(), Filterable {
    private var stations: List<Station> = listOf()
    private var originalList = listOf<Station>()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val station_list_value1_tv = itemView.findViewById<TextView>(R.id.station_list_value1_tv)
        val station_list_value2_tv = itemView.findViewById<TextView>(R.id.station_list_value2_tv)

        //val fav_btn = itemView.findViewById<TextView>(R.id.fav_btn)
        val list_main_value = itemView.findViewById<TextView>(R.id.list_main_value)

        //val travel_btn = itemView.findViewById<Button>(R.id.travel_btn)
        val back_btn = itemView.findViewById<ImageButton>(R.id.list_back_btn)
        val forward_btn = itemView.findViewById<ImageButton>(R.id.list_forward_btn)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.station_list_layout, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: StationAdapter.ViewHolder, position: Int) {
        viewHolder.station_list_value1_tv.text = stations[position].need.toString()
        viewHolder.station_list_value2_tv.text = stations[position].capacity.toString()
        viewHolder.list_main_value.text = stations[position].name

        if (position == 0)
            viewHolder.back_btn.visibility = View.INVISIBLE
        else
            viewHolder.back_btn.visibility = View.VISIBLE

        if (position == stations.size - 1)
            viewHolder.forward_btn.visibility = View.INVISIBLE
        else
            viewHolder.forward_btn.visibility = View.VISIBLE

        viewHolder.back_btn.setOnClickListener {
            listener.previous(if (position != 0) position - 1 else position)
        }

        viewHolder.forward_btn.setOnClickListener {
            listener.next(if (position != stations.size) position + 1 else position)
        }
    }

    public fun setStationListData(stations: List<Station>) {
        this.stations = stations
        originalList = stations
        notifyDataSetChanged()
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return stations.size
    }

    override fun getFilter(): Filter {
        var filteredList = ArrayList<Station>()
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredList = if (charString.isEmpty()) originalList as ArrayList<Station> else {
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
                    results.values as List<Station>
                }
                notifyDataSetChanged()
            }
        }
    }
}