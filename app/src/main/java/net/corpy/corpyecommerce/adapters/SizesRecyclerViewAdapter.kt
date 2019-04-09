package net.corpy.corpyecommerce.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.corpy.corpyecommerce.R

class SizesRecyclerViewAdapter(var products: List<String>)
    : RecyclerView.Adapter<SizesRecyclerViewAdapter.ViewHolder>() {
    var currentPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.product_size_item, parent, false))
    }
    override fun getItemCount(): Int {
        return products.size
    }
    fun setSelectedPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.itemView.setOnClickListener {
            setSelectedPosition(position)
        }
        holder.fab_color.text = product
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fab_color = itemView.findViewById(R.id.fab_color) as TextView
    }
}