package net.corpy.corpyecommerce.adapters.viewHolders

import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.Constants
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.login.LoginActivity
import net.corpy.corpyecommerce.adapters.bases.BaseViewHolder
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Data
import net.corpy.corpyecommerce.model.Wishlist
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest

class WishListViewHolder(itemView: View) : BaseViewHolder<Wishlist, ItemClickListener>(itemView) {

    private val departmentName: TextView = itemView.findViewById(R.id.department_name) as TextView
    private val productTitle: TextView = itemView.findViewById(R.id.product_title) as TextView
    private val priceText: TextView = itemView.findViewById(R.id.price_text) as TextView
    private val productImage: ImageView = itemView.findViewById(R.id.product_image) as ImageView
    private val remove_item: ImageView = itemView.findViewById(R.id.remove_item) as ImageButton
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar
    private val addToCartFab: FloatingActionButton = itemView.findViewById(R.id.add_to_cart_fab) as FloatingActionButton

    override fun resetItem() {
        departmentName.text = ""
        productTitle.text = ""
        priceText.text = ""
        productImage.setImageDrawable(null)
    }

    override fun bind(item: Wishlist, listener: ItemClickListener?) {
        remove_item.setOnClickListener {
            listener?.onClick(adapterPosition)
        }
        EcommerceRepo.loadSingleProduct(item.product_id.toString()) { status, data ->
            if (status) {
                populateData(data, listener)
            }
        }
    }

    private fun populateData(data: Data?, listener: ItemClickListener?) {
        EcommerceRepo.loadProductCategory(data?.singleProduct?.dep_id?.toString()
                ?: "") { status, productCat ->
            if (App.getCurrentLanguageKey(itemView.context) == "ar") {
                departmentName.text = productCat?.ar_name ?: ""
            } else {
                departmentName.text = productCat?.en_name ?: ""
            }
        }
        if (App.getCurrentLanguageKey(itemView.context) == "ar") {
            productTitle.text = data?.singleProduct?.ar_title
        } else {
            productTitle.text = data?.singleProduct?.en_title
        }
        priceText.text = data?.singleProduct?.price.toString() + " " + itemView.context.getString(R.string.price_label)
        Picasso.get().load(Constants.BASE_IMAGES_URL + data?.singleProduct?.photo).error(R.drawable.error)
                .fit().into(productImage, object : Callback {
                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }

                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }
                })
        addToCartFab.setOnClickListener {
            if (App.instance.userResponse?.data?.user != null) {
                val addToCartRequest = AddToCartRequest()
                addToCartRequest.productId = data?.singleProduct?.id.toString()
                addToCartRequest.userId = App.instance.userResponse?.data?.user?.id.toString()
                listener?.addToCart(addToCartRequest)
            } else {
                Utils.showWarningDialog(itemView.context,
                        itemView.context.getString(R.string.login),
                        itemView.context.getString(R.string.login_frist),
                        true) {
                    itemView.context.startActivity(Intent(itemView.context, LoginActivity::class.java))
                }
            }
        }
    }

}