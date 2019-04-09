package net.corpy.corpyecommerce.activities.orderdetails

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.network.EcommerceRepo

class OrderDetailsPresenter(view: OrderDetailsView) : BasePresenter<OrderDetailsView>(view) {
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

    fun loadOrderDetails(id: String) {
        view.onLoading("")
        EcommerceRepo.loadOrderItems(id){
            status,list->
            view.finishLoad()
            view.onOrderDetailsLoaded(list)
        }
    }
}