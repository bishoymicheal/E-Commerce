package net.corpy.corpyecommerce.activities.myorders

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.network.EcommerceRepo

class MyOrdersPresenter(view: MyOrdersView) : BasePresenter<MyOrdersView>(view) {
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

    fun loadOrders(id: String) {
        view.onLoading("")
        EcommerceRepo.loadOrders(id) { status, data ->
            view.finishLoad()
            view.onOrdersLoaded(data)
        }
    }
}