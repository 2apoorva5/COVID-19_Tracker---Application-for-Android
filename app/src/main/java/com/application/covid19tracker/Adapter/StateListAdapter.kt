package com.application.covid19tracker.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.application.covid19tracker.Model.StatewiseItem
import com.application.covid19tracker.R
import kotlinx.android.synthetic.main.state_list_item.view.*

class StateListAdapter(val list: List<StatewiseItem>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
                ?: LayoutInflater.from(parent.context).inflate(R.layout.state_list_item, parent, false)
        val item = list[position]

        view.cnfmd_value.text = SpannableDelta(
                "${item.confirmed} ↑ ${item.deltaconfirmed ?: "0"}",
                "#FF0000",
                item.confirmed?.length ?: 0
        )

        view.actv_value.text = item.active

        view.rcvrd_value.text = SpannableDelta(
                "${item.recovered} ↑ ${item.deltarecovered ?: "0"}",
                "#00FF00",
                item.recovered?.length ?: 0
        )

        view.dcsd_value.text = SpannableDelta(
                "${item.deaths} ↑ ${item.deltadeaths ?: "0"}",
                "#FFFF00",
                item.deaths?.length ?: 0
        )

        view.state_name.text = item.state
        return view
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount(): Int = list.size

}