package net.corpy.corpyecommerce.interfaces

import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest

@FunctionalInterface
interface ItemClickListener {
    fun onClick(position: Int)
    fun addToCart(addToCartRequest: AddToCartRequest)
}