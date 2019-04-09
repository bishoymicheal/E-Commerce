package net.corpy.corpyecommerce.adapters

import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Data
import net.corpy.corpyecommerce.model.Wishlist
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest


class WishListAdapter(var products: List<Wishlist>, var itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.wishlist_product_layout_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.remove_item.setOnClickListener {
            itemClickListener.onClick(position)
        }
        EcommerceRepo.loadSingleProduct(product.product_id.toString()) { status, data ->
            if (status) {
                populateData(holder, data)
            }
        }
    }



    private fun populateData(holder: ViewHolder, data: Data?) {
        EcommerceRepo.loadProductCategory(data?.singleProduct?.dep_id?.toString()
                ?: "") { status, productCat ->
            if (App.getCurrentLanguageKey(holder.itemView.context) == "ar") {
                holder.departmentName.text = productCat?.ar_name ?: ""
            } else {
                holder.departmentName.text = productCat?.en_name ?: ""
            }
        }
        if (App.getCurrentLanguageKey(holder.itemView.context) == "ar") {
            holder.productTitle.text = data?.singleProduct?.ar_title
        } else {
            holder.productTitle.text = data?.singleProduct?.en_title
        }
        holder.priceText.text = data?.singleProduct?.price.toString() + " " + holder.itemView.context.getString(R.string.price_label)
        Picasso.get().load(Constants.BASE_IMAGES_URL + data?.singleProduct?.photo).error(R.drawable.error)
                .fit().into(holder.productImage, object : Callback {
                    override fun onError(e: Exception?) {
                        holder.progressBar.visibility = View.GONE
                    }

                    override fun onSuccess() {
                        holder.progressBar.visibility = View.GONE
                    }
                })
        holder.addToCartFab.setOnClickListener {
            if (App.instance.userResponse?.data?.user != null) {
                val addToCartRequest = AddToCartRequest()
                addToCartRequest.productId = data?.singleProduct?.id.toString()
                addToCartRequest.userId = App.instance.userResponse?.data?.user?.id.toString()
                itemClickListener.addToCart(addToCartRequest)
            } else {
                Utils.showWarningDialog(holder.itemView.context,
                        holder.itemView.context.getString(R.string.login),
                        holder.itemView.context.getString(R.string.login_frist),
                        true) {
                    holder.itemView.context.startActivity(Intent(holder.itemView.context, LoginActivity::class.java))
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val departmentName: TextView = itemView.findViewById(R.id.department_name) as TextView
        val productTitle: TextView = itemView.findViewById(R.id.product_title) as TextView
        val priceText: TextView = itemView.findViewById(R.id.price_text) as TextView
        val productImage: ImageView = itemView.findViewById(R.id.product_image) as ImageView
        val remove_item: ImageView = itemView.findViewById(R.id.remove_item) as ImageButton
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar
        val addToCartFab: FloatingActionButton = itemView.findViewById(R.id.add_to_cart_fab) as FloatingActionButton
    }
}