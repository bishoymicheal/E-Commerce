package net.corpy.corpyecommerce.activities.checkout

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.network.EcommerceRepo

class CheckOutPresenter(view: CheckOutView):BasePresenter<CheckOutView>(view) {
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

    fun loadCities() {
        view.onLoading("")
        EcommerceRepo.getCities { status, list ->
            view.finishLoad()
            view.onCitiesLoaded(list)
        }
    }

    fun loadCart(id: String) {
        view.onLoading("")
        EcommerceRepo.loadCart(id) { status, data ->
            view.finishLoad()
            view.onCartLoaded(data)
        }
    }
}