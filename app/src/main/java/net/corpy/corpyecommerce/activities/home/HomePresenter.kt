package net.corpy.corpyecommerce.activities.home

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.network.EcommerceRepo


class HomePresenter(view: HomeView) : BasePresenter<HomeView>(view) {



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

    fun loadMainProudactData() {
        EcommerceRepo.getParentCategories { status, list ->
            view.onMainCategoryLoaded(list)
        }
    }
}