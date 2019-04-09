package net.corpy.corpyecommerce.activities.checkout

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.Cities
import net.corpy.corpyecommerce.model.Data

interface CheckOutView : LoadingView {
    fun onCitiesLoaded(list: List<Cities>)
    fun onCartLoaded(data: Data?)
}