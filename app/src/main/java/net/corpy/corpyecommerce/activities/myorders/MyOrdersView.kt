package net.corpy.corpyecommerce.activities.myorders

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.Order

interface MyOrdersView : LoadingView {
    fun onOrdersLoaded(list: List<Order>)
}