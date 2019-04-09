package net.corpy.corpyecommerce.interfaces

import android.view.View
import net.corpy.corpyecommerce.model.Product
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest


@FunctionalInterface
interface ProductClickListener {
    fun onProductClick(product: Product, view: View? = null)
    fun addToCart(addToCartRequest: AddToCartRequest)
}