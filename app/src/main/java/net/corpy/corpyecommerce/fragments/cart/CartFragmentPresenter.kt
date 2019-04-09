package net.corpy.corpyecommerce.fragments.cart

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.network.EcommerceRepo

class CartFragmentPresenter(view: CartFragmentView) : BasePresenter<CartFragmentView>(view) {
    override fun resume() {
    }

    override fun pause() {

    }

    override fun create(savedInstance: Bundle?) {

    }

    override fun destroy() {

    }

    override fun start() {

    }

    override fun stop() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun loadCart(id: String) {
        view.onLoading("")
        EcommerceRepo.loadCart(id) { status, data ->
            view.finishLoad()
            view.onCartLoaded(data)
            if (status) {
                App.cartListener?.cartCountChanged(data?.cartCount ?: App.cartCount)
            }
        }
    }
}