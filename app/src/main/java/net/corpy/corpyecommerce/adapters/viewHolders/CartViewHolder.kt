package net.corpy.corpyecommerce.adapters.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.Constants
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.adapters.bases.BaseViewHolder
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Cart
import net.corpy.corpyecommerce.network.EcommerceRepo

class CartViewHolder(itemView: View) : BaseViewHolder<Cart, ItemClickListener>(itemView) {

    val cardWeight: TextView = itemView.findViewById(R.id.cardWeight) as TextView
    val cardTitle: TextView = itemView.findViewById(R.id.cardTitle) as TextView
    val cardPrice: TextView = itemView.findViewById(R.id.cardPrice) as TextView
    val cardImage: ImageView = itemView.findViewById(R.id.cardImage) as ImageView
    val remove_item: ImageView = itemView.findViewById(R.id.remove_item) as ImageView
    val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar

    override fun bind(item: Cart, listener: ItemClickListener?) {
        cardPrice.text = item.price.toString() + " " + itemView.context.getString(R.string.price_label)
        cardWeight.text = item.weight.toString()
        remove_item.setOnClickListener {
            listener?.onClick(adapterPosition)
        }
        EcommerceRepo.loadSingleProduct(item.product_id.toString()) { status, data ->
            if (status) {
                if (App.getCurrentLanguageKey(itemView.context) == "ar") {
                    cardTitle.text = data?.singleProduct?.ar_title
                } else {
                    cardTitle.text = data?.singleProduct?.en_title
                }
                Picasso.get().load(Constants.BASE_IMAGES_URL + data?.singleProduct?.photo).error(R.drawable.error)
                        .fit().into(cardImage, object : Callback {
                            override fun onError(e: Exception?) {
                                progressBar.visibility = View.GONE
                            }

                            override fun onSuccess() {
                                progressBar.visibility = View.GONE
                            }
                        })
            }
        }
    }

    override fun resetItem() {
        cardWeight.text = ""
        cardTitle.text = ""
        cardPrice.text = ""
        cardImage.setImageDrawable(null)
    }
}