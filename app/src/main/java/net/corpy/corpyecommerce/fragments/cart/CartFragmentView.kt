package net.corpy.corpyecommerce.fragments.cart

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.Data

interface CartFragmentView : LoadingView {
    fun onCartLoaded(data: Data?)
}