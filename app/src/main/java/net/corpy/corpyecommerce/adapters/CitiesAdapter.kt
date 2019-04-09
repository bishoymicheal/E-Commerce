package net.corpy.corpyecommerce.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.model.Cities

class CitiesAdapter(var cities: List<Cities>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val holder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cities_list_item, parent, false)
            holder = ViewHolder(view!!)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        if (App.getCurrentLanguageKey(parent.context) == "ar")
            holder.countrieName.text = cities[position].country_name_ar
        else{
            holder.countrieName.text = cities[position].country_name_en
        }

        return view
    }



    internal class ViewHolder(view: View) {
        val countrieName: TextView = view.findViewById(R.id.country_name)

    }

    override fun getItem(p0: Int): Any {
        return cities[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return cities.size
    }

}