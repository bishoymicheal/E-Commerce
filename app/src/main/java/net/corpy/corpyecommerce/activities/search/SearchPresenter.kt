package net.corpy.corpyecommerce.activities.search

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.network.EcommerceRepo

class SearchPresenter(view: SearchView) : BasePresenter<SearchView>(view) {
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

    fun searchText(s: String) {
        view.onLoading("")
        EcommerceRepo.searchProducts(s) { status, list ->
            view.finishLoad()
            view.onSearchDataLoaded(list)
        }
    }
}