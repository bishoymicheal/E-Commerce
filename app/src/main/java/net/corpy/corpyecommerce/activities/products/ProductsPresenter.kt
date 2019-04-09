package net.corpy.corpyecommerce.activities.products

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.network.EcommerceRepo

class ProductsPresenter(view: ProductsView) : BasePresenter<ProductsView>(view) {
    override fun create(savedInstance: Bundle?) {

    }

    override fun destroy() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun start() {
    }

    override fun stop() {
    }

    fun loadProducts(id: String, parameterArName: String, parentEnName: String) {
        view.onLoading("")
        EcommerceRepo.products(id, parameterArName, parentEnName) { tatus, list ->
            view.finishLoad()
            view.onProductsLoaded(tatus, list)
        }
    }
}