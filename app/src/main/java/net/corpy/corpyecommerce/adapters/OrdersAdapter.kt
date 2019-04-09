package net.corpy.corpyecommerce.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.interfaces.OrderClickListener
import net.corpy.corpyecommerce.model.Order

class OrdersAdapter(var products: List<Order>, var productClickListener: OrderClickListener) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.orders_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products[position]

        holder.itemView.setOnClickListener { productClickListener.onOrderClicked(product) }


        holder.order_code.text = product.code
        holder.order_date.text = product.created_at
        holder.order_price.text = product.price.toString() + " " + holder.itemView.context.getString(R.string.price_label)


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val order_code: TextView = itemView.findViewById(R.id.order_code) as TextView
        val order_date: TextView = itemView.findViewById(R.id.order_date) as TextView
        val order_price: TextView = itemView.findViewById(R.id.order_price) as TextView
    }

}