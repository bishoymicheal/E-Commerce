package net.corpy.corpyecommerce.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.Constants
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Cart
import net.corpy.corpyecommerce.network.EcommerceRepo

class CartAdapter(var products: List<Cart>, var itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.card_layout_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products[position]

        holder.cardPrice.text = product.price.toString() + " " + holder.itemView.context.getString(R.string.price_label)
        holder.cardWeight.text = product.weight.toString()
        holder.remove_item.setOnClickListener {
            itemClickListener.onClick(position)
        }
        EcommerceRepo.loadSingleProduct(product.product_id.toString()) { status, data ->
            if (status) {
                if (App.getCurrentLanguageKey(holder.itemView.context) == "ar") {
                    holder.cardTitle.text = data?.singleProduct?.ar_title
                } else {
                    holder.cardTitle.text = data?.singleProduct?.en_title
                }
                Picasso.get().load(Constants.BASE_IMAGES_URL + data?.singleProduct?.photo).error(R.drawable.error)
                        .fit().into(holder.cardImage, object : Callback {
                            override fun onError(e: Exception?) {
                                holder.progressBar.visibility = View.GONE
                            }

                            override fun onSuccess() {
                                holder.progressBar.visibility = View.GONE
                            }
                        })
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardWeight: TextView = itemView.findViewById(R.id.cardWeight) as TextView
        val cardTitle: TextView = itemView.findViewById(R.id.cardTitle) as TextView
        val cardPrice: TextView = itemView.findViewById(R.id.cardPrice) as TextView
        val cardImage: ImageView = itemView.findViewById(R.id.cardImage) as ImageView
        val remove_item: ImageView = itemView.findViewById(R.id.remove_item) as ImageView
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar
    }


}