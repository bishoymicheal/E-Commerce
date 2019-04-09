package net.corpy.corpyecommerce.activities.products

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.Product

interface ProductsView : LoadingView {
    fun onProductsLoaded(status: Boolean, list: List<Product>)
}
