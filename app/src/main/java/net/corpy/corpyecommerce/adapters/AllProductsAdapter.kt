package net.corpy.corpyecommerce.adapters

import android.content.Intent
import android.support.design.widget.FloatingActionButton
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
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.login.LoginActivity
import net.corpy.corpyecommerce.interfaces.ProductClickListener
import net.corpy.corpyecommerce.model.Product
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest

class AllProductsAdapter(var products: List<Product>,
                         var productClickListener: ProductClickListener)
    : RecyclerView.Adapter<AllProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.products_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products[position]

        holder.itemView.setOnClickListener { productClickListener.onProductClick(product, holder.productImage) }


        if (App.getCurrentLanguageKey(holder.itemView.context) == "ar") {
            holder.departmentName.text = product.arParentTitle
            holder.productTitle.text = product.ar_title

        } else {
            holder.departmentName.text = product.enParentTitle
            holder.productTitle.text = product.en_title
        }
        holder.priceText.text = product.price.toString() + " " + holder.itemView.context.getString(R.string.price_label)
        Picasso.get().load(Constants.BASE_IMAGES_URL + product.photo).error(R.drawable.error)
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
                addToCartRequest.productId = product.id.toString()
                addToCartRequest.userId = App.instance.userResponse?.data?.user?.id.toString()
                productClickListener.addToCart(addToCartRequest)

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
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar
        val addToCartFab: FloatingActionButton = itemView.findViewById(R.id.add_to_cart_fab) as FloatingActionButton

    }


}