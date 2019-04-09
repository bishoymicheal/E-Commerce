package net.corpy.corpyecommerce.fragments.saved

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.Wishlist

interface SavedFragmentView : LoadingView {
    fun onWishListLoaded(data: ArrayList<Wishlist>?)

}