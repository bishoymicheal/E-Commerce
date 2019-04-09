package net.corpy.corpyecommerce.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.corpy.corpyecommerce.R

class ColorsRecyclerViewAdapter(var products: List<String>)
    : RecyclerView.Adapter<ColorsRecyclerViewAdapter.ViewHolder>() {
    var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.product_color_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setSelectedPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: String = products[position]
        holder.itemView.setOnClickListener {
            setSelectedPosition(position)
        }
        try {
            holder.fab_color.show()
            holder.text_color.visibility = View.GONE
            holder.fab_color.backgroundTintList = ColorStateList.valueOf(Color.parseColor(product))
        } catch (e: Exception) {
            holder.fab_color.hide()
            holder.text_color.visibility = View.VISIBLE
            holder.text_color.text = product.toString()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fab_color = itemView.findViewById(R.id.fab_color) as FloatingActionButton
        val text_color = itemView.findViewById(R.id.text_color) as TextView
    }


}