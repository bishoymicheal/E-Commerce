package net.corpy.corpyecommerce.fragments.saved

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.network.EcommerceRepo

class SavedFragmentPresenter(view: SavedFragmentView) : BasePresenter<SavedFragmentView>(view) {
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

    fun loadWishList(id: String) {
        view.onLoading("")
        EcommerceRepo.loadWishList(id){
            status,data->
            view.finishLoad()
            view.onWishListLoaded(data)
        }
    }
}